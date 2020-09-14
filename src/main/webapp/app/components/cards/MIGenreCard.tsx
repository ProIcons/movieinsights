import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import MIEntityCard, {MIValueType} from "app/components/cards/MIEntityCard";
import {IGenre} from "app/models/IGenre.Model";
import {defaultValue as genreDefaultValue} from "app/models/IGenre.Model";
import {TmdbEntityType} from "app/models/enumerations";

export interface MIGenreCardProps {
  genre: IGenre;
  movieCount: number
  valueType: MIValueType;
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
      >
        <FontAwesomeIcon className="text-info" icon={"theater-masks"} size={"5x"}/>
      </MIEntityCard>
    );
  }
}
