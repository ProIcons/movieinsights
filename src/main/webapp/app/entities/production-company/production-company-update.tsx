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
import { getEntity, updateEntity, createEntity, reset } from './production-company.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductionCompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionCompanyUpdate = (props: IProductionCompanyUpdateProps) => {
  const [moviesId, setMoviesId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { productionCompanyEntity, movies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production-company');
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
        ...productionCompanyEntity,
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
          <h2 id="movieInsightsApp.productionCompany.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.productionCompany.home.createOrEditLabel">Create or edit a ProductionCompany</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionCompanyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-company-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="production-company-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="production-company-name">
                  <Translate contentKey="movieInsightsApp.productionCompany.name">Name</Translate>
                </Label>
                <AvField
                  id="production-company-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tmdbIdLabel" for="production-company-tmdbId">
                  <Translate contentKey="movieInsightsApp.productionCompany.tmdbId">Tmdb Id</Translate>
                </Label>
                <AvField
                  id="production-company-tmdbId"
                  type="string"
                  className="form-control"
                  name="tmdbId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="logoPathLabel" for="production-company-logoPath">
                  <Translate contentKey="movieInsightsApp.productionCompany.logoPath">Logo Path</Translate>
                </Label>
                <AvField id="production-company-logoPath" type="text" name="logoPath" />
              </AvGroup>
              <AvGroup>
                <Label id="originCountryLabel" for="production-company-originCountry">
                  <Translate contentKey="movieInsightsApp.productionCompany.originCountry">Origin Country</Translate>
                </Label>
                <AvField id="production-company-originCountry" type="text" name="originCountry" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/production-company" replace color="info">
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
  productionCompanyEntity: storeState.productionCompany.entity,
  loading: storeState.productionCompany.loading,
  updating: storeState.productionCompany.updating,
  updateSuccess: storeState.productionCompany.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ProductionCompanyUpdate);
