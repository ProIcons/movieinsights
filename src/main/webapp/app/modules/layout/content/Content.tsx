import React, {Component} from "react";
import {CContainer, CFade} from "@coreui/react";
import Router from "app/modules/router/Router";

export class ContentComponent extends Component<any, any> {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <main className="c-main">
        <CContainer fluid>
          <CFade>
           <Router/>
          </CFade>
        </CContainer>
      </main>
    );
  }
}

export default ContentComponent;
