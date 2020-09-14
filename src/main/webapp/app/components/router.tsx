import React, {Component, ReactElement, Suspense} from "react";
import {IRedirect, IRoute, IRouteObject, IRouter, IType} from "app/utils/route-utils";
/*import Loading from "app/modules/layout/loading-overlay";
import {Redirect, Route, Switch} from "react-router-dom";
import {isRouter} from "app/modules/admin/routes";
import ErrorBoundaryRoute from "app/shared/error/error-boundary-route";*/

export interface RouterProps {
  routes: (IRouteObject | IRouter)[];
}

export default class Router<T extends RouterProps> extends Component<T, any> {
  /*constructor(props) {
    super(props);

    this.state = {
      optRoutes: null,
      routes: null
    }
  }

  private getRouteElement(routes: (IRouteObject | IRouter)[]): ReactElement | ReactElement[] {
    const routeRoutes = [];
    const redirectRoutes = [];
    const routers = [];
    const genericRoutes = [];

    routes.forEach((perRoute: IRouteObject | IRouter, idx: number) => {
      if (!isRouter(perRoute)) {
        /!* eslint no-case-declarations: off *!/
        switch (perRoute.type) {
          case IType.ROUTE:
            const route = perRoute as IRoute;
            const RouteComponent = route.routeComponentType ? route.routeComponentType : ErrorBoundaryRoute;
            routeRoutes.push(
              <RouteComponent
                key={idx}
                path={route.path}
                exact={route.exact}
                component={route.component}
              />
            )
            break;
          case IType.REDIRECT:
            const redirect = perRoute as IRedirect;
            redirectRoutes.push(
              <Redirect
                key={idx}
                exact={true}
                from={redirect.from}
                to={redirect.to}
              />);
            break;
          case IType.ANYROUTE:
            const anyRoute = perRoute as IRoute;
            genericRoutes.push(<Route
              key={idx}
              name={anyRoute.name}
              path={anyRoute.path}
              exact={anyRoute.exact}
              component={anyRoute.component}
            />);
            break;
          default:
            break;
        }
      } else {
        const router = perRoute;
        const render = router2 => props => {
          return <router.routerComponentType
            hasAnyAuthorities={router2.hasAnyAuthorities} routes={router2.routes} {...props} />;
        };
        routers.push(
          <Route
            key={idx}
            path={router.path}
            exact={false}
            render={render(router)}
          />
        )
      }
    });

    return [...routeRoutes, ...redirectRoutes, ...routers, ...genericRoutes];
  }


  componentDidMount() {
    this.setState({optRoutes: this.props.routes, routes: this.getRouteElement(this.props.routes)})
  }

  /!*
  {
      type: IType.ROUTE,
      name: "Login",
      path: `/login`,
      component: Login,
      exact: true
    },
    {
      type: IType.ROUTE,
      name: "Logout",
      path: `/logout`,
      component: Logout,
      exact: true
    },
    ...DashboardRoutes,
    AdminRoutes,
    {
      type: IType.REDIRECT,
      from: "/",
      to: `/dashboard`,
    },
    {
      type: IType.ANYROUTE,
      name: "404",
      exact:true,
      component: Page404
    }*!/*/
  render() {
    /*return (
      <Suspense fallback={Loading}>
        <Switch>
          {this.state.routes}
        </Switch>
      </Suspense>);
    ;*/
    return null;
  }
}
