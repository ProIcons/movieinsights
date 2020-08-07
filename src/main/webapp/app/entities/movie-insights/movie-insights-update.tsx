import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { IGenre } from 'app/shared/model/genre.model';
import { getEntities as getGenres } from 'app/entities/genre/genre.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { getEntities as getProductionCountries } from 'app/entities/production-country/production-country.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { getEntities as getProductionCompanies } from 'app/entities/production-company/production-company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights.reducer';
import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsUpdate = (props: IMovieInsightsUpdateProps) => {
  const [highestRatedMovieId, setHighestRatedMovieId] = useState('0');
  const [lowestRatedMovieId, setLowestRatedMovieId] = useState('0');
  const [highestBudgetMovieId, setHighestBudgetMovieId] = useState('0');
  const [lowestBudgetMovieId, setLowestBudgetMovieId] = useState('0');
  const [highestRevenueMovieId, setHighestRevenueMovieId] = useState('0');
  const [lowestRevenueMovieId, setLowestRevenueMovieId] = useState('0');
  const [mostPopularGenreId, setMostPopularGenreId] = useState('0');
  const [mostPopularActorId, setMostPopularActorId] = useState('0');
  const [mostPopularProducerId, setMostPopularProducerId] = useState('0');
  const [mostPopularWriterId, setMostPopularWriterId] = useState('0');
  const [mostPopularDirectorId, setMostPopularDirectorId] = useState('0');
  const [mostPopularProductionCountryId, setMostPopularProductionCountryId] = useState('0');
  const [mostPopularProductionCompanyId, setMostPopularProductionCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieInsightsEntity, movies, genres, people, productionCountries, productionCompanies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/movie-insights');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovies();
    props.getGenres();
    props.getPeople();
    props.getProductionCountries();
    props.getProductionCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieInsightsEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieInsightsApp.movieInsights.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsights.home.createOrEditLabel">Create or edit a MovieInsights</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="averageRatingLabel" for="movie-insights-averageRating">
                  <Translate contentKey="movieInsightsApp.movieInsights.averageRating">Average Rating</Translate>
                </Label>
                <AvField
                  id="movie-insights-averageRating"
                  type="string"
                  className="form-control"
                  name="averageRating"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="averageBudgetLabel" for="movie-insights-averageBudget">
                  <Translate contentKey="movieInsightsApp.movieInsights.averageBudget">Average Budget</Translate>
                </Label>
                <AvField
                  id="movie-insights-averageBudget"
                  type="string"
                  className="form-control"
                  name="averageBudget"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="averageRevenueLabel" for="movie-insights-averageRevenue">
                  <Translate contentKey="movieInsightsApp.movieInsights.averageRevenue">Average Revenue</Translate>
                </Label>
                <AvField
                  id="movie-insights-averageRevenue"
                  type="string"
                  className="form-control"
                  name="averageRevenue"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalMoviesLabel" for="movie-insights-totalMovies">
                  <Translate contentKey="movieInsightsApp.movieInsights.totalMovies">Total Movies</Translate>
                </Label>
                <AvField
                  id="movie-insights-totalMovies"
                  type="string"
                  className="form-control"
                  name="totalMovies"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularGenreMovieCountLabel" for="movie-insights-mostPopularGenreMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenreMovieCount">
                    Most Popular Genre Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularGenreMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularGenreMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularActorMovieCountLabel" for="movie-insights-mostPopularActorMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActorMovieCount">
                    Most Popular Actor Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularActorMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularActorMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularWriterMovieCountLabel" for="movie-insights-mostPopularWriterMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriterMovieCount">
                    Most Popular Writer Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularWriterMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularWriterMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularProducerMovieCountLabel" for="movie-insights-mostPopularProducerMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducerMovieCount">
                    Most Popular Producer Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularProducerMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularProducerMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularDirectorMovieCountLabel" for="movie-insights-mostPopularDirectorMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirectorMovieCount">
                    Most Popular Director Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularDirectorMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularDirectorMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularProductionCompanyMovieCountLabel" for="movie-insights-mostPopularProductionCompanyMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompanyMovieCount">
                    Most Popular Production Company Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularProductionCompanyMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularProductionCompanyMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mostPopularProductionCountryMovieCountLabel" for="movie-insights-mostPopularProductionCountryMovieCount">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountryMovieCount">
                    Most Popular Production Country Movie Count
                  </Translate>
                </Label>
                <AvField
                  id="movie-insights-mostPopularProductionCountryMovieCount"
                  type="string"
                  className="form-control"
                  name="mostPopularProductionCountryMovieCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-highestRatedMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.highestRatedMovie">Highest Rated Movie</Translate>
                </Label>
                <AvInput id="movie-insights-highestRatedMovie" type="select" className="form-control" name="highestRatedMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-lowestRatedMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestRatedMovie">Lowest Rated Movie</Translate>
                </Label>
                <AvInput id="movie-insights-lowestRatedMovie" type="select" className="form-control" name="lowestRatedMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-highestBudgetMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.highestBudgetMovie">Highest Budget Movie</Translate>
                </Label>
                <AvInput id="movie-insights-highestBudgetMovie" type="select" className="form-control" name="highestBudgetMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-lowestBudgetMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestBudgetMovie">Lowest Budget Movie</Translate>
                </Label>
                <AvInput id="movie-insights-lowestBudgetMovie" type="select" className="form-control" name="lowestBudgetMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-highestRevenueMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.highestRevenueMovie">Highest Revenue Movie</Translate>
                </Label>
                <AvInput id="movie-insights-highestRevenueMovie" type="select" className="form-control" name="highestRevenueMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-lowestRevenueMovie">
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestRevenueMovie">Lowest Revenue Movie</Translate>
                </Label>
                <AvInput id="movie-insights-lowestRevenueMovie" type="select" className="form-control" name="lowestRevenueMovieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularGenre">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenre">Most Popular Genre</Translate>
                </Label>
                <AvInput id="movie-insights-mostPopularGenre" type="select" className="form-control" name="mostPopularGenreId">
                  <option value="" key="0" />
                  {genres
                    ? genres.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularActor">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActor">Most Popular Actor</Translate>
                </Label>
                <AvInput id="movie-insights-mostPopularActor" type="select" className="form-control" name="mostPopularActorId">
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularProducer">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducer">Most Popular Producer</Translate>
                </Label>
                <AvInput id="movie-insights-mostPopularProducer" type="select" className="form-control" name="mostPopularProducerId">
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularWriter">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriter">Most Popular Writer</Translate>
                </Label>
                <AvInput id="movie-insights-mostPopularWriter" type="select" className="form-control" name="mostPopularWriterId">
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularDirector">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirector">Most Popular Director</Translate>
                </Label>
                <AvInput id="movie-insights-mostPopularDirector" type="select" className="form-control" name="mostPopularDirectorId">
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularProductionCountry">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountry">
                    Most Popular Production Country
                  </Translate>
                </Label>
                <AvInput
                  id="movie-insights-mostPopularProductionCountry"
                  type="select"
                  className="form-control"
                  name="mostPopularProductionCountryId"
                >
                  <option value="" key="0" />
                  {productionCountries
                    ? productionCountries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-mostPopularProductionCompany">
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompany">
                    Most Popular Production Company
                  </Translate>
                </Label>
                <AvInput
                  id="movie-insights-mostPopularProductionCompany"
                  type="select"
                  className="form-control"
                  name="mostPopularProductionCompanyId"
                >
                  <option value="" key="0" />
                  {productionCompanies
                    ? productionCompanies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/movie-insights" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  movies: storeState.movie.entities,
  genres: storeState.genre.entities,
  people: storeState.person.entities,
  productionCountries: storeState.productionCountry.entities,
  productionCompanies: storeState.productionCompany.entities,
  movieInsightsEntity: storeState.movieInsights.entity,
  loading: storeState.movieInsights.loading,
  updating: storeState.movieInsights.updating,
  updateSuccess: storeState.movieInsights.updateSuccess,
});

const mapDispatchToProps = {
  getMovies,
  getGenres,
  getPeople,
  getProductionCountries,
  getProductionCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsUpdate);
