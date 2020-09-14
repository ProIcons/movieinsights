import { BaseEntity } from 'app/models/BaseEntity.Model';
import { CreditRole } from 'app/models/enumerations';
import { IMovie, defaultValue as movieDefaultValue } from 'app/models/IMovie.Model';
import { IPerson, defaultValue as personDefaultValue } from 'app/models/IPerson.Model';

export interface ICredit extends BaseEntity {
  creditId: string;
  role: CreditRole;
  movie: IMovie;
  person: IPerson;
}

export const defaultValue: Readonly<ICredit> = {
  id: 0,
  creditId: '',
  role: undefined,
  movie: movieDefaultValue,
  person: personDefaultValue,
};

export function isCredit(object: BaseEntity): object is ICredit {
  return 'creditId' in object;
}
