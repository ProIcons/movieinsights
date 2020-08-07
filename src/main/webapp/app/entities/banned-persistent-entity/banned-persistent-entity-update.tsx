import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBannedEntity } from 'app/shared/model/banned-entity.model';
import { getEntities as getBannedEntities } from 'app/entities/banned-entity/banned-entity.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { getEntities as getProductionCompanies } from 'app/entities/production-company/production-company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './banned-persistent-entity.reducer';
import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBannedPersistentEntityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedPersistentEntityUpdate = (props: IBannedPersistentEntityUpdateProps) => {
  const [bannedEntityId, setBannedEntityId] = useState('0');
  const [movieId, setMovieId] = useState('0');
  const [personId, setPersonId] = useState('0');
  const [productionCompanyId, setProductionCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bannedPersistentEntityEntity, bannedEntities, movies, people, productionCompanies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/banned-persistent-entity');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBannedEntities();
    props.getMovies();
    props.getPeople();
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
        ...bannedPersistentEntityEntity,
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
          <h2 id="movieInsightsApp.bannedPersistentEntity.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.bannedPersistentEntity.home.createOrEditLabel">
              Create or edit a BannedPersistentEntity
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bannedPersistentEntityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="banned-persistent-entity-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="banned-persistent-entity-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="reasonLabel" for="banned-persistent-entity-reason">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reason">Reason</Translate>
                </Label>
                <AvInput
                  id="banned-persistent-entity-reason"
                  type="select"
                  className="form-control"
                  name="reason"
                  value={(!isNew && bannedPersistentEntityEntity.reason) || 'UNDEFINED'}
                >
                  <option value="UNDEFINED">{translate('movieInsightsApp.BanReason.UNDEFINED')}</option>
                  <option value="NOBUDGET">{translate('movieInsightsApp.BanReason.NOBUDGET')}</option>
                  <option value="NOREVENUE">{translate('movieInsightsApp.BanReason.NOREVENUE')}</option>
                  <option value="NORELEASEDATE">{translate('movieInsightsApp.BanReason.NORELEASEDATE')}</option>
                  <option value="NOVOTE">{translate('movieInsightsApp.BanReason.NOVOTE')}</option>
                  <option value="CUSTOM">{translate('movieInsightsApp.BanReason.CUSTOM')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="reasonTextLabel" for="banned-persistent-entity-reasonText">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reasonText">Reason Text</Translate>
                </Label>
                <AvField id="banned-persistent-entity-reasonText" type="text" name="reasonText" />
              </AvGroup>
              <AvGroup>
                <Label id="timestampLabel" for="banned-persistent-entity-timestamp">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.timestamp">Timestamp</Translate>
                </Label>
                <AvField
                  id="banned-persistent-entity-timestamp"
                  type="date"
                  className="form-control"
                  name="timestamp"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="banned-persistent-entity-movie">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.movie">Movie</Translate>
                </Label>
                <AvInput id="banned-persistent-entity-movie" type="select" className="form-control" name="movieId">
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
                <Label for="banned-persistent-entity-person">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.person">Person</Translate>
                </Label>
                <AvInput id="banned-persistent-entity-person" type="select" className="form-control" name="personId">
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
                <Label for="banned-persistent-entity-productionCompany">
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.productionCompany">Production Company</Translate>
                </Label>
                <AvInput id="banned-persistent-entity-productionCompany" type="select" className="form-control" name="productionCompanyId">
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
              <Button tag={Link} id="cancel-save" to="/banned-persistent-entity" replace color="info">
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
  bannedEntities: storeState.bannedEntity.entities,
  movies: storeState.movie.entities,
  people: storeState.person.entities,
  productionCompanies: storeState.productionCompany.entities,
  bannedPersistentEntityEntity: storeState.bannedPersistentEntity.entity,
  loading: storeState.bannedPersistentEntity.loading,
  updating: storeState.bannedPersistentEntity.updating,
  updateSuccess: storeState.bannedPersistentEntity.updateSuccess,
});

const mapDispatchToProps = {
  getBannedEntities,
  getMovies,
  getPeople,
  getProductionCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedPersistentEntityUpdate);
