import SockJS from 'sockjs-client';

import Stomp from 'webstomp-client';
import { Observable } from 'rxjs';
import { Storage } from 'app/utils';

import { ACTION_TYPES as ADMIN_ACTIONS } from 'app/modules/admin/administration.reducer';
import { ACTION_TYPES as AUTH_ACTIONS } from 'app/shared/reducers/authentication';
import { FAILURE, SUCCESS, WS } from 'app/shared/reducers/action-type.util';
import { AUTHORITIES } from 'app/config/constants';

let stompClient = null;

let metricsSubscriber = null;
let connection: Promise<any>;
let connectedPromise: any = null;
let metricsListener: Observable<Response>;
let metricsListenerObserver: any;
let alreadyConnectedOnce = false;
let requestedDisconnect = false;
let socket: WebSocket = null;

enum Metric {
  METRICS = 'METRICS',
  THREADDUMP = 'THREADDUMP',
  HEALTH = 'HEALTH',
}

interface Response {
  type: Metric;
  data: any;
}

const createConnection = (): Promise<any> => new Promise((resolve, reject) => (connectedPromise = resolve));

const createMetricsListener = (): Observable<Response> =>
  new Observable<Response>(observer => {
    metricsListenerObserver = observer;
  });

const subscribe = () => {
  void connection.then(() => {
    metricsSubscriber = stompClient.subscribe('/topic/metrics', data => {
      metricsListenerObserver.next(JSON.parse(data.body));
    });
  });
};

const connect = () => {
  if (connectedPromise !== null || alreadyConnectedOnce) {
    // the connection is already being established
    return;
  }
  connection = createConnection();
  metricsListener = createMetricsListener();

  // building absolute path so that websocket doesn't fail when deploying with a context path
  const loc = window.location;
  const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

  const headers = {};
  let url = '//' + loc.host + baseHref + '/websocket/metrics';
  const authToken = Storage.local.get('mi-authenticationToken') || Storage.session.get('mi-authenticationToken');
  if (authToken) {
    url += '?access_token=' + authToken;
  }
  socket = new SockJS(url);
  stompClient = Stomp.over(socket);
  stompClient.debug = () => {};

  stompClient.connect(headers, () => {
    connectedPromise('success');
    connectedPromise = null;
  });
};

const disconnect = () => {
  requestedDisconnect = true;
  if (stompClient !== null) {
    stompClient.disconnect();
    stompClient = null;
    socket.onclose = null;
    socket = null;
  }
  alreadyConnectedOnce = false;
};

const metricsReceive = () => metricsListener;

const unsubscribe = () => {
  if (metricsSubscriber !== null) {
    metricsSubscriber.unsubscribe();
  }
  metricsListener = createMetricsListener();
};

export default store => next => action => {
  if (action.type === SUCCESS(AUTH_ACTIONS.GET_SESSION)) {
    if (action.payload.data.authorities.includes(AUTHORITIES.ADMIN)) {
      connect();
      if (!alreadyConnectedOnce) {
        socket.onclose = () => {
          if (!requestedDisconnect) {
            store.dispatch({
              type: ADMIN_ACTIONS.WEBSOCKET_LOST_CONNECTION,
            });
          }
        };
        metricsReceive().subscribe(activity => {
          let type = null;
          switch (activity.type) {
            case Metric.HEALTH:
              type = WS(ADMIN_ACTIONS.FETCH_HEALTH);
              break;
            case Metric.METRICS:
              type = WS(ADMIN_ACTIONS.FETCH_METRICS);
              break;
            case Metric.THREADDUMP:
              type = WS(ADMIN_ACTIONS.FETCH_THREAD_DUMP);
              break;
            default:
          }
          return store.dispatch({
            type,
            payload: activity,
          });
        });
      }
    }
  } else if (action.type === FAILURE(AUTH_ACTIONS.GET_SESSION) || action.type === AUTH_ACTIONS.LOGOUT) {
    unsubscribe();
    disconnect();
  } else if (action.type === ADMIN_ACTIONS.WEBSOCKET_METRICS_SUBSCRIBE) {
    subscribe();
  } else if (action.type === ADMIN_ACTIONS.WEBSOCKET_METRICS_UNSUBSCRIBE) {
    unsubscribe();
  }
  return next(action);
};
