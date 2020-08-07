import { IImage } from 'app/shared/model/image.model';
import { CreditRole } from 'app/shared/model/enumerations/credit-role.model';

export interface ICredit {
  id?: number;
  creditId?: string;
  personTmdbId?: number;
  movieTmdbId?: number;
  role?: CreditRole;
  images?: IImage[];
  movieId?: number;
  personId?: number;
}

export const defaultValue: Readonly<ICredit> = {};
