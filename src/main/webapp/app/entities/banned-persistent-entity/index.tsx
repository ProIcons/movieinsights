import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BannedPersistentEntity from './banned-persistent-entity';
import BannedPersistentEntityDetail from './banned-persistent-entity-detail';
import BannedPersistentEntityUpdate from './banned-persistent-entity-update';
import BannedPersistentEntityDeleteDialog from './banned-persistent-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BannedPersistentEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BannedPersistentEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BannedPersistentEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={BannedPersistentEntity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BannedPersistentEntityDeleteDialog} />
  </>
);

export default Routes;
