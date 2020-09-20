import './MILoadingCircle.scss'
import React, {Component} from "react";

export class MILoadingCircle extends Component<any, any> {
  render() {
    return (
      <div className="mi-loading-circle-container">
        <div className="loader">
          <div className="loader">
            <div className="loader">
              <div className="loader">

              </div>
            </div>
          </div>
        </div>
      </div>);
  }
}

export default MILoadingCircle;
