import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { getEntities as getMovieInsights } from 'app/entities/movie-insights/movie-insights.reducer';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { getEntities as getProductionCountries } from 'app/entities/production-country/production-country.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights-per-country.reducer';
import { IMovieInsightsPerCountry } from 'app/shared/model/movie-insights-per-country.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsPerCountryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerCountryUpdate = (props: IMovieInsightsPerCountryUpdateProps) => {
  const [movieInsightsId, setMovieInsightsId] = useState('0');
  const [countryId, setCountryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieInsightsPerCountryEntity, movieInsights, productionCountries, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/movie-insights-per-country');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieInsights();
    props.getProductionCountries();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieInsightsPerCountryEntity,
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
          <h2 id="movieInsightsApp.movieInsightsPerCountry.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.home.createOrEditLabel">
              Create or edit a MovieInsightsPerCountry
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsPerCountryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-per-country-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-per-country-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="movie-insights-per-country-movieInsights">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.movieInsights">Movie Insights</Translate>
                </Label>
                <AvInput id="movie-insights-per-country-movieInsights" type="select" className="form-control" name="movieInsightsId">
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
                <Label for="movie-insights-per-country-country">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.country">Country</Translate>
                </Label>
                <AvInput id="movie-insights-per-country-country" type="select" className="form-control" name="countryId" required>
                  {productionCountries
                    ? productionCountries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/movie-insights-per-country" replace color="info">
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
  productionCountries: storeState.productionCountry.entities,
  movieInsightsPerCountryEntity: storeState.movieInsightsPerCountry.entity,
  loading: storeState.movieInsightsPerCountry.loading,
  updating: storeState.movieInsightsPerCountry.updating,
  updateSuccess: storeState.movieInsightsPerCountry.updateSuccess,
});

const mapDispatchToProps = {
  getMovieInsights,
  getProductionCountries,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerCountryUpdate);
