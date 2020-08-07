import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movie-insights-per-year.reducer';
import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerYearProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MovieInsightsPerYear = (props: IMovieInsightsPerYearProps) => {
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

  const { movieInsightsPerYearList, match, loading } = props;
  return (
    <div>
      <h2 id="movie-insights-per-year-heading">
        <Translate contentKey="movieInsightsApp.movieInsightsPerYear.home.title">Movie Insights Per Years</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.movieInsightsPerYear.home.createLabel">Create new Movie Insights Per Year</Translate>
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
                  placeholder={translate('movieInsightsApp.movieInsightsPerYear.home.search')}
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
        {movieInsightsPerYearList && movieInsightsPerYearList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.year">Year</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsights">Movie Insights</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCountry">
                    Movie Insights Per Country
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerCompany">
                    Movie Insights Per Company
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerPerson">Movie Insights Per Person</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerYear.movieInsightsPerGenre">Movie Insights Per Genre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieInsightsPerYearList.map((movieInsightsPerYear, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movieInsightsPerYear.id}`} color="link" size="sm">
                      {movieInsightsPerYear.id}
                    </Button>
                  </td>
                  <td>{movieInsightsPerYear.year}</td>
                  <td>
                    {movieInsightsPerYear.movieInsightsId ? (
                      <Link to={`movie-insights/${movieInsightsPerYear.movieInsightsId}`}>{movieInsightsPerYear.movieInsightsId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerYear.movieInsightsPerCountryId ? (
                      <Link to={`movie-insights-per-country/${movieInsightsPerYear.movieInsightsPerCountryId}`}>
                        {movieInsightsPerYear.movieInsightsPerCountryId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerYear.movieInsightsPerCompanyId ? (
                      <Link to={`movie-insights-per-company/${movieInsightsPerYear.movieInsightsPerCompanyId}`}>
                        {movieInsightsPerYear.movieInsightsPerCompanyId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerYear.movieInsightsPerPersonId ? (
                      <Link to={`movie-insights-per-person/${movieInsightsPerYear.movieInsightsPerPersonId}`}>
                        {movieInsightsPerYear.movieInsightsPerPersonId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerYear.movieInsightsPerGenreId ? (
                      <Link to={`movie-insights-per-genre/${movieInsightsPerYear.movieInsightsPerGenreId}`}>
                        {movieInsightsPerYear.movieInsightsPerGenreId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerYear.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerYear.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerYear.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.movieInsightsPerYear.home.notFound">No Movie Insights Per Years found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ movieInsightsPerYear }: IRootState) => ({
  movieInsightsPerYearList: movieInsightsPerYear.entities,
  loading: movieInsightsPerYear.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerYear);
