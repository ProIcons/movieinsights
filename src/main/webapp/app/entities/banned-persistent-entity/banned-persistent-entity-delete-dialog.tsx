import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './banned-persistent-entity.reducer';

export interface IBannedPersistentEntityDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedPersistentEntityDeleteDialog = (props: IBannedPersistentEntityDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/banned-persistent-entity');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.bannedPersistentEntityEntity.id);
  };

  const { bannedPersistentEntityEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="movieInsightsApp.bannedPersistentEntity.delete.question">
        <Translate
          contentKey="movieInsightsApp.bannedPersistentEntity.delete.question"
          interpolate={{ id: bannedPersistentEntityEntity.id }}
        >
          Are you sure you want to delete this BannedPersistentEntity?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-bannedPersistentEntity" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ bannedPersistentEntity }: IRootState) => ({
  bannedPersistentEntityEntity: bannedPersistentEntity.entity,
  updateSuccess: bannedPersistentEntity.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedPersistentEntityDeleteDialog);
