import { Moment } from 'moment';
import { ICredit } from 'app/shared/model/credit.model';

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
}

export const defaultValue: Readonly<IPerson> = {};
