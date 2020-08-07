import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMovieInsightsPerPerson } from 'app/shared/model/movie-insights-per-person.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './movie-insights-per-person.reducer';

export interface IMovieInsightsPerPersonDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerPersonDeleteDialog = (props: IMovieInsightsPerPersonDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/movie-insights-per-person');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.movieInsightsPerPersonEntity.id);
  };

  const { movieInsightsPerPersonEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="movieInsightsApp.movieInsightsPerPerson.delete.question">
        <Translate
          contentKey="movieInsightsApp.movieInsightsPerPerson.delete.question"
          interpolate={{ id: movieInsightsPerPersonEntity.id }}
        >
          Are you sure you want to delete this MovieInsightsPerPerson?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-movieInsightsPerPerson" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ movieInsightsPerPerson }: IRootState) => ({
  movieInsightsPerPersonEntity: movieInsightsPerPerson.entity,
  updateSuccess: movieInsightsPerPerson.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerPersonDeleteDialog);
