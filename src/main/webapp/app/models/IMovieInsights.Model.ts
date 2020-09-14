import { BaseEntity } from 'app/models/BaseEntity.Model';
import { IMovie, defaultValue as movieDefaultValue } from 'app/models/IMovie.Model';
import { IGenre, defaultValue as genreDefaultValue } from 'app/models/IGenre.Model';
import { IPerson, defaultValue as personDefaultValue } from 'app/models/IPerson.Model';
import { IProductionCompany, defaultValue as companyDefaultValue } from 'app/models/IProductionCompany.Model';
import { IProductionCountry, defaultValue as countryDefaultValue } from 'app/models/IProductionCountry.Model';

export interface IMovieInsights extends BaseEntity {
  totalMovies: number;
  averageRating: number;
  totalRatedMovies: number;
  highestRatedMovie: IMovie;
  lowestRatedMovie: IMovie;
  highestRatedLogarithmicMovie: IMovie;
  lowestRatedLogarithmicMovie: IMovie;

  averageBudget: number;
  totalBudget: number;
  totalBudgetMovies: number;
  highestBudgetMovie: IMovie;
  lowestBudgetMovie: IMovie;

  averageRevenue: number;
  totalRevenue: number;
  totalRevenueMovies: number;
  highestRevenueMovie: IMovie;
  lowestRevenueMovie: IMovie;

  totalGenres: number;
  averageGenreCount: number;
  mostPopularGenreMovieCount: number;
  mostPopularGenre: IGenre;
  leastPopularGenreMovieCount: number;
  leastPopularGenre: IGenre;

  totalActors: number;
  averageActorCount: number;
  mostPopularActorMovieCount: number;
  mostPopularActor: IPerson;
  leastPopularActorMovieCount: number;
  leastPopularActor: IPerson;

  totalProducers: number;
  averageProducerCount: number;
  mostPopularProducerMovieCount: number;
  mostPopularProducer: IPerson;
  leastPopularProducerMovieCount: number;
  leastPopularProducer: IPerson;

  totalWriters: number;
  averageWriterCount: number;
  mostPopularWriterMovieCount: number;
  mostPopularWriter: IPerson;
  leastPopularWriterMovieCount: number;
  leastPopularWriter: IPerson;

  totalDirectors: number;
  averageDirectorCount: number;
  mostPopularDirectorMovieCount: number;
  mostPopularDirector: IPerson;
  leastPopularDirectorMovieCount: number;
  leastPopularDirector: IPerson;

  totalProductionCompanies: number;
  averageProductionCompaniesCount: number;
  mostPopularProductionCompanyMovieCount: number;
  mostPopularProductionCompany: IProductionCompany;
  leastPopularProductionCompanyMovieCount: number;
  leastPopularProductionCompany: IProductionCompany;

  totalProductionCountries: number;
  averageProductionCountriesCount: number;
  mostPopularProductionCountryMovieCount: number;
  mostPopularProductionCountry: IProductionCountry;
  leastPopularProductionCountryMovieCount: number;
  leastPopularProductionCountry: IProductionCountry;
}

export const defaultValue: Readonly<IMovieInsights> = {
  averageActorCount: 0,
  averageDirectorCount: 0,
  averageGenreCount: 0,
  averageProducerCount: 0,
  averageProductionCompaniesCount: 0,
  averageProductionCountriesCount: 0,
  averageWriterCount: 0,
  leastPopularActorMovieCount: 0,
  leastPopularDirectorMovieCount: 0,
  leastPopularGenreMovieCount: 0,
  leastPopularProducerMovieCount: 0,
  leastPopularProductionCompanyMovieCount: 0,
  leastPopularProductionCountryMovieCount: 0,
  leastPopularWriterMovieCount: 0,
  totalActors: 0,
  totalDirectors: 0,
  totalGenres: 0,
  totalProducers: 0,
  totalProductionCompanies: 0,
  totalProductionCountries: 0,
  totalRatedMovies: 0,
  totalWriters: 0,
  id: 0,
  averageRating: 0,
  averageBudget: 0,
  totalBudget: 0,
  totalBudgetMovies: 0,
  averageRevenue: 0,
  totalRevenue: 0,
  totalRevenueMovies: 0,
  totalMovies: 0,
  mostPopularGenreMovieCount: 0,
  mostPopularActorMovieCount: 0,
  mostPopularWriterMovieCount: 0,
  mostPopularProducerMovieCount: 0,
  mostPopularDirectorMovieCount: 0,
  mostPopularProductionCompanyMovieCount: 0,
  mostPopularProductionCountryMovieCount: 0,
  highestRatedMovie: movieDefaultValue,
  lowestRatedMovie: movieDefaultValue,
  highestBudgetMovie: movieDefaultValue,
  lowestBudgetMovie: movieDefaultValue,
  highestRevenueMovie: movieDefaultValue,
  lowestRevenueMovie: movieDefaultValue,
  mostPopularGenre: genreDefaultValue,
  mostPopularActor: personDefaultValue,
  mostPopularProducer: personDefaultValue,
  mostPopularWriter: personDefaultValue,
  mostPopularDirector: personDefaultValue,
  mostPopularProductionCountry: countryDefaultValue,
  mostPopularProductionCompany: companyDefaultValue,
  highestRatedLogarithmicMovie: movieDefaultValue,
  leastPopularActor: personDefaultValue,
  leastPopularDirector: personDefaultValue,
  leastPopularGenre: genreDefaultValue,
  leastPopularProducer: personDefaultValue,
  leastPopularProductionCompany: companyDefaultValue,
  leastPopularProductionCountry: countryDefaultValue,
  leastPopularWriter: personDefaultValue,
  lowestRatedLogarithmicMovie: movieDefaultValue,
};
