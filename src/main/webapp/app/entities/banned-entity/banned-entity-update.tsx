import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './banned-entity.reducer';
import { IBannedEntity } from 'app/shared/model/banned-entity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBannedEntityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedEntityUpdate = (props: IBannedEntityUpdateProps) => {
  const [bannedPersistentEntityId, setBannedPersistentEntityId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bannedEntityEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/banned-entity');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bannedEntityEntity,
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
          <h2 id="movieInsightsApp.bannedEntity.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.bannedEntity.home.createOrEditLabel">Create or edit a BannedEntity</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bannedEntityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="banned-entity-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="banned-entity-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tmdbIdLabel" for="banned-entity-tmdbId">
                  <Translate contentKey="movieInsightsApp.bannedEntity.tmdbId">Tmdb Id</Translate>
                </Label>
                <AvField
                  id="banned-entity-tmdbId"
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
                <Label id="imdbLabel" for="banned-entity-imdb">
                  <Translate contentKey="movieInsightsApp.bannedEntity.imdb">Imdb</Translate>
                </Label>
                <AvField
                  id="banned-entity-imdb"
                  type="text"
                  name="imdb"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="banned-entity-type">
                  <Translate contentKey="movieInsightsApp.bannedEntity.type">Type</Translate>
                </Label>
                <AvInput
                  id="banned-entity-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && bannedEntityEntity.type) || 'MOVIE'}
                >
                  <option value="MOVIE">{translate('movieInsightsApp.TmdbEntityType.MOVIE')}</option>
                  <option value="PERSON">{translate('movieInsightsApp.TmdbEntityType.PERSON')}</option>
                  <option value="COMPANY">{translate('movieInsightsApp.TmdbEntityType.COMPANY')}</option>
                  <option value="GENRE">{translate('movieInsightsApp.TmdbEntityType.GENRE')}</option>
                  <option value="COUNTRY">{translate('movieInsightsApp.TmdbEntityType.COUNTRY')}</option>
                  <option value="CREDIT">{translate('movieInsightsApp.TmdbEntityType.CREDIT')}</option>
                  <option value="VOTE">{translate('movieInsightsApp.TmdbEntityType.VOTE')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/banned-entity" replace color="info">
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
  bannedEntityEntity: storeState.bannedEntity.entity,
  loading: storeState.bannedEntity.loading,
  updating: storeState.bannedEntity.updating,
  updateSuccess: storeState.bannedEntity.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedEntityUpdate);
