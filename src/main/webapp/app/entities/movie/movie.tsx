import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Movie = (props: IMovieProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { movieList, match, loading } = props;
  return (
    <div>
      <h2 id="movie-heading">
        <Translate contentKey="movieInsightsApp.movie.home.title">Movies</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.movie.home.createLabel">Create new Movie</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('movieInsightsApp.movie.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {movieList && movieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.tmdbId">Tmdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.tagline">Tagline</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.revenue">Revenue</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.budget">Budget</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.imdbId">Imdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.popularity">Popularity</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.runtime">Runtime</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.posterPath">Poster Path</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.backdropPath">Backdrop Path</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.releaseDate">Release Date</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.isBanned">Is Banned</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.vote">Vote</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.companies">Companies</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.countries">Countries</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movie.genres">Genres</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieList.map((movie, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movie.id}`} color="link" size="sm">
                      {movie.id}
                    </Button>
                  </td>
                  <td>{movie.tmdbId}</td>
                  <td>{movie.name}</td>
                  <td>{movie.tagline}</td>
                  <td>{movie.description}</td>
                  <td>{movie.revenue}</td>
                  <td>{movie.budget}</td>
                  <td>{movie.imdbId}</td>
                  <td>{movie.popularity}</td>
                  <td>{movie.runtime}</td>
                  <td>{movie.posterPath}</td>
                  <td>{movie.backdropPath}</td>
                  <td>{movie.releaseDate ? <TextFormat type="date" value={movie.releaseDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{movie.isBanned ? 'true' : 'false'}</td>
                  <td>{movie.voteId ? <Link to={`vote/${movie.voteId}`}>{movie.voteId}</Link> : ''}</td>
                  <td>
                    {movie.companies
                      ? movie.companies.map((val, j) => (
                          <span key={j}>
                            <Link to={`production-company/${val.id}`}>{val.id}</Link>
                            {j === movie.companies.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {movie.countries
                      ? movie.countries.map((val, j) => (
                          <span key={j}>
                            <Link to={`production-country/${val.id}`}>{val.id}</Link>
                            {j === movie.countries.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {movie.genres
                      ? movie.genres.map((val, j) => (
                          <span key={j}>
                            <Link to={`genre/${val.id}`}>{val.id}</Link>
                            {j === movie.genres.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movie.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movie.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movie.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="movieInsightsApp.movie.home.notFound">No Movies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ movie }: IRootState) => ({
  movieList: movie.entities,
  loading: movie.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Movie);
