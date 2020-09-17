import React, {Component} from "react";
import {CFooter} from "@coreui/react";

export default class Footer extends Component<any, any> {

  render() {
    return (
      <CFooter fixed={false}>
        <div>
          <a href="https://www.movieinsights.gr/" target="_blank" rel="noopener noreferrer">MovieInsights</a>
          <span className="ml-1">&copy; 2020 Νικόλαος Μααυρόπουλος.</span>
        </div>
        <div className="mfs-auto">
          <span className="mr-1">Powered by</span>
          <a href="https://coreui.io/react" target="_blank" rel="noopener noreferrer">CoreUI</a>,&nbsp;
          <a href="https://www.jhipster.tech/" target="_blank" rel="noopener noreferrer">JHipster</a>
        </div>
      </CFooter>
    );
  }
}
