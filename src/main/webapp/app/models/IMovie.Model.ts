import { BaseEntity, BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { IVote, defaultValue as voteDefaultValue } from 'app/models/IVote.Model';
import { ICredit } from 'app/models/ICredit.Model';
import { IProductionCompany } from 'app/models/IProductionCompany.Model';
import { IProductionCountry } from 'app/models/IProductionCountry.Model';
import { IGenre } from 'app/models/IGenre.Model';

export interface IMovie extends BaseNamedEntity {
  tmdbId: number;
  imdbId: string;
  revenue: number;
  budget: number;
  runtime: number;
  posterPath: string;
  backdropPath: string;
  vote: IVote;
  popularity?: number;
  tagline?: string;
  description?: any;
  releaseDate?: string;
  credits?: ICredit[];
  companies?: IProductionCompany[];
  countries?: IProductionCountry[];
  genres?: IGenre[];
}

export const defaultValue: Readonly<IMovie> = {
  id: 0,
  name: '',
  tmdbId: 0,
  imdbId: '',
  revenue: 0,
  budget: 0,
  runtime: 0,
  posterPath: '',
  backdropPath: '',
  vote: voteDefaultValue,
};

export function isMovie(object: BaseEntity): object is IMovie {
  return 'vote' in object && 'tmdbId' in object && 'imdbId' in object;
}
