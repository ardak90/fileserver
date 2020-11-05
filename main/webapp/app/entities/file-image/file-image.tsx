import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './file-image.reducer';
import { IFileImage } from 'app/shared/model/file-image.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileImageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FileImage extends React.Component<IFileImageProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { fileImageList, match } = this.props;
    return (
      <div>
        <h2 id="file-image-heading">
          <Translate contentKey="filestorageApp.fileImage.home.title">File Images</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="filestorageApp.fileImage.home.createLabel">Create new File Image</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {fileImageList && fileImageList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="filestorageApp.fileImage.imagePath">Image Path</Translate>
                  </th>
                  <th>
                    <Translate contentKey="filestorageApp.fileImage.expiryDate">Expiry Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="filestorageApp.fileImage.size">Size</Translate>
                  </th>
                  <th>
                    <Translate contentKey="filestorageApp.fileImage.mimeType">Mime Type</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fileImageList.map((fileImage, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${fileImage.id}`} color="link" size="sm">
                        {fileImage.id}
                      </Button>
                    </td>
                    <td>{fileImage.imagePath}</td>
                    <td>
                      <TextFormat type="date" value={fileImage.expiryDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{fileImage.size}</td>
                    <td>{fileImage.mimeType}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${fileImage.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${fileImage.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${fileImage.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="filestorageApp.fileImage.home.notFound">No File Images found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ fileImage }: IRootState) => ({
  fileImageList: fileImage.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileImage);
