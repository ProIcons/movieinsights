import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMovieInsightsPerYear } from 'app/shared/model/movie-insights-per-year.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './movie-insights-per-year.reducer';

export interface IMovieInsightsPerYearDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerYearDeleteDialog = (props: IMovieInsightsPerYearDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/movie-insights-per-year');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.movieInsightsPerYearEntity.id);
  };

  const { movieInsightsPerYearEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="movieInsightsApp.movieInsightsPerYear.delete.question">
        <Translate contentKey="movieInsightsApp.movieInsightsPerYear.delete.question" interpolate={{ id: movieInsightsPerYearEntity.id }}>
          Are you sure you want to delete this MovieInsightsPerYear?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-movieInsightsPerYear" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ movieInsightsPerYear }: IRootState) => ({
  movieInsightsPerYearEntity: movieInsightsPerYear.entity,
  updateSuccess: movieInsightsPerYear.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerYearDeleteDialog);
