import { Moment } from 'moment';
import { ICredit } from 'app/shared/model/credit.model';
import { IImage } from 'app/shared/model/image.model';
import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';

export interface IPerson {
  id?: number;
  tmdbId?: number;
  name?: string;
  imdbId?: string;
  popularity?: number;
  biography?: any;
  birthDay?: string;
  profilePath?: string;
  credits?: ICredit[];
  images?: IImage[];
  banReasons?: IBannedPersistentEntity[];
}

export const defaultValue: Readonly<IPerson> = {};
