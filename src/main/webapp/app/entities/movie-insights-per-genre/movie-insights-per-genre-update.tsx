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
import { IGenre } from 'app/shared/model/genre.model';
import { getEntities as getGenres } from 'app/entities/genre/genre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights-per-genre.reducer';
import { IMovieInsightsPerGenre } from 'app/shared/model/movie-insights-per-genre.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsPerGenreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerGenreUpdate = (props: IMovieInsightsPerGenreUpdateProps) => {
  const [movieInsightsId, setMovieInsightsId] = useState('0');
  const [genreId, setGenreId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieInsightsPerGenreEntity, movieInsights, genres, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/movie-insights-per-genre');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieInsights();
    props.getGenres();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieInsightsPerGenreEntity,
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
          <h2 id="movieInsightsApp.movieInsightsPerGenre.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.home.createOrEditLabel">
              Create or edit a MovieInsightsPerGenre
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsPerGenreEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-per-genre-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-per-genre-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="movie-insights-per-genre-movieInsights">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.movieInsights">Movie Insights</Translate>
                </Label>
                <AvInput id="movie-insights-per-genre-movieInsights" type="select" className="form-control" name="movieInsightsId">
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
                <Label for="movie-insights-per-genre-genre">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.genre">Genre</Translate>
                </Label>
                <AvInput id="movie-insights-per-genre-genre" type="select" className="form-control" name="genreId" required>
                  {genres
                    ? genres.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/movie-insights-per-genre" replace color="info">
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
  genres: storeState.genre.entities,
  movieInsightsPerGenreEntity: storeState.movieInsightsPerGenre.entity,
  loading: storeState.movieInsightsPerGenre.loading,
  updating: storeState.movieInsightsPerGenre.updating,
  updateSuccess: storeState.movieInsightsPerGenre.updateSuccess,
});

const mapDispatchToProps = {
  getMovieInsights,
  getGenres,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerGenreUpdate);
