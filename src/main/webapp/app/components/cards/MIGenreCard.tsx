import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {MIEntityCard} from "app/components/cards/MIEntityCard";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";
import {IGenre} from "app/models";
import {genreDefaultValue} from "app/models/defaultValues";
import {TranslatableComponent} from "app/components/util";
import _ from "lodash";

export interface MIGenreCardProps {
  genre: IGenre;
  movieCount: number
  valueType: MIValueType;
  coProducing?: boolean;
}

export class MIGenreCard extends TranslatableComponent<MIGenreCardProps, any> {
  constructor(props) {
    super(props,"genre");
  }
  render() {
    return (
      <MIEntityCard
        entityType={TmdbEntityType.GENRE}
        valueType={this.props.valueType}
        defaultEntity={genreDefaultValue}
        movieCount={this.props.movieCount}
        entity={this.props.genre}
        isCooperative={this.props.coProducing}
        customName={this.getTranslation(`translations.${_.camelCase(this.props.genre.name)}`)}
      >
        <FontAwesomeIcon className="text-info" icon={"theater-masks"} size={"5x"}/>
      </MIEntityCard>
    );
  }
}
