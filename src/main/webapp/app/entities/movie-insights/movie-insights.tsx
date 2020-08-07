import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movie-insights.reducer';
import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MovieInsights = (props: IMovieInsightsProps) => {
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

  const { movieInsightsList, match, loading } = props;
  return (
    <div>
      <h2 id="movie-insights-heading">
        <Translate contentKey="movieInsightsApp.movieInsights.home.title">Movie Insights</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.movieInsights.home.createLabel">Create new Movie Insights</Translate>
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
                  placeholder={translate('movieInsightsApp.movieInsights.home.search')}
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
        {movieInsightsList && movieInsightsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.averageRating">Average Rating</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.averageBudget">Average Budget</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.averageRevenue">Average Revenue</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.totalMovies">Total Movies</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenreMovieCount">
                    Most Popular Genre Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActorMovieCount">
                    Most Popular Actor Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriterMovieCount">
                    Most Popular Writer Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducerMovieCount">
                    Most Popular Producer Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirectorMovieCount">
                    Most Popular Director Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompanyMovieCount">
                    Most Popular Production Company Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountryMovieCount">
                    Most Popular Production Country Movie Count
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.highestRatedMovie">Highest Rated Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestRatedMovie">Lowest Rated Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.highestBudgetMovie">Highest Budget Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestBudgetMovie">Lowest Budget Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.highestRevenueMovie">Highest Revenue Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.lowestRevenueMovie">Lowest Revenue Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularGenre">Most Popular Genre</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularActor">Most Popular Actor</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProducer">Most Popular Producer</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularWriter">Most Popular Writer</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularDirector">Most Popular Director</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCountry">
                    Most Popular Production Country
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsights.mostPopularProductionCompany">
                    Most Popular Production Company
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieInsightsList.map((movieInsights, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movieInsights.id}`} color="link" size="sm">
                      {movieInsights.id}
                    </Button>
                  </td>
                  <td>{movieInsights.averageRating}</td>
                  <td>{movieInsights.averageBudget}</td>
                  <td>{movieInsights.averageRevenue}</td>
                  <td>{movieInsights.totalMovies}</td>
                  <td>{movieInsights.mostPopularGenreMovieCount}</td>
                  <td>{movieInsights.mostPopularActorMovieCount}</td>
                  <td>{movieInsights.mostPopularWriterMovieCount}</td>
                  <td>{movieInsights.mostPopularProducerMovieCount}</td>
                  <td>{movieInsights.mostPopularDirectorMovieCount}</td>
                  <td>{movieInsights.mostPopularProductionCompanyMovieCount}</td>
                  <td>{movieInsights.mostPopularProductionCountryMovieCount}</td>
                  <td>
                    {movieInsights.highestRatedMovieId ? (
                      <Link to={`movie/${movieInsights.highestRatedMovieId}`}>{movieInsights.highestRatedMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.lowestRatedMovieId ? (
                      <Link to={`movie/${movieInsights.lowestRatedMovieId}`}>{movieInsights.lowestRatedMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.highestBudgetMovieId ? (
                      <Link to={`movie/${movieInsights.highestBudgetMovieId}`}>{movieInsights.highestBudgetMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.lowestBudgetMovieId ? (
                      <Link to={`movie/${movieInsights.lowestBudgetMovieId}`}>{movieInsights.lowestBudgetMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.highestRevenueMovieId ? (
                      <Link to={`movie/${movieInsights.highestRevenueMovieId}`}>{movieInsights.highestRevenueMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.lowestRevenueMovieId ? (
                      <Link to={`movie/${movieInsights.lowestRevenueMovieId}`}>{movieInsights.lowestRevenueMovieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularGenreId ? (
                      <Link to={`genre/${movieInsights.mostPopularGenreId}`}>{movieInsights.mostPopularGenreId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularActorId ? (
                      <Link to={`person/${movieInsights.mostPopularActorId}`}>{movieInsights.mostPopularActorId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularProducerId ? (
                      <Link to={`person/${movieInsights.mostPopularProducerId}`}>{movieInsights.mostPopularProducerId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularWriterId ? (
                      <Link to={`person/${movieInsights.mostPopularWriterId}`}>{movieInsights.mostPopularWriterId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularDirectorId ? (
                      <Link to={`person/${movieInsights.mostPopularDirectorId}`}>{movieInsights.mostPopularDirectorId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularProductionCountryId ? (
                      <Link to={`production-country/${movieInsights.mostPopularProductionCountryId}`}>
                        {movieInsights.mostPopularProductionCountryId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsights.mostPopularProductionCompanyId ? (
                      <Link to={`production-company/${movieInsights.mostPopularProductionCompanyId}`}>
                        {movieInsights.mostPopularProductionCompanyId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movieInsights.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsights.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsights.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.movieInsights.home.notFound">No Movie Insights found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ movieInsights }: IRootState) => ({
  movieInsightsList: movieInsights.entities,
  loading: movieInsights.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsights);
