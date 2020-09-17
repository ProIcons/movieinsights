import './MIMovieCard.scss';
import React, {SyntheticEvent} from "react";
import {CCard, CCardBody, CCardFooter, CCardHeader} from "@coreui/react";
import MILoadingCircle from "app/components/MILoadingCircle";
import {IMovie} from "app/models/IMovie.Model";
import numeral from "app/utils/numeral-utils";
import {defaultValue as movieDefaultValue} from "app/models/IMovie.Model";
import MIEntityNotFound from "app/components/MIEntityNotFound";
import {NavLink} from "react-router-dom";
import MIBaseLoadableCard, {
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import MIMovieInfoModal from "app/components/cards/MIMovieInfoModal";
import {MOVIE_POSTER_PLACEHOLDER, TMDB_BACKDROP_SIZE, TMDB_POSTER_SIZE} from "app/config/constants";
import {TmdbUtils} from "app/utils/tmdb-utils";
import {AppUtils} from "app/utils/app-utils";
import {TmdbEntityType} from "app/models/enumerations";
import {MIValueNumeralFormat} from "app/shared/enumerations/MIValueNumeralFormat";

export interface MIMovieCardProps extends MIBaseLoadableCardProps<IMovie> {
  type: MIMovieCardType;
}

export interface MIMovieCardState extends MIBaseLoadableCardState<IMovie> {
  modal: boolean;
}

export enum MIMovieCardType {
  MostRevenue = "Most Revenue Movie",
  LeastRevenue = "Least Revenue Movie",
  MostBudget = "Most Budget Movie",
  LeastBudget = "Least Budget Movie",
  MostVote = "Highest Rated Movie",
  LeastVote = "Lowest Rated Movie"
}

type MovieView = {
  numberFormat: MIValueNumeralFormat;
  value: number;
  valueText: string;
  title: string;
}

const getViewConfig = (type: MIMovieCardType, movie: IMovie): MovieView => {
  switch (type) {
    case MIMovieCardType.LeastRevenue:
    case MIMovieCardType.MostRevenue:
      return {
        numberFormat: MIValueNumeralFormat.Money,
        value: movie?.revenue,
        valueText: "Revenue",
        title: type
      }
    case MIMovieCardType.MostBudget:
    case MIMovieCardType.LeastBudget:
      return {
        numberFormat: MIValueNumeralFormat.Money,
        value: movie?.budget,
        valueText: "Budget",
        title: type
      }

    case MIMovieCardType.MostVote:
    case MIMovieCardType.LeastVote:
      return {
        numberFormat: MIValueNumeralFormat.Decimal,
        value: movie?.vote?.average,
        valueText: "Rating",
        title: type
      }
    default:
      return {
        numberFormat: undefined,
        value: undefined,
        title: undefined,
        valueText: undefined
      };
  }
}

const getFieldValue = (type: MovieView): string => {
  return numeral(type.value).format(type.numberFormat);
}
const LOADABLE_ENTITIES = {
  POSTER: "poster",
  BACKDROP: "backdrop"
}
export default class MIMovieCard extends MIBaseLoadableCard<MIMovieCardProps, MIMovieCardState, IMovie> {
  constructor(props) {
    super(props, movieDefaultValue);

    this.state = {
      ...this.state,
      modal: false
    }
  }

  private onclose = () => {
    this.setState({modal: false});
  }

  protected subEntitiesNames(): string[] {
    return Object.values(LOADABLE_ENTITIES);
  }

  private posterLoaded = () => {
    this.loaded(LOADABLE_ENTITIES.POSTER)
  }

  private backdropLoaded = () => {
    this.loaded(LOADABLE_ENTITIES.BACKDROP);
  }

  private renderBodyLinked() {
    return (
      <NavLink className="mi-entity-link"
               onClick={this.openModal}
               to={AppUtils.generateNavigationLink(this.state.entity)}>
        {this.renderCard()}
      </NavLink>);
  }

  private renderHeader = () => {
    const viewConfig = getViewConfig(this.props.type, this.state.entity);
    return (
      <div>
        {viewConfig.title}
      </div>
    );
  }

  private renderContent = () => {
    const jsx = [];
    if (!this.state.loaded) {
      jsx.push(
        <div key={1} className="mi-entity-loading">
          <MILoadingCircle/>
        </div>);
    } else if (!this.state.entity) {
      jsx.push(
        <div key={0} className="mi-entity-loading">
          <MIEntityNotFound type={TmdbEntityType.MOVIE}/>
        </div>
      )
    }
    const posterUrl = this.state.entity?.posterPath ? TmdbUtils.getPosterUrl(TMDB_POSTER_SIZE.W185, this.state.entity.posterPath) : MOVIE_POSTER_PLACEHOLDER;
    jsx.push(
      <React.Fragment key={2}>
        <div className="mi-movie-title-overlay">
          <div className="mi-movie-title font-weight-bold">{this.state.entity?.name}</div>
        </div>
        <div className="mi-movie-poster">
          <img onLoad={() => this.posterLoaded()} height={150} width={100}
               src={this.state.entityLoaded ? posterUrl : ""}/>
        </div>
      </React.Fragment>
    );
    return jsx;
  }

  private renderFooter = () => {
    const viewConfig = getViewConfig(this.props.type, this.state.entity);
    return (
      <>
        <div className="mi-movie-prop font-weight-bold">{viewConfig.valueText}</div>
        {this.state.entity ? (
          <div className="mi-movie-val text-value-md">{getFieldValue(viewConfig)}</div>
        ) : null}
      </>);
  }

  private renderCard() {
    const backdropUrl = this.state.entity?.backdropPath ? `url("${TmdbUtils.getBackdropUrl(TMDB_BACKDROP_SIZE.W300, this.state.entity?.backdropPath)}")` : '';
    return (
      <>
        <CCardHeader className="mi-entity-header">
          {this.renderHeader()}
        </CCardHeader>
        <CCardBody onLoad={() => this.backdropLoaded()} className="mi-movie-still"
                   style={this.state.entityLoaded ? {background: `#fff ${backdropUrl} center`} : {}}>
          {this.renderContent()}
        </CCardBody>
        <CCardFooter className="mi-movie-body">
          {this.renderFooter()}
        </CCardFooter>
      </>
    )
  }

  render() {
    return (
      <>
        <CCard className="mi-entity-container">
          {this.state.entity && this.state.entity !== movieDefaultValue ? this.renderBodyLinked() : this.renderCard()}
        </CCard>
        <MIMovieInfoModal onClose={this.onclose} open={this.state.modal} entity={this.state.entity}/>
      </>
    );
  }

  private openModal = (e: SyntheticEvent) => {
    this.setState({modal: true});
    e.preventDefault();
    e.stopPropagation();
    e.nativeEvent.stopImmediatePropagation();
  }
}
