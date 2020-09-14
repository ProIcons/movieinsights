import { defaultValue as movieInsightsDefaultValue, IMovieInsights } from 'app/models/IMovieInsights.Model';
import { CreditRole } from 'app/models/enumerations';
import { IPerson, defaultValue as personDefaultValue } from 'app/models/IPerson.Model';

export interface IPersonMultiView {
  id: number;
  roles: CreditRole[];
  person: IPerson;
  year?: number;
  perYear: boolean;
  actor: IMovieInsights;
  actorYearData: number[][];
  director: IMovieInsights;
  directorYearData: number[][];
  writer: IMovieInsights;
  writerYearData: number[][];
  producer: IMovieInsights;
  producerYearData: number[][];
}

export const defaultValue: Readonly<IPersonMultiView> = {
  id: 0,
  roles: [],
  person: personDefaultValue,
  year: -1,
  perYear: false,
  actor: movieInsightsDefaultValue,
  actorYearData: [],
  director: movieInsightsDefaultValue,
  directorYearData: [],
  writer: movieInsightsDefaultValue,
  writerYearData: [],
  producer: movieInsightsDefaultValue,
  producerYearData: [],
};
export function isPersonMultiView(object: any): object is IPersonMultiView {
  return 'actor' in object && 'director' in object && 'producer' in object && 'writer' in object && 'roles' in object && 'person' in object;
}
