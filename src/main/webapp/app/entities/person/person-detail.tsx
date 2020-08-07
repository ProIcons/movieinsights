import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './person.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPersonDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PersonDetail = (props: IPersonDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { personEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.person.detail.title">Person</Translate> [<b>{personEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.person.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.tmdbId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieInsightsApp.person.name">Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.name}</dd>
          <dt>
            <span id="imdbId">
              <Translate contentKey="movieInsightsApp.person.imdbId">Imdb Id</Translate>
            </span>
          </dt>
          <dd>{personEntity.imdbId}</dd>
          <dt>
            <span id="popularity">
              <Translate contentKey="movieInsightsApp.person.popularity">Popularity</Translate>
            </span>
          </dt>
          <dd>{personEntity.popularity}</dd>
          <dt>
            <span id="biography">
              <Translate contentKey="movieInsightsApp.person.biography">Biography</Translate>
            </span>
          </dt>
          <dd>{personEntity.biography}</dd>
          <dt>
            <span id="birthDay">
              <Translate contentKey="movieInsightsApp.person.birthDay">Birth Day</Translate>
            </span>
          </dt>
          <dd>{personEntity.birthDay ? <TextFormat value={personEntity.birthDay} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="profilePath">
              <Translate contentKey="movieInsightsApp.person.profilePath">Profile Path</Translate>
            </span>
          </dt>
          <dd>{personEntity.profilePath}</dd>
        </dl>
        <Button tag={Link} to="/person" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person/${personEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ person }: IRootState) => ({
  personEntity: person.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PersonDetail);
