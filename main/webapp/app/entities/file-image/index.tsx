import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileImage from './file-image';
import FileImageDetail from './file-image-detail';
import FileImageUpdate from './file-image-update';
import FileImageDeleteDialog from './file-image-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FileImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FileImageDetail} />
      <ErrorBoundaryRoute path={match.url} component={FileImage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FileImageDeleteDialog} />
  </>
);

export default Routes;
