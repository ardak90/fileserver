import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './deffective-product.reducer';
import { IDeffectiveProduct } from 'app/shared/model/deffective-product.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeffectiveProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DeffectiveProductDetail extends React.Component<IDeffectiveProductDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { deffectiveProductEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="filestorageApp.deffectiveProduct.detail.title">DeffectiveProduct</Translate> [
            <b>{deffectiveProductEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="inventoryNumber">
                <Translate contentKey="filestorageApp.deffectiveProduct.inventoryNumber">Inventory Number</Translate>
              </span>
            </dt>
            <dd>{deffectiveProductEntity.inventoryNumber}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="filestorageApp.deffectiveProduct.description">Description</Translate>
              </span>
            </dt>
            <dd>{deffectiveProductEntity.description}</dd>
            <dt>
              <Translate contentKey="filestorageApp.deffectiveProduct.user">User</Translate>
            </dt>
            <dd>{deffectiveProductEntity.user ? deffectiveProductEntity.user.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/deffective-product" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/deffective-product/${deffectiveProductEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ deffectiveProduct }: IRootState) => ({
  deffectiveProductEntity: deffectiveProduct.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeffectiveProductDetail);
