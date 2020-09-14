import './MIPersonCard.scss'
import React from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import MILoadingCircle from "app/components/MILoadingCircle";
import {IPerson, defaultValue as personDefaultValue} from "app/models/IPerson.Model";
import MIEntityNotFound from "app/components/MIEntityNotFound";
import {NavLink} from "react-router-dom";
import {normalizeText} from "app/utils";
import MIBaseLoadableCard, {
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import {PERSON_PROFILE_PLACEHOLDER} from "app/config/constants";
import {TmdbEntityType} from "app/models/enumerations";


export enum MIPersonCardCreditType {
  Actor,
  Director,
  Writer,
  Producer
}

export enum MIPersonCardValueType {
  Most,
  Least
}

export interface MIPersonCardProps extends MIBaseLoadableCardProps<IPerson>{
  movieCount: number;
  type: MIPersonCardCreditType,
  valueType: MIPersonCardValueType,
}

export type MIPersonCardState = MIBaseLoadableCardState<IPerson>;

export default class MIPersonCard extends MIBaseLoadableCard<MIPersonCardProps, MIPersonCardState, IPerson> {

  constructor(props) {
    super(props,personDefaultValue);
  }
  protected subEntitiesNames(): string[] {
      return ["profile"];
  }
  renderBodyLinked() {
    return (
      <NavLink className="mi-entity-link"
               to={`/app/person/${this.state.entity?.id}-${normalizeText(this.state.entity?.name)}`}>
        {this.renderCard()}
      </NavLink>);
  }

  getProfilePic = () => {

    setTimeout(()=> {
      this.loaded("profile");
    },2000)
    if (this.state.entity !== personDefaultValue && this.state.entity) {
      if (!this.state.entity.profilePath) {
        return (<img onLoad={()=>this.loaded("profile")} style={{backgroundColor:"var(--secondary)"}} height={"150px"} width={"100px"} src={PERSON_PROFILE_PLACEHOLDER}/>)
      }
      else {
        return (<img onLoad={()=>this.loaded("profile")} height={"185x"} src={`https://image.tmdb.org/t/p/w185/${this.state.entity?.profilePath}`}/>);
      }
    }
    else {
      this.loaded("profile");
      return (<img  height={"150px"} width={"100px"}/>);
    }
  }

  renderBody() {
    const profilePic = this.getProfilePic();
    return (<>
      <CCol className="mi-person-profile">
        {
          this.state.entity ?
            profilePic ? profilePic : null
            : null
        }
      </CCol>
      <CCol>
        <CRow>
          &nbsp;
          <CCol>
            <div className="mi-person-title-overlay"/>
            <div className="mi-person-title align-middle">{this.state.entity?.name}</div>
          </CCol>
        </CRow>
        <CRow>&nbsp;</CRow>
        <CRow>
          <table style={{marginLeft: "-20px"}} width="200px">
            <tbody>
            <tr>
              <td className={"text-warning"}>
                <CIcon name={`cil-movie`} height="36" className="my-4"/>
              </td>
              <td className="text-md-center text-value-lg">
                {this.props.movieCount} Movies
              </td>
            </tr>
            </tbody>
          </table>
        </CRow>
      </CCol>
    </>);
  }

  renderContent = () => {
    const jsx = [];
    if (!this.state.entity) {
      jsx.push(
        <div key={2} className="mi-entity-loading">
          <MIEntityNotFound type={TmdbEntityType.PERSON}/>
        </div>
      )
    }else if (!this.state.loaded) {
      jsx.push(
        <div key={1} className="mi-entity-loading">
          <MILoadingCircle/>
        </div>
      )
    }
    jsx.push(
      <CRow key={3}>
        {this.renderBody()}
      </CRow>
    )
    return jsx;
  }

  renderCard() {
    return (
      <>
        <CCardHeader className="mi-entity-header">
          <div>
            {MIPersonCardValueType[this.props.valueType]} Popular {MIPersonCardCreditType[this.props.type]}
          </div>
        </CCardHeader>
        <CCardBody className="mi-person-body">
          {this.renderContent()}
        </CCardBody>
      </>
    );
  }

  render() {
    return (
      <CCard className="mi-entity-container">
        {this.state.entity && this.state.entity !== personDefaultValue ? this.renderBodyLinked() : this.renderCard()}
      </CCard>
    )
  }
}
