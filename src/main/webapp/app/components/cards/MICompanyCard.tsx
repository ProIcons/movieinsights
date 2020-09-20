import React, {Component} from "react";
import CIcon from "@coreui/icons-react";
import {MIEntityCard} from "app/components/cards/MIEntityCard";
import {IProductionCompany} from "app/models";
import {TmdbEntityType} from "app/models/enumerations";
import {productionCompanyDefaultValue} from "app/models/defaultValues";
import {MIValueType} from "app/shared/enumerations/MIValueType";

export interface MICompanyCardProps {
  company: IProductionCompany;
  movieCount: number;
  coProducing?: boolean;
  valueType: MIValueType;
}


export class MICompanyCard extends Component<MICompanyCardProps, any> {
  render() {
    return (
      <MIEntityCard entityType={TmdbEntityType.COMPANY} valueType={this.props.valueType}
                    isCooperative={this.props.coProducing} defaultEntity={productionCompanyDefaultValue}
                    movieCount={this.props.movieCount} entity={this.props.company}
      >
        <CIcon className={"text-secondary"} name={'cid-building'} size={'5xl'}/>
      </MIEntityCard>
    );
  }
}
