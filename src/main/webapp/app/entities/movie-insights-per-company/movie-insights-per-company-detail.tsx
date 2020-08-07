import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-insights-per-company.reducer';
import { IMovieInsightsPerCompany } from 'app/shared/model/movie-insights-per-company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieInsightsPerCompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerCompanyDetail = (props: IMovieInsightsPerCompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieInsightsPerCompanyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.detail.title">MovieInsightsPerCompany</Translate> [
          <b>{movieInsightsPerCompanyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.movieInsights">Movie Insights</Translate>
          </dt>
          <dd>{movieInsightsPerCompanyEntity.movieInsightsId ? movieInsightsPerCompanyEntity.movieInsightsId : ''}</dd>
          <dt>
            <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.company">Company</Translate>
          </dt>
          <dd>{movieInsightsPerCompanyEntity.companyId ? movieInsightsPerCompanyEntity.companyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-insights-per-company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-insights-per-company/${movieInsightsPerCompanyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieInsightsPerCompany }: IRootState) => ({
  movieInsightsPerCompanyEntity: movieInsightsPerCompany.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerCompanyDetail);
