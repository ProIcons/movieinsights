import React, {Component} from "react";

import {MIEntityCard} from "app/components/cards/MIEntityCard";
import CIcon from "@coreui/icons-react";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";
import {IProductionCountry} from "app/models";
import {productionCountryDefaultValue} from "app/models/defaultValues";
import {TranslatableComponent} from "app/components/util";

export interface MICountryCardProps {
  country: IProductionCountry;
  movieCount: number;
  coProducing?: boolean;
  valueType: MIValueType;
}

export class MICountryCard extends TranslatableComponent<MICountryCardProps, any> {
  constructor(props) {
    super(props,"productionCountry");
  }

  render() {
    return (
      <MIEntityCard
        entityType={TmdbEntityType.COUNTRY}
        valueType={this.props.valueType}
        isCooperative={this.props.coProducing}
        defaultEntity={productionCountryDefaultValue}
        movieCount={this.props.movieCount}
        entity={this.props.country}
        customName={this.getTranslation(`translations.${this.props.country.iso31661.toUpperCase()}`)}
      >
        {this.props.country && this.props.country !== productionCountryDefaultValue ? (
          <CIcon name={`cif-${this.props.country?.iso31661.toLowerCase()}`} size={'5xl'}/>
        ) : null
        }
      </MIEntityCard>
    );
  }
}
