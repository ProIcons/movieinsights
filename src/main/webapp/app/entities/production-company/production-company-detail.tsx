import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production-company.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionCompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionCompanyDetail = (props: IProductionCompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionCompanyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.productionCompany.detail.title">ProductionCompany</Translate> [
          <b>{productionCompanyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="movieInsightsApp.productionCompany.name">Name</Translate>
            </span>
          </dt>
          <dd>{productionCompanyEntity.name}</dd>
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.productionCompany.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{productionCompanyEntity.tmdbId}</dd>
          <dt>
            <span id="logoPath">
              <Translate contentKey="movieInsightsApp.productionCompany.logoPath">Logo Path</Translate>
            </span>
          </dt>
          <dd>{productionCompanyEntity.logoPath}</dd>
          <dt>
            <span id="originCountry">
              <Translate contentKey="movieInsightsApp.productionCompany.originCountry">Origin Country</Translate>
            </span>
          </dt>
          <dd>{productionCompanyEntity.originCountry}</dd>
        </dl>
        <Button tag={Link} to="/production-company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-company/${productionCompanyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productionCompany }: IRootState) => ({
  productionCompanyEntity: productionCompany.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionCompanyDetail);
