import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieDetail = (props: IMovieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movie.detail.title">Movie</Translate> [<b>{movieEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.movie.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{movieEntity.tmdbId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieInsightsApp.movie.name">Name</Translate>
            </span>
          </dt>
          <dd>{movieEntity.name}</dd>
          <dt>
            <span id="tagline">
              <Translate contentKey="movieInsightsApp.movie.tagline">Tagline</Translate>
            </span>
          </dt>
          <dd>{movieEntity.tagline}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="movieInsightsApp.movie.description">Description</Translate>
            </span>
          </dt>
          <dd>{movieEntity.description}</dd>
          <dt>
            <span id="revenue">
              <Translate contentKey="movieInsightsApp.movie.revenue">Revenue</Translate>
            </span>
          </dt>
          <dd>{movieEntity.revenue}</dd>
          <dt>
            <span id="budget">
              <Translate contentKey="movieInsightsApp.movie.budget">Budget</Translate>
            </span>
          </dt>
          <dd>{movieEntity.budget}</dd>
          <dt>
            <span id="imdbId">
              <Translate contentKey="movieInsightsApp.movie.imdbId">Imdb Id</Translate>
            </span>
          </dt>
          <dd>{movieEntity.imdbId}</dd>
          <dt>
            <span id="popularity">
              <Translate contentKey="movieInsightsApp.movie.popularity">Popularity</Translate>
            </span>
          </dt>
          <dd>{movieEntity.popularity}</dd>
          <dt>
            <span id="runtime">
              <Translate contentKey="movieInsightsApp.movie.runtime">Runtime</Translate>
            </span>
          </dt>
          <dd>{movieEntity.runtime}</dd>
          <dt>
            <span id="posterPath">
              <Translate contentKey="movieInsightsApp.movie.posterPath">Poster Path</Translate>
            </span>
          </dt>
          <dd>{movieEntity.posterPath}</dd>
          <dt>
            <span id="backdropPath">
              <Translate contentKey="movieInsightsApp.movie.backdropPath">Backdrop Path</Translate>
            </span>
          </dt>
          <dd>{movieEntity.backdropPath}</dd>
          <dt>
            <span id="releaseDate">
              <Translate contentKey="movieInsightsApp.movie.releaseDate">Release Date</Translate>
            </span>
          </dt>
          <dd>
            {movieEntity.releaseDate ? <TextFormat value={movieEntity.releaseDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movie.vote">Vote</Translate>
          </dt>
          <dd>{movieEntity.voteId ? movieEntity.voteId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movie.companies">Companies</Translate>
          </dt>
          <dd>
            {movieEntity.companies
              ? movieEntity.companies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.companies && i === movieEntity.companies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movie.countries">Countries</Translate>
          </dt>
          <dd>
            {movieEntity.countries
              ? movieEntity.countries.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.countries && i === movieEntity.countries.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movie.genres">Genres</Translate>
          </dt>
          <dd>
            {movieEntity.genres
              ? movieEntity.genres.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.genres && i === movieEntity.genres.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/movie" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie/${movieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movie }: IRootState) => ({
  movieEntity: movie.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieDetail);
