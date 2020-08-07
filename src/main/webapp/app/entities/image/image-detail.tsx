import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './image.reducer';
import { IImage } from 'app/shared/model/image.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IImageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImageDetail = (props: IImageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { imageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.image.detail.title">Image</Translate> [<b>{imageEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.image.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{imageEntity.tmdbId}</dd>
          <dt>
            <span id="aspectRatio">
              <Translate contentKey="movieInsightsApp.image.aspectRatio">Aspect Ratio</Translate>
            </span>
          </dt>
          <dd>{imageEntity.aspectRatio}</dd>
          <dt>
            <span id="filePath">
              <Translate contentKey="movieInsightsApp.image.filePath">File Path</Translate>
            </span>
          </dt>
          <dd>{imageEntity.filePath}</dd>
          <dt>
            <span id="height">
              <Translate contentKey="movieInsightsApp.image.height">Height</Translate>
            </span>
          </dt>
          <dd>{imageEntity.height}</dd>
          <dt>
            <span id="width">
              <Translate contentKey="movieInsightsApp.image.width">Width</Translate>
            </span>
          </dt>
          <dd>{imageEntity.width}</dd>
          <dt>
            <span id="iso6391">
              <Translate contentKey="movieInsightsApp.image.iso6391">Iso 6391</Translate>
            </span>
          </dt>
          <dd>{imageEntity.iso6391}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.image.vote">Vote</Translate>
          </dt>
          <dd>{imageEntity.voteId ? imageEntity.voteId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.image.movie">Movie</Translate>
          </dt>
          <dd>{imageEntity.movieId ? imageEntity.movieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.image.person">Person</Translate>
          </dt>
          <dd>{imageEntity.personId ? imageEntity.personId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.image.credit">Credit</Translate>
          </dt>
          <dd>{imageEntity.creditId ? imageEntity.creditId : ''}</dd>
        </dl>
        <Button tag={Link} to="/image" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image/${imageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ image }: IRootState) => ({
  imageEntity: image.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImageDetail);
