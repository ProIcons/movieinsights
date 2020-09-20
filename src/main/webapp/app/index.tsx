import './style.scss';
import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import ErrorBoundary from './shared/error/error-boundary';
import App from "./App";
import initApp from "app/config";

export const {store} = initApp();

const rootEl = document.getElementById('root');

const render = Component =>
  // eslint-disable-next-line react/no-render-return-value
  ReactDOM.render(
    <ErrorBoundary>
      <Provider store={store}>
        <div>
          <Component/>
        </div>
      </Provider>
    </ErrorBoundary>,
    rootEl
  );

render(App);
