import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './banned-entity.reducer';
import { IBannedEntity } from 'app/shared/model/banned-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBannedEntityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedEntityDetail = (props: IBannedEntityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bannedEntityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.bannedEntity.detail.title">BannedEntity</Translate> [<b>{bannedEntityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.bannedEntity.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{bannedEntityEntity.tmdbId}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="movieInsightsApp.bannedEntity.type">Type</Translate>
            </span>
          </dt>
          <dd>{bannedEntityEntity.type}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.bannedEntity.bannedPersistentEntity">Banned Persistent Entity</Translate>
          </dt>
          <dd>{bannedEntityEntity.bannedPersistentEntityId ? bannedEntityEntity.bannedPersistentEntityId : ''}</dd>
        </dl>
        <Button tag={Link} to="/banned-entity" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banned-entity/${bannedEntityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bannedEntity }: IRootState) => ({
  bannedEntityEntity: bannedEntity.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedEntityDetail);
