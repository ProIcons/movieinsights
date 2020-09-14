import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Logs from './logs/logs';
import Metrics from './metrics/metrics';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute exact path={`${match.url}`} component={Metrics} />
    <ErrorBoundaryRoute exact path={`${match.url}/metrics`} component={Metrics} />
    <ErrorBoundaryRoute exact path={`${match.url}/logs`} component={Logs} />
  </div>
);



export default Routes;
