import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './credit.reducer';
import { ICredit } from 'app/shared/model/credit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICreditProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Credit = (props: ICreditProps) => {
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

  const { creditList, match, loading } = props;
  return (
    <div>
      <h2 id="credit-heading">
        <Translate contentKey="movieInsightsApp.credit.home.title">Credits</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.credit.home.createLabel">Create new Credit</Translate>
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
                  placeholder={translate('movieInsightsApp.credit.home.search')}
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
        {creditList && creditList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.creditId">Credit Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.personTmdbId">Person Tmdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.movieTmdbId">Movie Tmdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.role">Role</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.movie">Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.credit.person">Person</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {creditList.map((credit, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${credit.id}`} color="link" size="sm">
                      {credit.id}
                    </Button>
                  </td>
                  <td>{credit.creditId}</td>
                  <td>{credit.personTmdbId}</td>
                  <td>{credit.movieTmdbId}</td>
                  <td>
                    <Translate contentKey={`movieInsightsApp.CreditRole.${credit.role}`} />
                  </td>
                  <td>{credit.movieId ? <Link to={`movie/${credit.movieId}`}>{credit.movieId}</Link> : ''}</td>
                  <td>{credit.personId ? <Link to={`person/${credit.personId}`}>{credit.personId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${credit.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${credit.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${credit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.credit.home.notFound">No Credits found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ credit }: IRootState) => ({
  creditList: credit.entities,
  loading: credit.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Credit);
