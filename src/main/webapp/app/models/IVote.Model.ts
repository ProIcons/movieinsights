import { BaseEntity } from 'app/models/BaseEntity.Model';

export interface IVote extends BaseEntity {
  average: number;
  votes: number;
}

export const defaultValue: Readonly<IVote> = {
  id: 0,
  average: 0,
  votes: 0,
};
