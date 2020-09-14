export interface BaseEntity {
  id: number;
}

export interface BaseNamedEntity extends BaseEntity {
  name: string;
}

export function isBaseEntity(object: any): object is BaseEntity {
  return 'id' in object;
}

export function isBaseNamedEntity(object: any): object is BaseNamedEntity {
  return 'id' in object && 'name' in object;
}
