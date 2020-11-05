import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './file-image.reducer';
import { IFileImage } from 'app/shared/model/file-image.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileImageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFileImageUpdateState {
  isNew: boolean;
}

export class FileImageUpdate extends React.Component<IFileImageUpdateProps, IFileImageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { fileImageEntity } = this.props;
      const entity = {
        ...fileImageEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/file-image');
  };

  render() {
    const { fileImageEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="filestorageApp.fileImage.home.createOrEditLabel">
              <Translate contentKey="filestorageApp.fileImage.home.createOrEditLabel">Create or edit a FileImage</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fileImageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="file-image-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="file-image-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="imagePathLabel" for="file-image-imagePath">
                    <Translate contentKey="filestorageApp.fileImage.imagePath">Image Path</Translate>
                  </Label>
                  <AvField id="file-image-imagePath" type="text" name="imagePath" />
                </AvGroup>
                <AvGroup>
                  <Label id="expiryDateLabel" for="file-image-expiryDate">
                    <Translate contentKey="filestorageApp.fileImage.expiryDate">Expiry Date</Translate>
                  </Label>
                  <AvField id="file-image-expiryDate" type="date" className="form-control" name="expiryDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="sizeLabel" for="file-image-size">
                    <Translate contentKey="filestorageApp.fileImage.size">Size</Translate>
                  </Label>
                  <AvField id="file-image-size" type="text" name="size" />
                </AvGroup>
                <AvGroup>
                  <Label id="mimeTypeLabel" for="file-image-mimeType">
                    <Translate contentKey="filestorageApp.fileImage.mimeType">Mime Type</Translate>
                  </Label>
                  <AvField id="file-image-mimeType" type="text" name="mimeType" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/file-image" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  fileImageEntity: storeState.fileImage.entity,
  loading: storeState.fileImage.loading,
  updating: storeState.fileImage.updating,
  updateSuccess: storeState.fileImage.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileImageUpdate);
