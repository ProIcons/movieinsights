import React, {Component} from "react";
import {IRootState} from "app/shared/reducers";
import {hasAnyAuthority} from "app/modules/admin/admin-route";
import {AUTHORITIES} from "app/config/constants";
import {setLocale} from "app/shared/reducers/locale";
import {getSession} from "app/shared/reducers/authentication";
import {getProfile} from "app/shared/reducers/application-profile";
import {connect} from "react-redux";
import {hot} from "react-hot-loader";
import {BrowserRouter as Router} from "react-router-dom";
import Layout from "app/modules/layout";

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');
export interface IAppProps extends StateProps, DispatchProps {}
class App extends Component<IAppProps, any> {
    componentDidMount() {
      this.updateSessionAndProfile();
    }

    componentDidUpdate(prevProps: Readonly<IAppProps>, prevState: Readonly<any>, snapshot?: any) {
      this.updateSessionAndProfile();
    }

    updateSessionAndProfile() {
    /*  this.props.getSession();
      this.props.getProfile();*/
    }

    render() {
      const props = this.props;
      return (
        <Router basename={baseHref}>
          <Layout appProps={props}/>
        </Router>
      );
    }
}


const mapStateToProps = ({ authentication, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled,
});

const mapDispatchToProps = { setLocale, getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(hot(module)(App));
