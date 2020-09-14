import {CCard, CCardHeader, CCol, CContainer, CRow} from '@coreui/react'
import React, {Component} from 'react'
import MISearchBar from "app/components/MISearchBar";
import {RouteComponentProps} from 'react-router-dom';
import {AppUtils} from "app/utils/app-utils";

/*
 /*<div className="c-app c-default-layout flex-row align-items-center">

{/*<CCard>
        <CCardHeader>
          <CRow className="justify-content-center">
          <div className="clearfix">
            <h1 className="float-left display-3 mr-4">404</h1>
            <h4 className="pt-3">Oops! You&apos;re lost.</h4>
            <p className="text-muted float-left">The page you are looking for was not found.</p>
          </div>
          </CRow>
        </CCardHeader>
          <CRow className="justify-content-center">
            <CCol md="6">
              <CInputGroup className="input-prepend">
                <CInputGroupPrepend>
                  <CInputGroupText>
                    <CIcon name="cil-magnifying-glass"/>
                  </CInputGroupText>
                </CInputGroupPrepend>
                <CInput size="16" type="text" placeholder="What are you looking for?"/>
                <CInputGroupAppend>
                  <CButton color="info">Search</CButton>
                </CInputGroupAppend>
              </CInputGroup>
            </CCol>
          </CRow>
      </CCard>}
 */
interface Page404Props extends RouteComponentProps {

}
class Page404 extends Component<any, any> {

  render() {
    return (
      <>
        <CContainer>
          <CCard>
            <CCardHeader>
              <CCol>
                <CRow  className="justify-content-center">
                  <h1 className="float-left display-3 mr-4">404</h1>
                  <h4 className="pt-3">Oops! You&apos;re lost.</h4>
                </CRow>
                <CRow  className="justify-content-center">
                  <p className="text-muted float-left">The page you are looking for was not found.</p>
                </CRow>
              </CCol>
            </CCardHeader>
            <CRow className="justify-content-center">
                <MISearchBar onSelected={(e)=>{
                  this.props.history.push(AppUtils.generateNavigationLink(e));
                }}/>
            </CRow>
          </CCard>
        </CContainer>
      </>

    )
  }
}

export default Page404;
