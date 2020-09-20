import './playground.scss'
import React, {Component} from 'react'
import {hot} from "react-hot-loader";
import {IRootState} from "app/shared/reducers";
import {setLocale} from "app/shared/reducers/locale";
import {getSession} from "app/shared/reducers/authentication";
import {getProfile} from "app/shared/reducers/application-profile";
import {connect} from "react-redux";
import {clearView, fetch, fetchYear, setActive, setActiveView, setActiveYear} from "app/reducers/dashboard-reducer";
import {MIPersonRolePickerProps} from "app/components/MIPersonRolePicker";
import MISearchBar from "app/components/MISearchBar";
import {CRow} from "@coreui/react";
import {ThreadsModal} from "app/components/metrics";
import data from './data.json'

interface PlaygroundState {
  creditSelector: MIPersonRolePickerProps;
  modal: boolean
}

class Playground extends Component<StateProps & DispatchProps, PlaygroundState> {

  constructor(props) {
    super(props);
    this.state = {
      creditSelector: null,
      modal: false,
    }
  }

  render() {
    return (<>

        <ThreadsModal showModal={true} threadDump={data} handleClose={()=>{}}/>
        <CRow className="justify-content-md-center">
          <MISearchBar onSelected={()=>{}}/>
        </CRow>


    </>);
  }
}

const mapStateToProps = (rootState: IRootState) => ({
  rootState
});

const mapDispatchToProps = {
  setLocale,
  getSession,
  getProfile,
  setActiveView,
  setActive,
  setActiveYear,
  fetch,
  fetchYear,
  clearView
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(hot(module)(Playground));
