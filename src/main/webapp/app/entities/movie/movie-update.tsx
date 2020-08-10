import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVote } from 'app/shared/model/vote.model';
import { getEntities as getVotes } from 'app/entities/vote/vote.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { getEntities as getProductionCompanies } from 'app/entities/production-company/production-company.reducer';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { getEntities as getProductionCountries } from 'app/entities/production-country/production-country.reducer';
import { IGenre } from 'app/shared/model/genre.model';
import { getEntities as getGenres } from 'app/entities/genre/genre.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieUpdate = (props: IMovieUpdateProps) => {
  const [idscompanies, setIdscompanies] = useState([]);
  const [idscountries, setIdscountries] = useState([]);
  const [idsgenres, setIdsgenres] = useState([]);
  const [voteId, setVoteId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieEntity, votes, productionCompanies, productionCountries, genres, loading, updating } = props;

  const { description } = movieEntity;

  const handleClose = () => {
    props.history.push('/movie');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVotes();
    props.getProductionCompanies();
    props.getProductionCountries();
    props.getGenres();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieEntity,
        ...values,
        companies: mapIdList(values.companies),
        countries: mapIdList(values.countries),
        genres: mapIdList(values.genres),
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
          <h2 id="movieInsightsApp.movie.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movie.home.createOrEditLabel">Create or edit a Movie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tmdbIdLabel" for="movie-tmdbId">
                  <Translate contentKey="movieInsightsApp.movie.tmdbId">Tmdb Id</Translate>
                </Label>
                <AvField
                  id="movie-tmdbId"
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
                <Label id="nameLabel" for="movie-name">
                  <Translate contentKey="movieInsightsApp.movie.name">Name</Translate>
                </Label>
                <AvField
                  id="movie-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="taglineLabel" for="movie-tagline">
                  <Translate contentKey="movieInsightsApp.movie.tagline">Tagline</Translate>
                </Label>
                <AvField id="movie-tagline" type="text" name="tagline" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="movie-description">
                  <Translate contentKey="movieInsightsApp.movie.description">Description</Translate>
                </Label>
                <AvInput id="movie-description" type="textarea" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="revenueLabel" for="movie-revenue">
                  <Translate contentKey="movieInsightsApp.movie.revenue">Revenue</Translate>
                </Label>
                <AvField id="movie-revenue" type="string" className="form-control" name="revenue" />
              </AvGroup>
              <AvGroup>
                <Label id="budgetLabel" for="movie-budget">
                  <Translate contentKey="movieInsightsApp.movie.budget">Budget</Translate>
                </Label>
                <AvField id="movie-budget" type="string" className="form-control" name="budget" />
              </AvGroup>
              <AvGroup>
                <Label id="imdbIdLabel" for="movie-imdbId">
                  <Translate contentKey="movieInsightsApp.movie.imdbId">Imdb Id</Translate>
                </Label>
                <AvField id="movie-imdbId" type="text" name="imdbId" />
              </AvGroup>
              <AvGroup>
                <Label id="popularityLabel" for="movie-popularity">
                  <Translate contentKey="movieInsightsApp.movie.popularity">Popularity</Translate>
                </Label>
                <AvField
                  id="movie-popularity"
                  type="string"
                  className="form-control"
                  name="popularity"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="runtimeLabel" for="movie-runtime">
                  <Translate contentKey="movieInsightsApp.movie.runtime">Runtime</Translate>
                </Label>
                <AvField id="movie-runtime" type="string" className="form-control" name="runtime" />
              </AvGroup>
              <AvGroup>
                <Label id="posterPathLabel" for="movie-posterPath">
                  <Translate contentKey="movieInsightsApp.movie.posterPath">Poster Path</Translate>
                </Label>
                <AvField id="movie-posterPath" type="text" name="posterPath" />
              </AvGroup>
              <AvGroup>
                <Label id="backdropPathLabel" for="movie-backdropPath">
                  <Translate contentKey="movieInsightsApp.movie.backdropPath">Backdrop Path</Translate>
                </Label>
                <AvField id="movie-backdropPath" type="text" name="backdropPath" />
              </AvGroup>
              <AvGroup>
                <Label id="releaseDateLabel" for="movie-releaseDate">
                  <Translate contentKey="movieInsightsApp.movie.releaseDate">Release Date</Translate>
                </Label>
                <AvField id="movie-releaseDate" type="date" className="form-control" name="releaseDate" />
              </AvGroup>
              <AvGroup>
                <Label for="movie-vote">
                  <Translate contentKey="movieInsightsApp.movie.vote">Vote</Translate>
                </Label>
                <AvInput id="movie-vote" type="select" className="form-control" name="voteId">
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
                <Label for="movie-companies">
                  <Translate contentKey="movieInsightsApp.movie.companies">Companies</Translate>
                </Label>
                <AvInput
                  id="movie-companies"
                  type="select"
                  multiple
                  className="form-control"
                  name="companies"
                  value={movieEntity.companies && movieEntity.companies.map(e => e.id)}
                >
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
              <AvGroup>
                <Label for="movie-countries">
                  <Translate contentKey="movieInsightsApp.movie.countries">Countries</Translate>
                </Label>
                <AvInput
                  id="movie-countries"
                  type="select"
                  multiple
                  className="form-control"
                  name="countries"
                  value={movieEntity.countries && movieEntity.countries.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {productionCountries
                    ? productionCountries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-genres">
                  <Translate contentKey="movieInsightsApp.movie.genres">Genres</Translate>
                </Label>
                <AvInput
                  id="movie-genres"
                  type="select"
                  multiple
                  className="form-control"
                  name="genres"
                  value={movieEntity.genres && movieEntity.genres.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {genres
                    ? genres.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/movie" replace color="info">
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
  productionCompanies: storeState.productionCompany.entities,
  productionCountries: storeState.productionCountry.entities,
  genres: storeState.genre.entities,
  movieEntity: storeState.movie.entity,
  loading: storeState.movie.loading,
  updating: storeState.movie.updating,
  updateSuccess: storeState.movie.updateSuccess,
});

const mapDispatchToProps = {
  getVotes,
  getProductionCompanies,
  getProductionCountries,
  getGenres,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieUpdate);
