import 'react-circular-progressbar/dist/styles.css';
import './systemMetrics.scss'
import React from 'react'
import {TranslatableComponent} from "app/components/util";
import {CBadge, CCol, CContainer, CRow} from "@coreui/react";
import {buildStyles, CircularProgressbarWithChildren} from 'react-circular-progressbar';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faServer} from "@fortawesome/free-solid-svg-icons/faServer";
import {faCheck} from "@fortawesome/free-solid-svg-icons/faCheck";
import CIcon from "@coreui/icons-react";
import {faDatabase} from "@fortawesome/free-solid-svg-icons/faDatabase";
import {faTimes} from "@fortawesome/free-solid-svg-icons/faTimes";
import {faQuestion} from "@fortawesome/free-solid-svg-icons/faQuestion";
import AnimatedNumber from 'animated-number-react';

interface SystemStatusProps {
  health: any;
  systemMetrics: any;
  jvm: any
  error: boolean;
}

enum Status {
  UP = "UP",
  DOWN = "DOWN",
  UNKNOWN = "UNKNOWN"
}

interface SystemStatusState {
  db: Status;
  ping: Status;
  elasticsearch: Status;
  error: boolean;
}

export class SystemStatus extends TranslatableComponent<SystemStatusProps, SystemStatusState> {
  constructor(props) {
    super(props, "metrics", true);
    this.state = {
      db: Status.UNKNOWN,
      elasticsearch: Status.UNKNOWN,
      ping: Status.UNKNOWN,
      error: false
    }
  }

  componentDidUpdate(prevProps: Readonly<SystemStatusProps>, prevState: Readonly<any>, snapshot?: any) {
    if (!this.props.error) {
      if (this.state.db !== this.props.health?.components?.db?.status && this.props.health?.components?.db?.status !== null) {
        this.setState({db: this.props.health?.components?.db?.status});
      }
      if (this.state.elasticsearch !== this.props.health?.components?.elasticsearch?.status && this.props.health?.components?.elasticsearch?.status !== null) {
        this.setState({elasticsearch: this.props.health?.components?.elasticsearch?.status});
      }
      if (this.state.ping !== this.props.health?.components?.ping?.status && this.props.health?.components?.ping?.status != null) {
        this.setState({ping: this.props.health?.components?.ping?.status});
      }
    } else {
      if (!this.state.error) {
        this.setState({error: true, ping: Status.DOWN, db: Status.UNKNOWN, elasticsearch: Status.UNKNOWN})
      }
    }

  }

  getBadgeIndicator = (endpoint) => {
    switch (this.getStatus(endpoint)) {
      case Status.UP:
        return faCheck;
      case Status.DOWN:
        return faTimes;
      default:
        return faQuestion;
    }
  }
  getStatus = (endpoint) => {
    return this.state[endpoint];
  }
  getColor = (status) => {
    switch (status) {
      case Status.UP:
        return "success";
      case Status.DOWN:
        return "danger";
      default:
        return "secondary";
    }
  }

