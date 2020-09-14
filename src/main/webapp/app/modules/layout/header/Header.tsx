import "./header.scss"
import React, {Component} from "react";
import {CHeader, CHeaderBrand, CHeaderNav, CHeaderNavItem, CHeaderNavLink} from "@coreui/react";
import {IAppProps} from "app/App";
import MediaQuery from "react-responsive";

export interface IHeaderProps {
  appProps: IAppProps;
}


class Header extends Component<IHeaderProps, any> {

  constructor(props) {
    super(props);
  }

  render() {

    const props = this.props;
    return (
      <>

        <CHeader className="c-header-dark">

          <CHeaderBrand className="c-header-brand">
            <div className="logo-slice">
              <img className="img-slice" src="/content/images/logo-slice.png" alt="Logo"/>
            </div>
            <MediaQuery  minDeviceWidth={575.98}>
              <img src="/content/images/logo-jhipster.png"/>
            </MediaQuery>
            <MediaQuery  maxDeviceWidth={575.98}>
              <img src="/content/images/logo-jhipster-collapsed.png"/>
            </MediaQuery>


          </CHeaderBrand>
          <CHeaderNav className="d-md-down-none mr-auto">
            <CHeaderNavItem className="px-3">
              <CHeaderNavLink to="/app">Home</CHeaderNavLink>
            </CHeaderNavItem>
            <CHeaderNavItem className="px-3">
              <CHeaderNavLink to="/admin/docs">About</CHeaderNavLink>
            </CHeaderNavItem>
          </CHeaderNav>


          {
            props.appProps.isAdmin ? (
              <CHeaderNav className="px-3">
                {
                  props.appProps.isAdmin ? (
                    <CHeaderNavItem className="px-3">
                      <CHeaderNavLink to="/admin">Admin</CHeaderNavLink>
                    </CHeaderNavItem>
                  ) : null
                }
              </CHeaderNav>) : null
          }
          {/* <CSubheader className="px-3 justify-content-between">
          <CBreadcrumbRouter
            className="border-0 c-subheader-nav m-0 px-0 px-md-3"

            routes={this.state.breadcrump}
          />
          <div className="d-md-down-none mfe-2 c-subheader-nav">
            <CLink className="c-subheader-nav-link" href="#">
              <CIcon name="cil-speech" alt="Settings"/>
            </CLink>
            <CLink
              className="c-subheader-nav-link"
              aria-current="page"
              to="/dashboard"
            >
              <CIcon name="cil-graph" alt="Dashboard"/>&nbsp;Dashboard
            </CLink>

            <CLink className="c-subheader-nav-link" href="#">
              <CIcon name="cil-settings" alt="Settings"/>&nbsp;Settings
            </CLink>
          </div>
        </CSubheader>*/}
        </CHeader>
      </>
    );
  }
}

export default Header;
