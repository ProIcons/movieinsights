import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './image.reducer';
import { IImage } from 'app/shared/model/image.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IImageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Image = (props: IImageProps) => {
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

  const { imageList, match, loading } = props;
  return (
    <div>
      <h2 id="image-heading">
        <Translate contentKey="movieInsightsApp.image.home.title">Images</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="movieInsightsApp.image.home.createLabel">Create new Image</Translate>
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
                  placeholder={translate('movieInsightsApp.image.home.search')}
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
        {imageList && imageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.tmdbId">Tmdb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.aspectRatio">Aspect Ratio</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.filePath">File Path</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.height">Height</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.width">Width</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.iso6391">Iso 6391</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.vote">Vote</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.movie">Movie</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.person">Person</Translate>
                </th>
                <th>
                  <Translate contentKey="movieInsightsApp.image.credit">Credit</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {imageList.map((image, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${image.id}`} color="link" size="sm">
                      {image.id}
                    </Button>
                  </td>
                  <td>{image.tmdbId}</td>
                  <td>{image.aspectRatio}</td>
                  <td>{image.filePath}</td>
                  <td>{image.height}</td>
                  <td>{image.width}</td>
                  <td>{image.iso6391}</td>
                  <td>{image.voteId ? <Link to={`vote/${image.voteId}`}>{image.voteId}</Link> : ''}</td>
                  <td>{image.movieId ? <Link to={`movie/${image.movieId}`}>{image.movieId}</Link> : ''}</td>
                  <td>{image.personId ? <Link to={`person/${image.personId}`}>{image.personId}</Link> : ''}</td>
                  <td>{image.creditId ? <Link to={`credit/${image.creditId}`}>{image.creditId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${image.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${image.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${image.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="movieInsightsApp.image.home.notFound">No Images found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ image }: IRootState) => ({
  imageList: image.entities,
  loading: image.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Image);
