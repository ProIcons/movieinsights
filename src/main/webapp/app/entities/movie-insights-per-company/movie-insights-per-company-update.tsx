import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovieInsights } from 'app/shared/model/movie-insights.model';
import { getEntities as getMovieInsights } from 'app/entities/movie-insights/movie-insights.reducer';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { getEntities as getProductionCompanies } from 'app/entities/production-company/production-company.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movie-insights-per-company.reducer';
import { IMovieInsightsPerCompany } from 'app/shared/model/movie-insights-per-company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovieInsightsPerCompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerCompanyUpdate = (props: IMovieInsightsPerCompanyUpdateProps) => {
  const [movieInsightsId, setMovieInsightsId] = useState('0');
  const [companyId, setCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { movieInsightsPerCompanyEntity, movieInsights, productionCompanies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/movie-insights-per-company');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieInsights();
    props.getProductionCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...movieInsightsPerCompanyEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieInsightsApp.movieInsightsPerCompany.home.createOrEditLabel">
            <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.home.createOrEditLabel">
              Create or edit a MovieInsightsPerCompany
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : movieInsightsPerCompanyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="movie-insights-per-company-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="movie-insights-per-company-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="movie-insights-per-company-movieInsights">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.movieInsights">Movie Insights</Translate>
                </Label>
                <AvInput id="movie-insights-per-company-movieInsights" type="select" className="form-control" name="movieInsightsId">
                  <option value="" key="0" />
                  {movieInsights
                    ? movieInsights.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="movie-insights-per-company-company">
                  <Translate contentKey="movieInsightsApp.movieInsightsPerCompany.company">Company</Translate>
                </Label>
                <AvInput id="movie-insights-per-company-company" type="select" className="form-control" name="companyId" required>
                  {productionCompanies
                    ? productionCompanies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/movie-insights-per-company" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  movieInsights: storeState.movieInsights.entities,
  productionCompanies: storeState.productionCompany.entities,
  movieInsightsPerCompanyEntity: storeState.movieInsightsPerCompany.entity,
  loading: storeState.movieInsightsPerCompany.loading,
  updating: storeState.movieInsightsPerCompany.updating,
  updateSuccess: storeState.movieInsightsPerCompany.updateSuccess,
});

const mapDispatchToProps = {
  getMovieInsights,
  getProductionCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerCompanyUpdate);
