import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BannedEntity from './banned-entity';
import BannedEntityDetail from './banned-entity-detail';
import BannedEntityUpdate from './banned-entity-update';
import BannedEntityDeleteDialog from './banned-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BannedEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BannedEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BannedEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={BannedEntity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BannedEntityDeleteDialog} />
  </>
);

export default Routes;
