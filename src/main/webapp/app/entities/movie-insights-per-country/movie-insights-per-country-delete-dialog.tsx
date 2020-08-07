import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMovieInsightsPerCountry } from 'app/shared/model/movie-insights-per-country.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './movie-insights-per-country.reducer';

export interface IMovieInsightsPerCountryDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieInsightsPerCountryDeleteDialog = (props: IMovieInsightsPerCountryDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/movie-insights-per-country');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.movieInsightsPerCountryEntity.id);
  };

  const { movieInsightsPerCountryEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="movieInsightsApp.movieInsightsPerCountry.delete.question">
        <Translate
          contentKey="movieInsightsApp.movieInsightsPerCountry.delete.question"
          interpolate={{ id: movieInsightsPerCountryEntity.id }}
        >
          Are you sure you want to delete this MovieInsightsPerCountry?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-movieInsightsPerCountry" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ movieInsightsPerCountry }: IRootState) => ({
  movieInsightsPerCountryEntity: movieInsightsPerCountry.entity,
  updateSuccess: movieInsightsPerCountry.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieInsightsPerCountryDeleteDialog);
