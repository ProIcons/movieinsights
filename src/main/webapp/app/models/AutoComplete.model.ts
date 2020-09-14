import { EntityType } from 'app/models/enumerations/EntityType.enum';
import { BaseNamedEntity } from 'app/models/BaseEntity.Model';

export interface AutoComplete {
  _: ACResult[];
}

export interface ACResult {
  e: ACEntity[];
  i: EntityType;
}

export interface ACEntity extends BaseNamedEntity {
  i: EntityType;
  id: number;
  name: string;
  popularity?: number;
  logoPath?: string;
  profilePath?: string;
}

export const defaultValue: AutoComplete = {
  _: [],
};
