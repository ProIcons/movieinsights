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
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights-per-person.reducer';
import { IMovieInsightsPerPerson } from 'app/shared/model/movie-insights-per-person.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsPerPersonUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerPersonUpdate = (props: IMovieInsightsPerPersonUpdateProps) => {
  const [movieInsightsId, setMovieInsightsId] = useState('0');
  const [personId, setPersonId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieInsightsPerPersonEntity, movieInsights, people, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/movie-insights-per-person');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieInsights();
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
        ...movieInsightsPerPersonEntity,
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
          <h2 id="movieInsightsApp.movieInsightsPerPerson.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.home.createOrEditLabel">
              Create or edit a MovieInsightsPerPerson
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsPerPersonEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-per-person-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-per-person-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="asLabel" for="movie-insights-per-person-as">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.as">As</Translate>
                </Label>
                <AvInput
                  id="movie-insights-per-person-as"
                  type="select"
                  className="form-control"
                  name="as"
                  value={(!isNew && movieInsightsPerPersonEntity.as) || 'ACTOR'}
                >
                  <option value="ACTOR">{translate('movieInsightsApp.CreditRole.ACTOR')}</option>
                  <option value="PRODUCER">{translate('movieInsightsApp.CreditRole.PRODUCER')}</option>
                  <option value="WRITER">{translate('movieInsightsApp.CreditRole.WRITER')}</option>
                  <option value="DIRECTOR">{translate('movieInsightsApp.CreditRole.DIRECTOR')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-person-movieInsights">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.movieInsights">Movie Insights</Translate>
                </Label>
                <AvInput id="movie-insights-per-person-movieInsights" type="select" className="form-control" name="movieInsightsId">
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
                <Label for="movie-insights-per-person-person">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.person">Person</Translate>
                </Label>
                <AvInput id="movie-insights-per-person-person" type="select" className="form-control" name="personId" required>
                  {people
                    ? people.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/movie-insights-per-person" replace color="info">
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
  people: storeState.person.entities,
  movieInsightsPerPersonEntity: storeState.movieInsightsPerPerson.entity,
  loading: storeState.movieInsightsPerPerson.loading,
  updating: storeState.movieInsightsPerPerson.updating,
  updateSuccess: storeState.movieInsightsPerPerson.updateSuccess,
});

const mapDispatchToProps = {
  getMovieInsights,
  getPeople,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerPersonUpdate);
