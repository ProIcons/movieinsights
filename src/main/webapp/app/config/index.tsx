import React from "react";
import {bindActionCreators, Store} from "redux";
import initStore from "./store";
import {registerLocale} from "app/config/translation";
import {clearAuthentication} from "app/shared/reducers/authentication";
import setupAxiosInterceptors from "./axios-interceptor";
import {loadIcons} from "./icon-loader";
export interface ApplicationConfiguration {
  store: Store;
}

const initApp = (): ApplicationConfiguration => {


  // const devTools = process.env.NODE_ENV === 'development' ? <DevTools/> : null;

  const store = initStore();
  registerLocale(store);

  const actions = bindActionCreators({clearAuthentication}, store.dispatch);
  setupAxiosInterceptors(() => actions.clearAuthentication('login.error.unauthorized'));

  loadIcons();
  return {store};
}

export default initApp;
