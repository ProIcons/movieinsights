import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './genre.reducer';
import { IGenre } from 'app/shared/model/genre.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGenreDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GenreDetail = (props: IGenreDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { genreEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="movieInsightsApp.genre.detail.title">Genre</Translate> [<b>{genreEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tmdbId">
              <Translate contentKey="movieInsightsApp.genre.tmdbId">Tmdb Id</Translate>
            </span>
          </dt>
          <dd>{genreEntity.tmdbId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieInsightsApp.genre.name">Name</Translate>
            </span>
          </dt>
          <dd>{genreEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/genre" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/genre/${genreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ genre }: IRootState) => ({
  genreEntity: genre.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GenreDetail);
