export interface IMovieInsightsPerYear {
  id?: number;
  year?: number;
  movieInsightsId?: number;
  movieInsightsPerCountryId?: number;
  movieInsightsPerCompanyId?: number;
  movieInsightsPerPersonId?: number;
  movieInsightsPerGenreId?: number;
}

export const defaultValue: Readonly<IMovieInsightsPerYear> = {};
