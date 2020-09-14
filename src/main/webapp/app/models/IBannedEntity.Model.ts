import { BanReason, TmdbEntityType } from 'app/models/enumerations';

export interface IBannedEntity {
  id?: number;
  tmdbId?: number;
  imdbId?: string;
  type?: TmdbEntityType;
  reason?: BanReason;
  reasonText?: string;
  timestamp?: string;
}

export const defaultValue: Readonly<IBannedEntity> = {};
