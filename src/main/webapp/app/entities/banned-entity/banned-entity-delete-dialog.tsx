import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBannedEntity } from 'app/shared/model/banned-entity.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './banned-entity.reducer';

export interface IBannedEntityDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannedEntityDeleteDialog = (props: IBannedEntityDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/banned-entity');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.bannedEntityEntity.id);
  };

  const { bannedEntityEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="movieInsightsApp.bannedEntity.delete.question">
        <Translate contentKey="movieInsightsApp.bannedEntity.delete.question" interpolate={{ id: bannedEntityEntity.id }}>
          Are you sure you want to delete this BannedEntity?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-bannedEntity" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ bannedEntity }: IRootState) => ({
  bannedEntityEntity: bannedEntity.entity,
  updateSuccess: bannedEntity.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BannedEntityDeleteDialog);
