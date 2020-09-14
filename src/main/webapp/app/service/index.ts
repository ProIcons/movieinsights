import { IMovieInsightsPerGenre } from 'app/models/IMovieInsightsPerGenre.Model';
import { IMovieInsightsPerCompany } from 'app/models/IMovieInsightsPerCompany.Model';
import { IMovieInsightsPerCountry } from 'app/models/IMovieInsightsPerCountry.Model';
import { IMovieInsightsGeneral } from 'app/models/IMovieInsightsGeneral.Model';
import axios, { AxiosResponse } from 'axios';

import { ICountryData } from 'app/models/ICountryData';
import { IPersonMultiView } from 'app/models/IPersonMultiView';
import { IMovieInsightsPerYear } from 'app/models/IMovieInsightsPerYear.Model';
import { AutoComplete } from 'app/models/AutoComplete.model';

export const SERVICE_URIS = {
  MOVIEINSIGHTS_GENERAL: '/api/m/ge',
  MOVIEINSIGHTS_PER_COMPANY: '/api/m/cm',
  MOVIEINSIGHTS_PER_COUNTRY: '/api/m/cn',
  MOVIEINSIGHTS_PER_PERSON: '/api/m/p',
  MOVIEINSIGHTS_PER_GENRE: '/api/m/gn',
  COUNTRY: '/api/e/cn',
  SEARCH: '/api/s/ac',
};

interface Service {
  fetchCountryData(): Promise<AxiosResponse<ICountryData[]>>;

  getMovieInsightsPerPerson(id: number): Promise<AxiosResponse<IPersonMultiView>>;

  getMovieInsightsPerPersonPerYear(id: number, year: number): Promise<AxiosResponse<IPersonMultiView>>;

  getMovieInsightsPerGenre(id: number): Promise<AxiosResponse<IMovieInsightsPerGenre>>;

  getMovieInsightsPerGenrePerYear(id: number, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>>;

  getMovieInsightsPerCompany(id: number): Promise<AxiosResponse<IMovieInsightsPerCompany>>;

  getMovieInsightsPerCompanyPerYear(id: number, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>>;

  getMovieInsightsPerCountry(iso: string): Promise<AxiosResponse<IMovieInsightsPerCountry>>;

  getMovieInsightsPerCountryPerYear(iso: string, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>>;

  getMovieInsightsGeneral(): Promise<AxiosResponse<IMovieInsightsGeneral>>;

  getMovieInsightsGeneralPerYear(year: number): Promise<AxiosResponse<IMovieInsightsPerYear>>;
  search(q: string): Promise<AxiosResponse<AutoComplete>>;
}

class ServiceImpl implements Service {
  public async getMovieInsightsGeneral(): Promise<AxiosResponse<IMovieInsightsGeneral>> {
    return await axios.get<IMovieInsightsGeneral>(SERVICE_URIS.MOVIEINSIGHTS_GENERAL);
  }

  public async getMovieInsightsGeneralPerYear(year: number): Promise<AxiosResponse<IMovieInsightsPerYear>> {
    return await axios.get<IMovieInsightsPerYear>(`${SERVICE_URIS.MOVIEINSIGHTS_GENERAL}/${year}`);
  }

  public async getMovieInsightsPerCompany(id: number): Promise<AxiosResponse<IMovieInsightsPerCompany>> {
    return await axios.get<IMovieInsightsPerCompany>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_COMPANY}/${id}`);
  }

  public async getMovieInsightsPerCompanyPerYear(id: number, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>> {
    return await axios.get<IMovieInsightsPerYear>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_COMPANY}/${id}/${year}`);
  }

  public async getMovieInsightsPerCountry(iso: string | number): Promise<AxiosResponse<IMovieInsightsPerCountry>> {
    return await axios.get<IMovieInsightsPerCountry>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_COUNTRY}/${iso}`);
  }

  public async getMovieInsightsPerCountryPerYear(iso: string | number, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>> {
    return await axios.get<IMovieInsightsPerYear>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_COUNTRY}/${iso}/${year}`);
  }

  public async getMovieInsightsPerPerson(id: number): Promise<AxiosResponse<IPersonMultiView>> {
    return await axios.get<IPersonMultiView>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_PERSON}/${id}`);
  }

  public async getMovieInsightsPerPersonPerYear(id: number, year: number): Promise<AxiosResponse<IPersonMultiView>> {
    return await axios.get<IPersonMultiView>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_PERSON}/${id}/${year}`);
  }

  public async getMovieInsightsPerGenre(id: number): Promise<AxiosResponse<IMovieInsightsPerGenre>> {
    return await axios.get<IMovieInsightsPerCompany>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_GENRE}/${id}`);
  }

  public async getMovieInsightsPerGenrePerYear(id: number, year: number): Promise<AxiosResponse<IMovieInsightsPerYear>> {
    return await axios.get<IMovieInsightsPerYear>(`${SERVICE_URIS.MOVIEINSIGHTS_PER_GENRE}/${id}/${year}`);
  }

  public async search(q: string): Promise<AxiosResponse<AutoComplete>> {
    return await axios.get<AutoComplete>(`${SERVICE_URIS.SEARCH}?q=${q}`);
  }

  public async fetchCountryData(): Promise<AxiosResponse<ICountryData[]>> {
    return await axios.get<ICountryData[]>(`${SERVICE_URIS.COUNTRY}`);
  }
}

const serviceInstance = new ServiceImpl();
export { serviceInstance as Service };
