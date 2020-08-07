import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-insights-per-genre.reducer';
import { IMovieInsightsPerGenre } from 'app/shared/model/movie-insights-per-genre.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerGenreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerGenreDetail = (props: IMovieInsightsPerGenreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieInsightsPerGenreEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.detail.title">MovieInsightsPerGenre</Translate> [
          <b>{movieInsightsPerGenreEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.movieInsights">Movie Insights</Translate>
          </dt>
          <dd>{movieInsightsPerGenreEntity.movieInsightsId ? movieInsightsPerGenreEntity.movieInsightsId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.genre">Genre</Translate>
          </dt>
          <dd>{movieInsightsPerGenreEntity.genreId ? movieInsightsPerGenreEntity.genreId : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-insights-per-genre" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-insights-per-genre/${movieInsightsPerGenreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieInsightsPerGenre }: IRootState) => ({
  movieInsightsPerGenreEntity: movieInsightsPerGenre.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerGenreDetail);
