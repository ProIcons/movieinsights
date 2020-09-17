import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import MIEntityCard from "app/components/cards/MIEntityCard";
import {IGenre} from "app/models/IGenre.Model";
import {defaultValue as genreDefaultValue} from "app/models/IGenre.Model";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";

export interface MIGenreCardProps {
  genre: IGenre;
  movieCount: number
  valueType: MIValueType;
  coProducing?: boolean;
}

export default class MIGenreCard extends React.Component<MIGenreCardProps, any> {
  render() {
    return (
      <MIEntityCard
        entityType={TmdbEntityType.GENRE}
        valueType={this.props.valueType}
        defaultEntity={genreDefaultValue}
        movieCount={this.props.movieCount}
        entity={this.props.genre}
        isCooperative={this.props.coProducing}
      >
        <FontAwesomeIcon className="text-info" icon={"theater-masks"} size={"5x"}/>
      </MIEntityCard>
    );
  }
}
