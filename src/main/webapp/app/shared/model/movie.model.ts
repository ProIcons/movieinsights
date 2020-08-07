import { Moment } from 'moment';
import { ICredit } from 'app/shared/model/credit.model';
import { IImage } from 'app/shared/model/image.model';
import { IBannedPersistentEntity } from 'app/shared/model/banned-persistent-entity.model';
import { IProductionCompany } from 'app/shared/model/production-company.model';
import { IProductionCountry } from 'app/shared/model/production-country.model';
import { IGenre } from 'app/shared/model/genre.model';

export interface IMovie {
  id?: number;
  tmdbId?: number;
  name?: string;
  tagline?: string;
  description?: any;
  revenue?: number;
  budget?: number;
  imdbId?: string;
  popularity?: number;
  runtime?: number;
  posterPath?: string;
  backdropPath?: string;
  releaseDate?: string;
  isBanned?: boolean;
  voteId?: number;
  credits?: ICredit[];
  images?: IImage[];
  banReasons?: IBannedPersistentEntity[];
  companies?: IProductionCompany[];
  countries?: IProductionCountry[];
  genres?: IGenre[];
}

export const defaultValue: Readonly<IMovie> = {
  isBanned: false,
};
