import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vote.reducer';
import { IVote } from 'app/shared/model/vote.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVoteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VoteDetail = (props: IVoteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { voteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.vote.detail.title">Vote</Translate> [<b>{voteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="average">
              <Translate contentKey="movieInsightsApp.vote.average">Average</Translate>
            </span>
          </dt>
          <dd>{voteEntity.average}</dd>
          <dt>
            <span id="votes">
              <Translate contentKey="movieInsightsApp.vote.votes">Votes</Translate>
            </span>
          </dt>
          <dd>{voteEntity.votes}</dd>
        </dl>
        <Button tag={Link} to="/vote" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vote/${voteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ vote }: IRootState) => ({
  voteEntity: vote.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VoteDetail);
