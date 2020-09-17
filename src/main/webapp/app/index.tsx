import './style.scss';
import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import ErrorBoundary from './shared/error/error-boundary';
import App from "./App";
import initApp from "app/config";

const {store} = initApp();
export const store_ = store;
const rootEl = document.getElementById('root');

const render = Component =>
  // eslint-disable-next-line react/no-render-return-value
  ReactDOM.render(
    <ErrorBoundary>
      <Provider store={store}>
        <div>
          {/* If this slows down the app in dev disable it and enable when required  */}
          {/*{devTools}*/}
          <Component/>
        </div>
      </Provider>
    </ErrorBoundary>,
    rootEl
  );

render(App);
