import { IMovie } from 'app/shared/model/movie.model';

export interface IGenre {
  id?: number;
  tmdbId?: number;
  name?: string;
  movies?: IMovie[];
}

export const defaultValue: Readonly<IGenre> = {};
