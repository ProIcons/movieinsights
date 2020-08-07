import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './credit.reducer';
import { ICredit } from 'app/shared/model/credit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICreditDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CreditDetail = (props: ICreditDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { creditEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.credit.detail.title">Credit</Translate> [<b>{creditEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="creditId">
              <Translate contentKey="movieInsightsApp.credit.creditId">Credit Id</Translate>
            </span>
          </dt>
          <dd>{creditEntity.creditId}</dd>
          <dt>
            <span id="personTmdbId">
              <Translate contentKey="movieInsightsApp.credit.personTmdbId">Person Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{creditEntity.personTmdbId}</dd>
          <dt>
            <span id="movieTmdbId">
              <Translate contentKey="movieInsightsApp.credit.movieTmdbId">Movie Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{creditEntity.movieTmdbId}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="movieInsightsApp.credit.role">Role</Translate>
            </span>
          </dt>
          <dd>{creditEntity.role}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.credit.movie">Movie</Translate>
          </dt>
          <dd>{creditEntity.movieId ? creditEntity.movieId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.credit.person">Person</Translate>
          </dt>
          <dd>{creditEntity.personId ? creditEntity.personId : ''}</dd>
        </dl>
        <Button tag={Link} to="/credit" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/credit/${creditEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ credit }: IRootState) => ({
  creditEntity: credit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CreditDetail);
