import React, {Component} from "react";
import MIEntityCard from "app/components/cards/MIEntityCard";
import CIcon from "@coreui/icons-react";
import {IProductionCompany} from "app/models/IProductionCompany.Model";
import {defaultValue as companyDefaultValue} from "app/models/IProductionCompany.Model";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";

export interface MICompanyCardProps {
  company: IProductionCompany;
  movieCount: number;
  coProducing?: boolean;
  valueType: MIValueType;
}


export default class MICompanyCard extends Component<MICompanyCardProps, any> {
  render() {
    return (
      <MIEntityCard entityType={TmdbEntityType.COMPANY} valueType={this.props.valueType}
                    isCooperative={this.props.coProducing} defaultEntity={companyDefaultValue}
                    movieCount={this.props.movieCount} entity={this.props.company}
      >
        <CIcon className={"text-secondary"} name={'cid-building'} size={'5xl'}/>
      </MIEntityCard>
    );
  }
}
