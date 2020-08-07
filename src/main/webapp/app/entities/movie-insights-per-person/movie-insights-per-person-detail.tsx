import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-insights-per-person.reducer';
import { IMovieInsightsPerPerson } from 'app/shared/model/movie-insights-per-person.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerPersonDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerPersonDetail = (props: IMovieInsightsPerPersonDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieInsightsPerPersonEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.detail.title">MovieInsightsPerPerson</Translate> [
          <b>{movieInsightsPerPersonEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="as">
              <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.as">As</Translate>
            </span>
          </dt>
          <dd>{movieInsightsPerPersonEntity.as}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.movieInsights">Movie Insights</Translate>
          </dt>
          <dd>{movieInsightsPerPersonEntity.movieInsightsId ? movieInsightsPerPersonEntity.movieInsightsId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerPerson.person">Person</Translate>
          </dt>
          <dd>{movieInsightsPerPersonEntity.personId ? movieInsightsPerPersonEntity.personId : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-insights-per-person" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-insights-per-person/${movieInsightsPerPersonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieInsightsPerPerson }: IRootState) => ({
  movieInsightsPerPersonEntity: movieInsightsPerPerson.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerPersonDetail);
