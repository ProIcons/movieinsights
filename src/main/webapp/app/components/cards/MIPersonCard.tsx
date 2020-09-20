import './MIPersonCard.scss'
import React from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {NavLink} from "react-router-dom";

import {
  MIBaseLoadableCard,
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import {MIEntityNotFound, MILoadingCircle} from "app/components/util";
import {PERSON_PROFILE_PLACEHOLDER} from "app/config/constants";
import {IPerson} from "app/models";
import {personDefaultValue} from "app/models/defaultValues";
import {CreditRole, TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";
import {normalizeText} from "app/utils";

export interface MIPersonCardProps extends MIBaseLoadableCardProps<IPerson> {
  movieCount: number;
  type: CreditRole,
  valueType: MIValueType,
  isCooperative?: boolean,
}

export type MIPersonCardState = MIBaseLoadableCardState<IPerson>;

const indexByProps = (props: MIPersonCardProps) => {
  switch (props.type) {
    case CreditRole.ACTOR:
      return props.isCooperative ? "coStar" : "actor";
    case CreditRole.DIRECTOR:
      return props.isCooperative ? "coDirector" : "director";
    case CreditRole.PRODUCER:
      return props.isCooperative ? "coProducer" : "producer";
    case CreditRole.WRITER:
      return props.isCooperative ? "coWriter" : "writer";
    default:
      throw new Error();
  }
}

export class MIPersonCard extends MIBaseLoadableCard<MIPersonCardProps, MIPersonCardState, IPerson> {

  constructor(props) {
    super(props, personDefaultValue);
  }

  protected subEntitiesNames(): string[] {
    return ["profile"];
  }

  renderBodyLinked() {
    return (
      <NavLink className="mi-entity-link"
               to={`/app/person/${this.state.entity?.id}-${normalizeText(this.state.entity?.name)}/${CreditRole[this.props.type].toUpperCase()}`}>
        {this.renderCard()}
      </NavLink>);
  }

  getProfilePic = () => {

    setTimeout(() => {
      this.loaded("profile");
    }, 2000)
    if (this.state.entity !== personDefaultValue && this.state.entity) {
      if (!this.state.entity.profilePath) {
        return (
          <img alt={""} onLoad={() => this.loaded("profile")} style={{backgroundColor: "var(--secondary)"}}
               height={"150px"}
               width={"100px"} src={PERSON_PROFILE_PLACEHOLDER}/>)
      } else {
        return (<img alt={""} onLoad={() => this.loaded("profile")} height={"185x"}
                     src={`https://image.tmdb.org/t/p/w185/${this.state.entity?.profilePath}`}/>);
      }
    } else {
      this.loaded("profile");
      return (<img alt={""} height={"150px"} width={"100px"}/>);
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
          <table style={{marginLeft: "-5px"}} width="150px">
            <tbody>
            <tr>
              <td className={"text-warning"}>
                <CIcon name={`cil-movie`} height="36" className="my-4"/>
              </td>
              <td className="text-md-center text-value-lg">
                {this.props.movieCount} {this.getAppTranslation(`movie.movie${this.props.movieCount>1?'s':''}`)}
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
    } else if (!this.state.loaded) {
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
    const translatedIndex = this.getAppTranslation(`person.${indexByProps(this.props)}`);
    const title = `${this.getTranslation(`${this.props.valueType.toLowerCase()}PopularEntity`, {entity: translatedIndex})}`;
    return (
      <>
        <CCardHeader className="mi-entity-header">
          <div>
            {title}
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
