import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production-country.reducer';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionCountryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionCountryDetail = (props: IProductionCountryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionCountryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.productionCountry.detail.title">ProductionCountry</Translate> [
          <b>{productionCountryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="movieInsightsApp.productionCountry.name">Name</Translate>
            </span>
          </dt>
          <dd>{productionCountryEntity.name}</dd>
          <dt>
            <span id="iso31661">
              <Translate contentKey="movieInsightsApp.productionCountry.iso31661">Iso 31661</Translate>
            </span>
          </dt>
          <dd>{productionCountryEntity.iso31661}</dd>
        </dl>
        <Button tag={Link} to="/production-country" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production-country/${productionCountryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productionCountry }: IRootState) => ({
  productionCountryEntity: productionCountry.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionCountryDetail);
