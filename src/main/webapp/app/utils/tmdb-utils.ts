import { TMDB_BACKDROP_SIZE, TMDB_IMAGE_URL, TMDB_LOGO_SIZE, TMDB_POSTER_SIZE, TMDB_PROFILE_SIZE } from 'app/config/constants';

export namespace TmdbUtils {
  export const getPosterUrl = (size: TMDB_POSTER_SIZE, url: string): string => {
    return `${TMDB_IMAGE_URL}/${size}/${url}`;
  };

  export const getBackdropUrl = (size: TMDB_BACKDROP_SIZE, url: string): string => {
    return `${TMDB_IMAGE_URL}/${size}/${url}`;
  };

  export const getProfileUrl = (size: TMDB_PROFILE_SIZE, url: string): string => {
    return `${TMDB_IMAGE_URL}/${size}/${url}`;
  };

  export const getLogoUrl = (size: TMDB_LOGO_SIZE, url: string, svg?: boolean): string => {
    return `${TMDB_IMAGE_URL}/${size}/${svg ? url.replace(/(\.(jpg|png))/, '.svg') : url}`;
  };
}
