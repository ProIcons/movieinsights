export interface IMovieInsights {
  id?: number;
  averageRating?: number;
  averageBudget?: number;
  averageRevenue?: number;
  totalMovies?: number;
  mostPopularGenreMovieCount?: number;
  mostPopularActorMovieCount?: number;
  mostPopularWriterMovieCount?: number;
  mostPopularProducerMovieCount?: number;
  mostPopularDirectorMovieCount?: number;
  mostPopularProductionCompanyMovieCount?: number;
  mostPopularProductionCountryMovieCount?: number;
  highestRatedMovieId?: number;
  lowestRatedMovieId?: number;
  highestBudgetMovieId?: number;
  lowestBudgetMovieId?: number;
  highestRevenueMovieId?: number;
  lowestRevenueMovieId?: number;
  mostPopularGenreId?: number;
  mostPopularActorId?: number;
  mostPopularProducerId?: number;
  mostPopularWriterId?: number;
  mostPopularDirectorId?: number;
  mostPopularProductionCountryId?: number;
  mostPopularProductionCompanyId?: number;
}

export const defaultValue: Readonly<IMovieInsights> = {};
