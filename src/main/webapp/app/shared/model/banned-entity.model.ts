import { EntityType } from 'app/shared/model/enumerations/entity-type.model';

export interface IBannedEntity {
  id?: number;
  tmdbId?: number;
  type?: EntityType;
  bannedPersistentEntityId?: number;
}

export const defaultValue: Readonly<IBannedEntity> = {};
