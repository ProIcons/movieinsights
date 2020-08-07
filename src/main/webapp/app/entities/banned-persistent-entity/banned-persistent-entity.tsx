import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './banned-persistent-entity.reducer';
import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBannedPersistentEntityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BannedPersistentEntity = (props: IBannedPersistentEntityProps) => {
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

  const { bannedPersistentEntityList, match, loading } = props;
  return (
    <div>
      <h2 id="banned-persistent-entity-heading">
        <Translate contentKey="movieInsightsApp.bannedPersistentEntity.home.title">Banned Persistent Entities</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.bannedPersistentEntity.home.createLabel">Create new Banned Persistent Entity</Translate>
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
                  placeholder={translate('movieInsightsApp.bannedPersistentEntity.home.search')}
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
        {bannedPersistentEntityList && bannedPersistentEntityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reason">Reason</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reasonText">Reason Text</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.timestamp">Timestamp</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.movie">Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.person">Person</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.bannedPersistentEntity.productionCompany">Production Company</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bannedPersistentEntityList.map((bannedPersistentEntity, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${bannedPersistentEntity.id}`} color="link" size="sm">
                      {bannedPersistentEntity.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`movieInsightsApp.BanReason.${bannedPersistentEntity.reason}`} />
                  </td>
                  <td>{bannedPersistentEntity.reasonText}</td>
                  <td>
                    {bannedPersistentEntity.timestamp ? (
                      <TextFormat type="date" value={bannedPersistentEntity.timestamp} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bannedPersistentEntity.movieId ? (
                      <Link to={`movie/${bannedPersistentEntity.movieId}`}>{bannedPersistentEntity.movieId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bannedPersistentEntity.personId ? (
                      <Link to={`person/${bannedPersistentEntity.personId}`}>{bannedPersistentEntity.personId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bannedPersistentEntity.productionCompanyId ? (
                      <Link to={`production-company/${bannedPersistentEntity.productionCompanyId}`}>
                        {bannedPersistentEntity.productionCompanyId}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bannedPersistentEntity.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bannedPersistentEntity.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bannedPersistentEntity.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.bannedPersistentEntity.home.notFound">No Banned Persistent Entities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bannedPersistentEntity }: IRootState) => ({
  bannedPersistentEntityList: bannedPersistentEntity.entities,
  loading: bannedPersistentEntity.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedPersistentEntity);
