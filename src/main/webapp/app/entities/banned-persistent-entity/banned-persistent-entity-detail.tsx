import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './banned-persistent-entity.reducer';
import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBannedPersistentEntityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedPersistentEntityDetail = (props: IBannedPersistentEntityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bannedPersistentEntityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.bannedPersistentEntity.detail.title">BannedPersistentEntity</Translate> [
          <b>{bannedPersistentEntityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="reason">
              <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{bannedPersistentEntityEntity.reason}</dd>
          <dt>
            <span id="reasonText">
              <Translate contentKey="movieInsightsApp.bannedPersistentEntity.reasonText">Reason Text</Translate>
            </span>
          </dt>
          <dd>{bannedPersistentEntityEntity.reasonText}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="movieInsightsApp.bannedPersistentEntity.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {bannedPersistentEntityEntity.timestamp ? (
              <TextFormat value={bannedPersistentEntityEntity.timestamp} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="movieInsightsApp.bannedPersistentEntity.movie">Movie</Translate>
          </dt>
          <dd>{bannedPersistentEntityEntity.movieId ? bannedPersistentEntityEntity.movieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.bannedPersistentEntity.person">Person</Translate>
          </dt>
          <dd>{bannedPersistentEntityEntity.personId ? bannedPersistentEntityEntity.personId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.bannedPersistentEntity.productionCompany">Production Company</Translate>
          </dt>
          <dd>{bannedPersistentEntityEntity.productionCompanyId ? bannedPersistentEntityEntity.productionCompanyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/banned-persistent-entity" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banned-persistent-entity/${bannedPersistentEntityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bannedPersistentEntity }: IRootState) => ({
  bannedPersistentEntityEntity: bannedPersistentEntity.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedPersistentEntityDetail);
