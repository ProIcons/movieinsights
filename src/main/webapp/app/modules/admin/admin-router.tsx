import React from "react";
import {IRoute, IRouteObject, IType} from "app/utils/route-utils";
import AdminRoute, {AdminRouteProps, hasAnyAuthority} from "app/modules/admin/admin-route";
import Router from "app/components/router";
import {connect} from "react-redux";
import {IRootState} from "app/shared/reducers";
import {Switch} from "react-router-dom";

interface IAdminRouterProps extends AdminRouteProps, StateProps {
  routes: IRouteObject[];
}

function mergeArrays(...arrays) {
  let jointArray = []

  arrays.forEach(array => {
    jointArray = [...jointArray, ...(array ? array : [])]
  })
  const uniqueArray = jointArray.reduce((newArray, item) => {
    if (newArray.includes(item)) {
      return newArray
    } else {
      return [...newArray, item]
    }
  }, [])
  return uniqueArray
}

class AdminRouterComponent extends Router<IAdminRouterProps> {

  constructor(props: IAdminRouterProps) {
    super(props);

    let route: IRoute;
    props.routes.forEach((p) => {
      route = null;
      if (p.type === IType.ROUTE) {
        route = p as IRoute;
        route.routeComponentType = AdminRoute;
        route.hasAnyAuthorities = mergeArrays(this.props.hasAnyAuthorities,route.hasAnyAuthorities);
      }
    })
  }
  render(): JSX.Element {
    return (
      <Switch {...this.props}>
        {this.state.routes}
      </Switch>
    )
  }

}
const mapStateToProps = (
  {authentication: {isAuthenticated, account, sessionHasBeenFetched}}: IRootState,
  {hasAnyAuthorities = []}: AdminRouteProps
) => ({
  isAuthenticated,
  isAuthorized: hasAnyAuthority(account.authorities, hasAnyAuthorities),
  sessionHasBeenFetched,
});
type StateProps = ReturnType<typeof mapStateToProps>;

export const AdminRouter = connect(mapStateToProps, null, null, { pure: false })(AdminRouterComponent);
export default AdminRouter
