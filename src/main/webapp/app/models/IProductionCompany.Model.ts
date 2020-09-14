import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { IMovie } from 'app/models/IMovie.Model';

export interface IProductionCompany extends BaseNamedEntity {
  tmdbId: number;
  logoPath: string;
  originCountry?: string;
  movies?: IMovie[];
}

export const defaultValue: Readonly<IProductionCompany> = {
  id: 0,
  name: '',
  logoPath: '',
  tmdbId: 0,
};

export function isCompany(object: BaseEntity): object is IProductionCompany {
  return 'id' in object && 'name' in object && 'logoPath' in object;
}
