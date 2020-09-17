import React, {Component} from "react";
import {defaultValue as countryDefaultValue, IProductionCountry} from "app/models/IProductionCountry.Model";
import MIEntityCard from "app/components/cards/MIEntityCard";
import CIcon from "@coreui/icons-react";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";

export interface MICountryCardProps {
  country: IProductionCountry;
  movieCount: number;
  coProducing?: boolean;
  valueType: MIValueType;
}

export default class MICountryCard extends Component<MICountryCardProps, any> {
  render() {
    return (
      <MIEntityCard
        entityType={TmdbEntityType.COUNTRY}
        valueType={this.props.valueType}
        isCooperative={this.props.coProducing}
        defaultEntity={countryDefaultValue}
        movieCount={this.props.movieCount}
        entity={this.props.country}
      >
        {this.props.country && this.props.country !== countryDefaultValue ? (
          <CIcon name={`cif-${this.props.country?.iso31661.toLowerCase()}`} size={'5xl'}/>
        ) : null
        }
      </MIEntityCard>
    );
  }
}
