import { SeriesMapDataOptions } from 'highcharts';

export interface ICountryData extends SeriesMapDataOptions {
  _id: number;
  value: number;
  iso31661: string;
  name: string;
}

export const defaultValue = {
  _id: 0,
  value: 0,
  iso31661: '',
  name: '',
} as ICountryData;
