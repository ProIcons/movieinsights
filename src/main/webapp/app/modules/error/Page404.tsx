import {CCard, CCardHeader, CCol, CContainer, CRow} from '@coreui/react'
import React, {Component} from 'react'
import MISearchBar from "app/components/MISearchBar";
import {AppUtils} from "app/utils/app-utils";

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
