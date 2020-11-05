import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-image.reducer';
import { IFileImage } from 'app/shared/model/file-image.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileImageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FileImageDetail extends React.Component<IFileImageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileImageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="filestorageApp.fileImage.detail.title">FileImage</Translate> [<b>{fileImageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="imagePath">
                <Translate contentKey="filestorageApp.fileImage.imagePath">Image Path</Translate>
              </span>
            </dt>
            <dd>{fileImageEntity.imagePath}</dd>
            <dt>
              <span id="expiryDate">
                <Translate contentKey="filestorageApp.fileImage.expiryDate">Expiry Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fileImageEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="size">
                <Translate contentKey="filestorageApp.fileImage.size">Size</Translate>
              </span>
            </dt>
            <dd>{fileImageEntity.size}</dd>
            <dt>
              <span id="mimeType">
                <Translate contentKey="filestorageApp.fileImage.mimeType">Mime Type</Translate>
              </span>
            </dt>
            <dd>{fileImageEntity.mimeType}</dd>
          </dl>
          <Button tag={Link} to="/entity/file-image" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/file-image/${fileImageEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ fileImage }: IRootState) => ({
  fileImageEntity: fileImage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileImageDetail);
