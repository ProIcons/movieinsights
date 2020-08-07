import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-insights.reducer';
import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsDetail = (props: IMovieInsightsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieInsightsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movieInsights.detail.title">MovieInsights</Translate> [<b>{movieInsightsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="averageRating">
              <Translate contentKey="movieInsightsApp.movieInsights.averageRating">Average Rating</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.averageRating}</dd>
          <dt>
            <span id="averageBudget">
              <Translate contentKey="movieInsightsApp.movieInsights.averageBudget">Average Budget</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.averageBudget}</dd>
          <dt>
            <span id="averageRevenue">
              <Translate contentKey="movieInsightsApp.movieInsights.averageRevenue">Average Revenue</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.averageRevenue}</dd>
          <dt>
            <span id="totalMovies">
              <Translate contentKey="movieInsightsApp.movieInsights.totalMovies">Total Movies</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.totalMovies}</dd>
          <dt>
            <span id="mostPopularGenreMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenreMovieCount">Most Popular Genre Movie Count</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularGenreMovieCount}</dd>
          <dt>
            <span id="mostPopularActorMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActorMovieCount">Most Popular Actor Movie Count</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularActorMovieCount}</dd>
          <dt>
            <span id="mostPopularWriterMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriterMovieCount">Most Popular Writer Movie Count</Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularWriterMovieCount}</dd>
          <dt>
            <span id="mostPopularProducerMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducerMovieCount">
                Most Popular Producer Movie Count
              </Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProducerMovieCount}</dd>
          <dt>
            <span id="mostPopularDirectorMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirectorMovieCount">
                Most Popular Director Movie Count
              </Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularDirectorMovieCount}</dd>
          <dt>
            <span id="mostPopularProductionCompanyMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompanyMovieCount">
                Most Popular Production Company Movie Count
              </Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProductionCompanyMovieCount}</dd>
          <dt>
            <span id="mostPopularProductionCountryMovieCount">
              <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountryMovieCount">
                Most Popular Production Country Movie Count
              </Translate>
            </span>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProductionCountryMovieCount}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.highestRatedMovie">Highest Rated Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.highestRatedMovieId ? movieInsightsEntity.highestRatedMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.lowestRatedMovie">Lowest Rated Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.lowestRatedMovieId ? movieInsightsEntity.lowestRatedMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.highestBudgetMovie">Highest Budget Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.highestBudgetMovieId ? movieInsightsEntity.highestBudgetMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.lowestBudgetMovie">Lowest Budget Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.lowestBudgetMovieId ? movieInsightsEntity.lowestBudgetMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.highestRevenueMovie">Highest Revenue Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.highestRevenueMovieId ? movieInsightsEntity.highestRevenueMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.lowestRevenueMovie">Lowest Revenue Movie</Translate>
          </dt>
          <dd>{movieInsightsEntity.lowestRevenueMovieId ? movieInsightsEntity.lowestRevenueMovieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenre">Most Popular Genre</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularGenreId ? movieInsightsEntity.mostPopularGenreId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActor">Most Popular Actor</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularActorId ? movieInsightsEntity.mostPopularActorId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducer">Most Popular Producer</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProducerId ? movieInsightsEntity.mostPopularProducerId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriter">Most Popular Writer</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularWriterId ? movieInsightsEntity.mostPopularWriterId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirector">Most Popular Director</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularDirectorId ? movieInsightsEntity.mostPopularDirectorId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountry">Most Popular Production Country</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProductionCountryId ? movieInsightsEntity.mostPopularProductionCountryId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompany">Most Popular Production Company</Translate>
          </dt>
          <dd>{movieInsightsEntity.mostPopularProductionCompanyId ? movieInsightsEntity.mostPopularProductionCompanyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-insights" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-insights/${movieInsightsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieInsights }: IRootState) => ({
  movieInsightsEntity: movieInsights.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsDetail);
