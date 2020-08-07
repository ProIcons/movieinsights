import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movie-insights-per-genre.reducer';
import { IMovieInsightsPerGenre } from 'app/shared/model/movie-insights-per-genre.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerGenreProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MovieInsightsPerGenre = (props: IMovieInsightsPerGenreProps) => {
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

  const { movieInsightsPerGenreList, match, loading } = props;
  return (
    <div>
      <h2 id="movie-insights-per-genre-heading">
        <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.home.title">Movie Insights Per Genres</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.home.createLabel">Create new Movie Insights Per Genre</Translate>
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
                  placeholder={translate('movieInsightsApp.movieInsightsPerGenre.home.search')}
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
        {movieInsightsPerGenreList && movieInsightsPerGenreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.movieInsights">Movie Insights</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.genre">Genre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieInsightsPerGenreList.map((movieInsightsPerGenre, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movieInsightsPerGenre.id}`} color="link" size="sm">
                      {movieInsightsPerGenre.id}
                    </Button>
                  </td>
                  <td>
                    {movieInsightsPerGenre.movieInsightsId ? (
                      <Link to={`movie-insights/${movieInsightsPerGenre.movieInsightsId}`}>{movieInsightsPerGenre.movieInsightsId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerGenre.genreId ? (
                      <Link to={`genre/${movieInsightsPerGenre.genreId}`}>{movieInsightsPerGenre.genreId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerGenre.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerGenre.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerGenre.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.movieInsightsPerGenre.home.notFound">No Movie Insights Per Genres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ movieInsightsPerGenre }: IRootState) => ({
  movieInsightsPerGenreList: movieInsightsPerGenre.entities,
  loading: movieInsightsPerGenre.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerGenre);
