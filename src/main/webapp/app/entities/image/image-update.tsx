import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVote } from 'app/shared/model/vote.model';
import { getEntities as getVotes } from 'app/entities/vote/vote.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { ICredit } from 'app/shared/model/credit.model';
import { getEntities as getCredits } from 'app/entities/credit/credit.reducer';
import { getEntity, updateEntity, createEntity, reset } from './image.reducer';
import { IImage } from 'app/shared/model/image.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IImageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImageUpdate = (props: IImageUpdateProps) => {
  const [voteId, setVoteId] = useState('0');
  const [movieId, setMovieId] = useState('0');
  const [personId, setPersonId] = useState('0');
  const [creditId, setCreditId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { imageEntity, votes, movies, people, credits, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/image');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVotes();
    props.getMovies();
    props.getPeople();
    props.getCredits();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...imageEntity,
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
          <h2 id="movieInsightsApp.image.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.image.home.createOrEditLabel">Create or edit a Image</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : imageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="image-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="image-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tmdbIdLabel" for="image-tmdbId">
                  <Translate contentKey="movieInsightsApp.image.tmdbId">Tmdb Id</Translate>
                </Label>
                <AvField
                  id="image-tmdbId"
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
                <Label id="aspectRatioLabel" for="image-aspectRatio">
                  <Translate contentKey="movieInsightsApp.image.aspectRatio">Aspect Ratio</Translate>
                </Label>
                <AvField
                  id="image-aspectRatio"
                  type="string"
                  className="form-control"
                  name="aspectRatio"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="filePathLabel" for="image-filePath">
                  <Translate contentKey="movieInsightsApp.image.filePath">File Path</Translate>
                </Label>
                <AvField
                  id="image-filePath"
                  type="text"
                  name="filePath"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="heightLabel" for="image-height">
                  <Translate contentKey="movieInsightsApp.image.height">Height</Translate>
                </Label>
                <AvField
                  id="image-height"
                  type="string"
                  className="form-control"
                  name="height"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="widthLabel" for="image-width">
                  <Translate contentKey="movieInsightsApp.image.width">Width</Translate>
                </Label>
                <AvField
                  id="image-width"
                  type="string"
                  className="form-control"
                  name="width"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="iso6391Label" for="image-iso6391">
                  <Translate contentKey="movieInsightsApp.image.iso6391">Iso 6391</Translate>
                </Label>
                <AvField id="image-iso6391" type="text" name="iso6391" />
              </AvGroup>
              <AvGroup>
                <Label for="image-vote">
                  <Translate contentKey="movieInsightsApp.image.vote">Vote</Translate>
                </Label>
                <AvInput id="image-vote" type="select" className="form-control" name="voteId">
                  <option value="" key="0" />
                  {votes
                    ? votes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="image-movie">
                  <Translate contentKey="movieInsightsApp.image.movie">Movie</Translate>
                </Label>
                <AvInput id="image-movie" type="select" className="form-control" name="movieId">
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
                <Label for="image-person">
                  <Translate contentKey="movieInsightsApp.image.person">Person</Translate>
                </Label>
                <AvInput id="image-person" type="select" className="form-control" name="personId">
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
                <Label for="image-credit">
                  <Translate contentKey="movieInsightsApp.image.credit">Credit</Translate>
                </Label>
                <AvInput id="image-credit" type="select" className="form-control" name="creditId">
                  <option value="" key="0" />
                  {credits
                    ? credits.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/image" replace color="info">
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
  votes: storeState.vote.entities,
  movies: storeState.movie.entities,
  people: storeState.person.entities,
  credits: storeState.credit.entities,
  imageEntity: storeState.image.entity,
  loading: storeState.image.loading,
  updating: storeState.image.updating,
  updateSuccess: storeState.image.updateSuccess,
});

const mapDispatchToProps = {
  getVotes,
  getMovies,
  getPeople,
  getCredits,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImageUpdate);
