import React, {Component, Suspense} from "react";
import {Redirect, Switch} from "react-router-dom";
import ErrorBoundaryRoute from "app/shared/error/error-boundary-route";
import Login from "app/modules/login/Login";
import Logout from "app/modules/login/Logout";
import Dashboard from "app/modules/dashboard/Dashboard";
import {AUTHORITIES} from "app/config/constants";
import Page404 from "app/modules/error/Page404";
import AdminRoute from 'app/modules/admin/admin-route';
import {MILoadingCircle} from "app/components/util";

const Playground = React.lazy(() => import("app/playground"));
const Admin = React.lazy(() => import('app/modules/admin'));

export default class Router extends Component<any, any> {
  render() {
    return (
      <Suspense fallback={MILoadingCircle}>
        <Switch>
          <ErrorBoundaryRoute path="/login" component={Login}/>
          <ErrorBoundaryRoute path="/logout" component={Logout}/>
          {process.env.NODE_ENV === 'development' ? (
            <ErrorBoundaryRoute path={"/dev"} exact={false} component={Playground}/>) : null}
          <ErrorBoundaryRoute path="/app" exact={false} component={Dashboard}/>
          <AdminRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]}/>
          <Redirect exact={true} from="/" to="/app"/>
          <ErrorBoundaryRoute component={Page404}/>
        </Switch>
      </Suspense>
    );
  }

}