  render() {
    const p = (endpoint) => buildStyles({
      pathColor: `var(--${this.getColor(this.getStatus(endpoint))}`,
    });
    const {systemMetrics, jvm, error} = this.props;
    const cpuUsage = systemMetrics && !error ? 100 * systemMetrics['system.cpu.usage'] : 0;
    const proUsage = systemMetrics && !error ? 100 * systemMetrics['process.cpu.usage'] : 0;
    const ramMax = jvm && !error ? jvm['G1 Old Gen'].max : 0;
    const ramVal = jvm && !error ? jvm['G1 Old Gen'].used : 0;
    return (
      <div>
        <CContainer>
          <CRow>
            <CCol className="justify-content-center text-center" style={{display: "flex"}}>
              <div style={{width: 200, height: 200, alignSelf: "center"}}>
                <CircularProgressbarWithChildren value={100} styles={p("elasticsearch")}>
                  <div style={{position: "relative"}}>
                    <CRow>
                      <CCol className="text-center" style={{marginLeft: "10px"}}>
                        <span className="progress-header text-value-lg">ElasticSearch</span>
                      </CCol>
                    </CRow>
                    <CRow style={{lineHeight: "10px"}}>&nbsp;</CRow>
                    <CRow>
                      <CCol className="justify-content-center text-center">
                        <CIcon size={"4xl"} name={"cib-elastic-search"}/>
                        <CBadge
                          style={{position: "absolute", bottom: 0, marginLeft: "-20px"}}
                          shape="pill"
                          color={this.getColor(this.getStatus('elasticsearch'))}>
                          <FontAwesomeIcon size={"2x"} icon={this.getBadgeIndicator('elasticsearch')}/>
                        </CBadge>
                      </CCol>
                    </CRow>
                  </div>
                </CircularProgressbarWithChildren>
              </div>
            </CCol>
            <CCol className="justify-content-center text-center" style={{display: "flex", minWidth: "300px"}}>
              <div style={{width: 300, height: 300, position: "relative", minHeight: "300px", minWidth: "300px"}}>
                <CircularProgressbarWithChildren
                  value={cpuUsage} className="overlay-progress-bar cpu"
                  styles={buildStyles({
                    trailColor: "transparent",
                    pathColor: "var(--danger)"
                  })}
                >
                  <div className="content" style={{marginLeft: "-50%"}}>
                    <span className={"text-muted text-cpu"}>System CPU Usage</span><br/>
                    <span className="text-percent text-danger">{error ? (
                      <>0.00%</>
                    ) : (
                      <AnimatedNumber value={cpuUsage} duration={950} formatValue={(val) => val.toFixed(2) + "%"}/>
                    )}</span>
                  </div>
                </CircularProgressbarWithChildren>
                <CircularProgressbarWithChildren
                  value={proUsage}
                  className="overlay-progress-bar cpu-service"
                  styles={buildStyles({
                    trailColor: "transparent",
                    pathColor: "var(--warning)"
                  })}>
                  <div className="content" style={{marginLeft: "50%"}}>
                    <span className={"text-muted text-cpu"}>
                      Service CPU Usage
                    </span><br/>
                    <span className="text-percent text-warning">{error ? (
                      <>0.00%</>
                    ) : (
                      <AnimatedNumber
                        value={proUsage}
                        duration={950}
                        formatValue={(val) => val.toFixed(2) + "%"}
                      />
                    )}</span>
                  </div>
                </CircularProgressbarWithChildren>
                <CircularProgressbarWithChildren
                  strokeWidth={error ? 0 : 2}
                  value={ramVal}
                  maxValue={ramMax}
                  className="overlay-progress-bar memory"
                  styles={buildStyles({
                    trailColor: "var(--secondary)",
                    pathColor: "var(--info)"
                  })}>
                  <div className="content" style={{marginTop: "55%"}}>
                    <span className={"text-muted text-cpu"}>
                      Service RAM Usage
                    </span><br/>
                    <span className="text-percent text-info">{this.props.error ? (
                      <>0.00%</>
                    ) : (
                      <AnimatedNumber
                        value={100 * ramVal / ramMax}
                        duration={950}
                        formatValue={(val) => val.toFixed(2) + "%"}
                      />
                    )}</span>
                  </div>
                </CircularProgressbarWithChildren>
                <CircularProgressbarWithChildren value={100} styles={p("ping")}>
                  <div style={{position: "relative"}}>
                    <CRow>
                      <CCol className="text-center" style={{marginLeft: "10px"}}>
                        <span className="progress-header text-value-lg">
                          Service
                        </span>
                      </CCol>
                    </CRow>
                    <CRow>
                      <CCol className="justify-content-center text-center">
                        <FontAwesomeIcon size={"5x"} icon={faServer}/>
                        <CBadge
                          style={{position: "absolute", bottom: 0, marginLeft: "-20px"}}
                          shape="pill"
                          color={this.getColor(this.getStatus('ping'))}
                        >
                          <FontAwesomeIcon size={"2x"} icon={this.getBadgeIndicator('ping')}/>
                        </CBadge>
                      </CCol>
                    </CRow>
                  </div>
                </CircularProgressbarWithChildren>
              </div>
            </CCol>
            <CCol className="justify-content-center text-center" style={{display: "flex"}}>
              <div style={{width: 200, height: 200, alignSelf: "center"}}>
                <CircularProgressbarWithChildren value={100} styles={p("db")}>
                  <div style={{position: "relative"}}>
                    <CRow>
                      <CCol className="text-center" style={{marginLeft: "10px"}}>
                        <span className="progress-header text-value-lg">
                          Database
                        </span>
                      </CCol>
                    </CRow>
                    <CRow>
                      <CCol className="justify-content-center text-center">
                        <FontAwesomeIcon size={"5x"} icon={faDatabase}/>
                        <CBadge
                          style={{position: "absolute", bottom: 0, marginLeft: "-20px"}}
                          shape="pill"
                          color={this.getColor(this.getStatus('db'))}
                        >
                          <FontAwesomeIcon size={"2x"} icon={this.getBadgeIndicator('db')}/>
                        </CBadge>
                      </CCol>
                    </CRow>
                  </div>
                </CircularProgressbarWithChildren>
              </div>
            </CCol>
          </CRow>
        </CContainer>
      </div>
    );
  }
}
