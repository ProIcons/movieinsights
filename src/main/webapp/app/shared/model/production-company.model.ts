import { IMovie } from 'app/shared/model/movie.model';

export interface IProductionCompany {
  id?: number;
  name?: string;
  tmdbId?: number;
  logoPath?: string;
  originCountry?: string;
  movies?: IMovie[];
}

export const defaultValue: Readonly<IProductionCompany> = {};
