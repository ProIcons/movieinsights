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
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { getEntity, updateEntity, createEntity, reset } from './credit.reducer';
import { ICredit } from 'app/shared/model/credit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICreditUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CreditUpdate = (props: ICreditUpdateProps) => {
  const [movieId, setMovieId] = useState('0');
  const [personId, setPersonId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { creditEntity, movies, people, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/credit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovies();
    props.getPeople();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...creditEntity,
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
          <h2 id="movieInsightsApp.credit.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.credit.home.createOrEditLabel">Create or edit a Credit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : creditEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="credit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="credit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="creditIdLabel" for="credit-creditId">
                  <Translate contentKey="movieInsightsApp.credit.creditId">Credit Id</Translate>
                </Label>
                <AvField
                  id="credit-creditId"
                  type="text"
                  name="creditId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="personTmdbIdLabel" for="credit-personTmdbId">
                  <Translate contentKey="movieInsightsApp.credit.personTmdbId">Person Tmdb Id</Translate>
                </Label>
                <AvField
                  id="credit-personTmdbId"
                  type="string"
                  className="form-control"
                  name="personTmdbId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="movieTmdbIdLabel" for="credit-movieTmdbId">
                  <Translate contentKey="movieInsightsApp.credit.movieTmdbId">Movie Tmdb Id</Translate>
                </Label>
                <AvField
                  id="credit-movieTmdbId"
                  type="string"
                  className="form-control"
                  name="movieTmdbId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="roleLabel" for="credit-role">
                  <Translate contentKey="movieInsightsApp.credit.role">Role</Translate>
                </Label>
                <AvInput
                  id="credit-role"
                  type="select"
                  className="form-control"
                  name="role"
                  value={(!isNew && creditEntity.role) || 'ACTOR'}
                >
                  <option value="ACTOR">{translate('movieInsightsApp.CreditRole.ACTOR')}</option>
                  <option value="PRODUCER">{translate('movieInsightsApp.CreditRole.PRODUCER')}</option>
                  <option value="WRITER">{translate('movieInsightsApp.CreditRole.WRITER')}</option>
                  <option value="DIRECTOR">{translate('movieInsightsApp.CreditRole.DIRECTOR')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="credit-movie">
                  <Translate contentKey="movieInsightsApp.credit.movie">Movie</Translate>
                </Label>
                <AvInput id="credit-movie" type="select" className="form-control" name="movieId">
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
                <Label for="credit-person">
                  <Translate contentKey="movieInsightsApp.credit.person">Person</Translate>
                </Label>
                <AvInput id="credit-person" type="select" className="form-control" name="personId">
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
              <Button tag={Link} id="cancel-save" to="/credit" replace color="info">
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
  people: storeState.person.entities,
  creditEntity: storeState.credit.entity,
  loading: storeState.credit.loading,
  updating: storeState.credit.updating,
  updateSuccess: storeState.credit.updateSuccess,
});

const mapDispatchToProps = {
  getMovies,
  getPeople,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CreditUpdate);
