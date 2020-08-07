import { Moment } from 'moment';
import { BanReason } from 'app/shared/model/enumerations/ban-reason.model';

export interface IBannedPersistentEntity {
  id?: number;
  reason?: BanReason;
  reasonText?: string;
  timestamp?: string;
  bannedEntityId?: number;
  movieId?: number;
  personId?: number;
  productionCompanyId?: number;
}

export const defaultValue: Readonly<IBannedPersistentEntity> = {};
