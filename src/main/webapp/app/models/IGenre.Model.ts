import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { IMovie } from 'app/models/IMovie.Model';

export interface IGenre extends BaseNamedEntity {
  tmdbId: number;
  movies?: IMovie[];
  genre?: any;
}

export const defaultValue: Readonly<IGenre> = {
  id: 0,
  name: '',
  tmdbId: 0,
};

export function isGenre(object: BaseEntity): object is IGenre {
  return 'id' in object && 'name' in object;
}
