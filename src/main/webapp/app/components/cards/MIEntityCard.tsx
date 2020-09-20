import './MIEntityCard.scss'
import React from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import MILoadingCircle from "app/components/util/MILoadingCircle";
import CIcon from "@coreui/icons-react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {NavLink} from "react-router-dom";
import _ from "lodash";

import {
  MIBaseLoadableCard,
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import {MIEntityNotFound} from "app/components/util";
import {AppUtils} from "app/utils/app-utils";
import {BaseNamedEntity} from "app/models";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";


export interface MIEntityCardProps<T extends BaseNamedEntity> extends MIBaseLoadableCardProps<T> {
  entityType: TmdbEntityType,
  valueType: MIValueType,
  isCooperative?: boolean,
  defaultEntity: T;
  movieCount: number,
  children: CIcon | typeof FontAwesomeIcon,
  customName?: string;
}

export type MIEntityCardState<T extends BaseNamedEntity> = MIBaseLoadableCardState<T>;


export class MIEntityCard<T extends BaseNamedEntity> extends MIBaseLoadableCard<MIEntityCardProps<T>, MIEntityCardState<T>, T> {
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
    const str =this.props.entityType.split(' ').reverse().reduce(s=>s).toLowerCase();
    const indexName = _.camelCase(`${this.props.isCooperative?"co-":""}${str}`);
    const translatedIndex = this.getAppTranslation(`${_.camelCase(this.props.entityType)}.${indexName}`);
    const title = `${this.getTranslation(`${this.props.valueType.toLowerCase()}PopularEntity`,{entity:translatedIndex})}`;
    return (
      <>
        <CCardHeader id="mi-entity-header" className="mi-entity-header">
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
                  <div className="mi-entity-title align-middle">{this.props.customName || this.state.entity?.name}</div>
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
