export interface IImage {
  id?: number;
  tmdbId?: number;
  aspectRatio?: number;
  filePath?: string;
  height?: number;
  width?: number;
  iso6391?: string;
  voteId?: number;
  movieId?: number;
  personId?: number;
  creditId?: number;
}

export const defaultValue: Readonly<IImage> = {};
