export interface IVote {
  id?: number;
  average?: number;
  votes?: number;
}

export const defaultValue: Readonly<IVote> = {};
