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
import { getEntity, updateEntity, createEntity, reset } from './production-country.reducer';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductionCountryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionCountryUpdate = (props: IProductionCountryUpdateProps) => {
  const [moviesId, setMoviesId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { productionCountryEntity, movies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production-country');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productionCountryEntity,
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
          <h2 id="movieInsightsApp.productionCountry.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.productionCountry.home.createOrEditLabel">Create or edit a ProductionCountry</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionCountryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-country-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="production-country-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="production-country-name">
                  <Translate contentKey="movieInsightsApp.productionCountry.name">Name</Translate>
                </Label>
                <AvField
                  id="production-country-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="iso31661Label" for="production-country-iso31661">
                  <Translate contentKey="movieInsightsApp.productionCountry.iso31661">Iso 31661</Translate>
                </Label>
                <AvField
                  id="production-country-iso31661"
                  type="text"
                  name="iso31661"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/production-country" replace color="info">
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
  productionCountryEntity: storeState.productionCountry.entity,
  loading: storeState.productionCountry.loading,
  updating: storeState.productionCountry.updating,
  updateSuccess: storeState.productionCountry.updateSuccess,
});

const mapDispatchToProps = {
  getMovies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionCountryUpdate);
