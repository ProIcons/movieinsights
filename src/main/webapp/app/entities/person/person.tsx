import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './person.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPersonProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Person = (props: IPersonProps) => {
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

  const { personList, match, loading } = props;
  return (
    <div>
      <h2 id="person-heading">
        <Translate contentKey="movieInsightsApp.person.home.title">People</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.person.home.createLabel">Create new Person</Translate>
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
                  placeholder={translate('movieInsightsApp.person.home.search')}
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
        {personList && personList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.tmdbId">Tmdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.imdbId">Imdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.popularity">Popularity</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.biography">Biography</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.birthDay">Birth Day</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.person.profilePath">Profile Path</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personList.map((person, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${person.id}`} color="link" size="sm">
                      {person.id}
                    </Button>
                  </td>
                  <td>{person.tmdbId}</td>
                  <td>{person.name}</td>
                  <td>{person.imdbId}</td>
                  <td>{person.popularity}</td>
                  <td>{person.biography}</td>
                  <td>{person.birthDay ? <TextFormat type="date" value={person.birthDay} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{person.profilePath}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${person.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${person.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${person.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.person.home.notFound">No People found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ person }: IRootState) => ({
  personList: person.entities,
  loading: person.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Person);
