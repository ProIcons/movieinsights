import React, {Component, ComponentType, Suspense} from "react";
import {IRootState} from "app/shared/reducers";
import {Translate} from "app/translate";
import Loading from "app/modules/layout/loading-overlay";
import {Redirect, Route, RouteProps} from 'react-router-dom';
import {connect} from "react-redux";
import {setLocale} from "app/shared/reducers/locale";
import {getSession} from "app/shared/reducers/authentication";
import {getProfile} from "app/shared/reducers/application-profile";

export interface AdminRouteProps extends RouteProps {
  hasAnyAuthorities?: string[];
}

export interface IAdminRouteProps extends AdminRouteProps, DispatchProps, StateProps {
}


class AdminRouteComponent extends Component<IAdminRouteProps, any> {

  constructor(props: IAdminRouteProps) {
    super(props);
  }

  componentDidMount() {
    this.props.getSession();
    this.props.getProfile();
  }

  renderComponent = (ComponentT: ComponentType<any>, props) => <Suspense fallback={Loading}>
    <ComponentT {...props} /></Suspense>;

  checkAuthorities = (ComponentT: ComponentType<any>, props) =>
    this.props.isAuthorized ? (
      this.renderComponent(ComponentT, props)
    ) : (
      <div className="insufficient-authority">
        <div className="alert alert-danger">
          <Translate contentKey="error.http.403">You are not authorized to access this page.</Translate>
        </div>
      </div>
    );

  renderRedirect = (ComponentT: ComponentType<any>) => (props) => {
    if (!this.props.sessionHasBeenFetched) {
      return (
        <Loading/>
      );
    } else {
      return this.props.isAuthenticated ? (
        this.checkAuthorities(ComponentT, props)
      ) : (
        <Redirect
          to={{
            pathname: '/login',
            search: this.props.location.search,
            state: {from: this.props.location},
          }}
        />
      );
    }
  };

  render() {

    const {
      component: ComponentT,
      isAuthenticated,
      sessionHasBeenFetched,
      isAuthorized,
      hasAnyAuthorities = [],
      ...rest
    } = this.props as IAdminRouteProps;

    const NewComponent = !ComponentT ? this.props.render as ComponentType : ComponentT;

    if (!ComponentT) throw new Error(`A component needs to be specified for private route for path ${(this.props as any).path}`);

    return (
      <Route {...rest} render={this.renderRedirect(ComponentT)}/>
    );
  }
}

export const hasAnyAuthority = (authorities: string[], hasAnyAuthorities: string[]) => {
  if (authorities && authorities.length !== 0) {
    if (hasAnyAuthorities.length === 0) {
      return true;
    }
    return hasAnyAuthorities.some(auth => authorities.includes(auth));
  }
  return false;

};

const mapStateToProps = (
  {authentication: {isAuthenticated, account, sessionHasBeenFetched}}: IRootState,
  {hasAnyAuthorities = []}: AdminRouteProps
) => ({
  isAuthenticated,
  isAuthorized: hasAnyAuthority(account.authorities, hasAnyAuthorities),
  sessionHasBeenFetched,
});
type StateProps = ReturnType<typeof mapStateToProps>;
const mapDispatchToProps = {setLocale, getSession, getProfile};
type DispatchProps = typeof mapDispatchToProps;
export default connect(mapStateToProps, mapDispatchToProps, null, {pure: false})(AdminRouteComponent);
