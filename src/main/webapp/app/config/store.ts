import { applyMiddleware, compose, createStore } from 'redux';
import promiseMiddleware from 'redux-promise-middleware';
import thunkMiddleware from 'redux-thunk';
import reducer, { IRootState } from 'app/shared/reducers';
import errorMiddleware from './error-middleware';
import notificationMiddleware from './notification-middleware';
import loggerMiddleware from './logger-middleware';
import { loadingBarMiddleware } from 'react-redux-loading-bar';

const defaultMiddlewares = [
  thunkMiddleware,
  errorMiddleware,
  notificationMiddleware,
  promiseMiddleware,
  loadingBarMiddleware({
    promiseTypeSuffixes: ['PENDING', 'FULFILLED', 'REJECTED'],
    scope: 'app',
  }),
  loggerMiddleware,
];
const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ trace: true, traceLimit: 25 }) || compose;
const composedMiddlewares = middlewares =>
  process.env.NODE_ENV === 'development'
    ? composeEnhancers(applyMiddleware(...defaultMiddlewares, ...middlewares))
    : compose(applyMiddleware(...defaultMiddlewares, ...middlewares));

const initialize = (initialState?: IRootState, middlewares = []) => createStore(reducer, initialState, composedMiddlewares(middlewares));

export default initialize;
