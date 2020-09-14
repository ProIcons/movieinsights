import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { ICredit } from 'app/models/ICredit.Model';

export interface IPerson extends BaseNamedEntity {
  tmdbId: number;
  imdbId: string;
  profilePath: string;
  popularity: number;
  biography?: any;
  birthDay?: string;
  credits?: ICredit[];
}

export const defaultValue: Readonly<IPerson> = {
  id: 0,
  name: '',
  tmdbId: 0,
  imdbId: '',
  profilePath: '',
  popularity: 0,
};
export function isPerson(object: BaseEntity): object is IPerson {
  return 'id' in object && 'name' in object && 'profilePath' in object;
}
