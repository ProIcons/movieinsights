import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movie-insights-per-country.reducer';
import { IMovieInsightsPerCountry } from 'app/shared/model/movie-insights-per-country.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerCountryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MovieInsightsPerCountry = (props: IMovieInsightsPerCountryProps) => {
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

  const { movieInsightsPerCountryList, match, loading } = props;
  return (
    <div>
      <h2 id="movie-insights-per-country-heading">
        <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.home.title">Movie Insights Per Countries</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.home.createLabel">
            Create new Movie Insights Per Country
          </Translate>
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
                  placeholder={translate('movieInsightsApp.movieInsightsPerCountry.home.search')}
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
        {movieInsightsPerCountryList && movieInsightsPerCountryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.movieInsights">Movie Insights</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.country">Country</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieInsightsPerCountryList.map((movieInsightsPerCountry, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movieInsightsPerCountry.id}`} color="link" size="sm">
                      {movieInsightsPerCountry.id}
                    </Button>
                  </td>
                  <td>
                    {movieInsightsPerCountry.movieInsightsId ? (
                      <Link to={`movie-insights/${movieInsightsPerCountry.movieInsightsId}`}>
                        {movieInsightsPerCountry.movieInsightsId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {movieInsightsPerCountry.countryId ? (
                      <Link to={`production-country/${movieInsightsPerCountry.countryId}`}>{movieInsightsPerCountry.countryId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerCountry.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerCountry.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movieInsightsPerCountry.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.movieInsightsPerCountry.home.notFound">
                No Movie Insights Per Countries found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ movieInsightsPerCountry }: IRootState) => ({
  movieInsightsPerCountryList: movieInsightsPerCountry.entities,
  loading: movieInsightsPerCountry.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerCountry);
