import { IUser } from 'app/shared/model/user.model';
import { IFileImage } from 'app/shared/model/file-image.model';

export interface IDeffectiveProduct {
  id?: number;
  inventoryNumber?: string;
  description?: string;
  user?: IUser;
  fileImages?: IFileImage[];
}

export const defaultValue: Readonly<IDeffectiveProduct> = {};
