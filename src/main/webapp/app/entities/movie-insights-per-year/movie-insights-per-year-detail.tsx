import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-insights-per-year.reducer';
import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerYearDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerYearDetail = (props: IMovieInsightsPerYearDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieInsightsPerYearEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movieInsightsPerYear.detail.title">MovieInsightsPerYear</Translate> [
          <b>{movieInsightsPerYearEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="year">
              <Translate contentKey="movieInsightsApp.movieInsightsPerYear.year">Year</Translate>
            </span>
          </dt>
          <dd>{movieInsightsPerYearEntity.year}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsights">Movie Insights</Translate>
          </dt>
          <dd>{movieInsightsPerYearEntity.movieInsightsId ? movieInsightsPerYearEntity.movieInsightsId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCountry">Movie Insights Per Country</Translate>
          </dt>
          <dd>{movieInsightsPerYearEntity.movieInsightsPerCountryId ? movieInsightsPerYearEntity.movieInsightsPerCountryId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCompany">Movie Insights Per Company</Translate>
          </dt>
          <dd>{movieInsightsPerYearEntity.movieInsightsPerCompanyId ? movieInsightsPerYearEntity.movieInsightsPerCompanyId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerPerson">Movie Insights Per Person</Translate>
          </dt>
          <dd>{movieInsightsPerYearEntity.movieInsightsPerPersonId ? movieInsightsPerYearEntity.movieInsightsPerPersonId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerGenre">Movie Insights Per Genre</Translate>
          </dt>
          <dd>{movieInsightsPerYearEntity.movieInsightsPerGenreId ? movieInsightsPerYearEntity.movieInsightsPerGenreId : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-insights-per-year" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-insights-per-year/${movieInsightsPerYearEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieInsightsPerYear }: IRootState) => ({
  movieInsightsPerYearEntity: movieInsightsPerYear.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerYearDetail);
