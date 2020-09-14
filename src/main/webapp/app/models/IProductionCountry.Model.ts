import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { IMovie } from 'app/models/IMovie.Model';

export interface IProductionCountry extends BaseNamedEntity {
  iso31661: string;
  movies?: IMovie[];
}

export const defaultValue: Readonly<IProductionCountry> = {
  id: 0,
  name: '',
  iso31661: '',
};

export function isCountry(object: BaseEntity): object is IProductionCountry {
  return 'id' in object && 'name' in object && 'iso31661' in object;
}
