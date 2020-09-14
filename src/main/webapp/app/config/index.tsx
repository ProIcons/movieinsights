import React from "react";
import {bindActionCreators, Store} from "redux";
import initStore from "./store";
import {registerLocale} from "app/config/translation";
import {clearAuthentication} from "app/shared/reducers/authentication";
import setupAxiosInterceptors from "./axios-interceptor";
import {loadIcons} from "./icon-loader";
import DevTools from './devtools'
export interface ApplicationConfiguration {
  store: Store;
  devTools?: JSX.Element;
}

const initApp = (): ApplicationConfiguration => {


  const devTools = process.env.NODE_ENV === 'development' ? <DevTools/> : null;

  const store = initStore();
  registerLocale(store);

  const actions = bindActionCreators({clearAuthentication}, store.dispatch);
  setupAxiosInterceptors(() => actions.clearAuthentication('login.error.unauthorized'));

  loadIcons();


  return {store,devTools};
}

export default initApp;
