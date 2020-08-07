import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { getEntities as getMovieInsights } from 'app/entities/movie-insights/movie-insights.reducer';
import { IMovieInsightsPerCountry } from 'app/shared/model/movie-insights-per-country.model';
import { getEntities as getMovieInsightsPerCountries } from 'app/entities/movie-insights-per-country/movie-insights-per-country.reducer';
import { IMovieInsightsPerCompany } from 'app/shared/model/movie-insights-per-company.model';
import { getEntities as getMovieInsightsPerCompanies } from 'app/entities/movie-insights-per-company/movie-insights-per-company.reducer';
import { IMovieInsightsPerPerson } from 'app/shared/model/movie-insights-per-person.model';
import { getEntities as getMovieInsightsPerPeople } from 'app/entities/movie-insights-per-person/movie-insights-per-person.reducer';
import { IMovieInsightsPerGenre } from 'app/shared/model/movie-insights-per-genre.model';
import { getEntities as getMovieInsightsPerGenres } from 'app/entities/movie-insights-per-genre/movie-insights-per-genre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights-per-year.reducer';
import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsPerYearUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerYearUpdate = (props: IMovieInsightsPerYearUpdateProps) => {
  const [movieInsightsId, setMovieInsightsId] = useState('0');
  const [movieInsightsPerCountryId, setMovieInsightsPerCountryId] = useState('0');
  const [movieInsightsPerCompanyId, setMovieInsightsPerCompanyId] = useState('0');
  const [movieInsightsPerPersonId, setMovieInsightsPerPersonId] = useState('0');
  const [movieInsightsPerGenreId, setMovieInsightsPerGenreId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {
    movieInsightsPerYearEntity,
    movieInsights,
    movieInsightsPerCountries,
    movieInsightsPerCompanies,
    movieInsightsPerPeople,
    movieInsightsPerGenres,
    loading,
    updating,
  } = props;

  const handleClose = () => {
    props.history.push('/movie-insights-per-year');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieInsights();
    props.getMovieInsightsPerCountries();
    props.getMovieInsightsPerCompanies();
    props.getMovieInsightsPerPeople();
    props.getMovieInsightsPerGenres();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieInsightsPerYearEntity,
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
          <h2 id="movieInsightsApp.movieInsightsPerYear.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsightsPerYear.home.createOrEditLabel">
              Create or edit a MovieInsightsPerYear
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsPerYearEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-per-year-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-per-year-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="yearLabel" for="movie-insights-per-year-year">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.year">Year</Translate>
                </Label>
                <AvField
                  id="movie-insights-per-year-year"
                  type="string"
                  className="form-control"
                  name="year"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-year-movieInsights">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsights">Movie Insights</Translate>
                </Label>
                <AvInput id="movie-insights-per-year-movieInsights" type="select" className="form-control" name="movieInsightsId">
                  <option value="" key="0" />
                  {movieInsights
                    ? movieInsights.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-year-movieInsightsPerCountry">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCountry">
                    Movie Insights Per Country
                  </Translate>
                </Label>
                <AvInput
                  id="movie-insights-per-year-movieInsightsPerCountry"
                  type="select"
                  className="form-control"
                  name="movieInsightsPerCountryId"
                >
                  <option value="" key="0" />
                  {movieInsightsPerCountries
                    ? movieInsightsPerCountries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-year-movieInsightsPerCompany">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCompany">
                    Movie Insights Per Company
                  </Translate>
                </Label>
                <AvInput
                  id="movie-insights-per-year-movieInsightsPerCompany"
                  type="select"
                  className="form-control"
                  name="movieInsightsPerCompanyId"
                >
                  <option value="" key="0" />
                  {movieInsightsPerCompanies
                    ? movieInsightsPerCompanies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-year-movieInsightsPerPerson">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerPerson">Movie Insights Per Person</Translate>
                </Label>
                <AvInput
                  id="movie-insights-per-year-movieInsightsPerPerson"
                  type="select"
                  className="form-control"
                  name="movieInsightsPerPersonId"
                >
                  <option value="" key="0" />
                  {movieInsightsPerPeople
                    ? movieInsightsPerPeople.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-year-movieInsightsPerGenre">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerGenre">Movie Insights Per Genre</Translate>
                </Label>
                <AvInput
                  id="movie-insights-per-year-movieInsightsPerGenre"
                  type="select"
                  className="form-control"
                  name="movieInsightsPerGenreId"
                >
                  <option value="" key="0" />
                  {movieInsightsPerGenres
                    ? movieInsightsPerGenres.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/movie-insights-per-year" replace color="info">
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
  movieInsights: storeState.movieInsights.entities,
  movieInsightsPerCountries: storeState.movieInsightsPerCountry.entities,
  movieInsightsPerCompanies: storeState.movieInsightsPerCompany.entities,
  movieInsightsPerPeople: storeState.movieInsightsPerPerson.entities,
  movieInsightsPerGenres: storeState.movieInsightsPerGenre.entities,
  movieInsightsPerYearEntity: storeState.movieInsightsPerYear.entity,
  loading: storeState.movieInsightsPerYear.loading,
  updating: storeState.movieInsightsPerYear.updating,
  updateSuccess: storeState.movieInsightsPerYear.updateSuccess,
});

const mapDispatchToProps = {
  getMovieInsights,
  getMovieInsightsPerCountries,
  getMovieInsightsPerCompanies,
  getMovieInsightsPerPeople,
  getMovieInsightsPerGenres,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerYearUpdate);
