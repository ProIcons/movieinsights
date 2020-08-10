import { TmdbEntityType } from 'app/shared/model/enumerations/tmdb-entity-type.model';

export interface IBannedEntity {
  id?: number;
  tmdbId?: number;
  imdb?: string;
  type?: TmdbEntityType;
  bannedPersistentEntityId?: number;
}

export const defaultValue: Readonly<IBannedEntity> = {};
