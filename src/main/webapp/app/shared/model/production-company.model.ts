import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { IMovie } from 'app/shared/model/movie.model';

export interface IProductionCompany {
  id?: number;
  name?: string;
  tmdbId?: number;
  logoPath?: string;
  originCountry?: string;
  banReasons?: IBannedPersistentEntity[];
  movies?: IMovie[];
}

export const defaultValue: Readonly<IProductionCompany> = {};
