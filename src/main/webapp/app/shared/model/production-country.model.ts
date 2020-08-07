import { IMovie } from 'app/shared/model/movie.model';

export interface IProductionCountry {
  id?: number;
  name?: string;
  iso31661?: string;
  movies?: IMovie[];
}

export const defaultValue: Readonly<IProductionCountry> = {};
