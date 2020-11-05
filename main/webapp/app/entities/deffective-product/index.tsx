import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeffectiveProduct from './deffective-product';
import DeffectiveProductDetail from './deffective-product-detail';
import DeffectiveProductUpdate from './deffective-product-update';
import DeffectiveProductDeleteDialog from './deffective-product-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeffectiveProductUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeffectiveProductUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeffectiveProductDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeffectiveProduct} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DeffectiveProductDeleteDialog} />
  </>
);

export default Routes;
