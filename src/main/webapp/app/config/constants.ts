const config = {
  VERSION: process.env.VERSION,
};

export default config;

export const SERVER_API_URL = process.env.SERVER_API_URL;

export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
};

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error',
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const APP_LOCAL_DATETIME_FORMAT_Z = 'YYYY-MM-DDTHH:mm Z';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';
export const TMDB_IMAGE_URL = 'https://image.tmdb.org/t/p';

export enum TMDB_STILL_SIZE {
  W92 = 'w92',
  W185 = 'w185',
  W300 = 'w300',
  ORIGINAL = 'original',
}

export enum TMDB_LOGO_SIZE {
  W45 = 'w45',
  W92 = 'w92',
  W154 = 'w154',
  W185 = 'w185',
  W300 = 'w300',
  W500 = 'w500',
  ORIGINAL = 'original',
}

export enum TMDB_BACKDROP_SIZE {
  W300 = 'w300',
  W780 = 'w780',
  W1280 = 'w1280',
  ORIGINAL = 'original',
}

export enum TMDB_POSTER_SIZE {
  W92 = 'w92',
  W154 = 'w154',
  W185 = 'w185',
  W342 = 'w342',
  W500 = 'w500',
  W700 = 'w700',
  ORIGINAL = 'original',
}

export enum TMDB_PROFILE_SIZE {
  W45 = 'w45',
  W185 = 'w185',
  W632 = 'w632',
  ORIGINAL = 'original',
}
function getRandomInt(min: number, max: number): number {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export const MOVIE_POSTER_PLACEHOLDER = '/content/images/movie_poster_placeholder_big.svg';
export const PERSON_PROFILE_PLACEHOLDER_RAND = () => `/content/images/jhipster_family_member_${getRandomInt(0, 4)}.svg`;
export const PERSON_PROFILE_PLACEHOLDER = `/content/images/person_profile_placeholder.svg`;

export const TRANSLATION_ROOT_INDEX = 'movieInsightsApp';
