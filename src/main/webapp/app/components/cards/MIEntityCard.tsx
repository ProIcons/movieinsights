import './MIEntityCard.scss'
import React from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import MILoadingCircle from "app/components/MILoadingCircle";
import CIcon from "@coreui/icons-react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {NavLink} from "react-router-dom";
import MIEntityNotFound from "app/components/MIEntityNotFound";

import MIBaseLoadableCard, {
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import {AppUtils} from "app/utils/app-utils";
import {BaseNamedEntity} from "app/models/BaseEntity.Model";
import {TmdbEntityType} from "app/models/enumerations";


export enum MIValueType {
  Most = "Most",
  Least = "Least"
}

export interface MIEntityCardProps<T extends BaseNamedEntity> extends MIBaseLoadableCardProps<T> {
  entityType: TmdbEntityType,
  valueType: MIValueType,
  isCooperative?: boolean,
  defaultEntity: T;
  movieCount: number,
  children: CIcon | typeof FontAwesomeIcon
}

export type MIEntityCardState<T extends BaseNamedEntity> = MIBaseLoadableCardState<T>;


export default class MIEntityCard<T extends BaseNamedEntity> extends MIBaseLoadableCard<MIEntityCardProps<T>, MIEntityCardState<T>, T> {
  constructor(props) {
    super(props, props.defaultEntity);
  }

  protected subEntitiesNames(): string[] {
    return [];
  }

  renderLinked() {
    const link = this.state.entity ? AppUtils.generateNavigationLink(this.state.entity) : ''
    return (
      <NavLink className="mi-entity-link" to={link}>
        {this.renderContent()}
      </NavLink>);
  }


  renderContent() {
    const title = `${this.props.valueType} Popular ${this.props.isCooperative?"Co-":""}${this.props.entityType}`;

    return (
      <>
        <CCardHeader className="mi-entity-header">
          <div>
            {title}
          </div>
        </CCardHeader>
        <CCardBody className="mi-entity-body">
          {
            !this.state.loaded ? (
              <div className="mi-entity-loading">
                <MILoadingCircle/>
              </div>
            ) : null

          }
          {!this.state.entity ? (
            <div className="mi-entity-loading">
              <MIEntityNotFound type={this.props.entityType}/>
            </div>
          ) : null}
          <CRow>
            <CCol className="mi-entity-profile">
              {this.props.children}
            </CCol>
            <div className='c-vr'/>
            <CCol>
              <CRow>
                &nbsp;
                <CCol>
                  <div className="mi-entity-title-overlay"/>
                  <div className="mi-entity-title align-middle">{this.state.entity?.name}</div>
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
                      {this.props.movieCount} Movies
                    </td>
                  </tr>
                  </tbody>
                </table>
              </CRow>
            </CCol>
          </CRow>
        </CCardBody>
      </>
    )
  }

  render() {
    return (
      <CCard className="mi-entity-container">
        {
          this.state.loaded ?
            this.renderLinked() :
            this.renderContent()
        }
      </CCard>
    );
  }

}
