import { Moment } from 'moment';

export interface IFileImage {
  id?: number;
  imagePath?: string;
  expiryDate?: Moment;
  size?: string;
  mimeType?: string;
}

export const defaultValue: Readonly<IFileImage> = {};
