package gr.movieinsights.config.tmdb.services;

import com.uwetrottmann.tmdb2.entities.AccountStates;
import com.uwetrottmann.tmdb2.entities.AlternativeTitles;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.Changes;
import com.uwetrottmann.tmdb2.entities.Credits;
import com.uwetrottmann.tmdb2.entities.Images;
import com.uwetrottmann.tmdb2.entities.Keywords;
import com.uwetrottmann.tmdb2.entities.ListResultsPage;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieExternalIds;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.RatingObject;
import com.uwetrottmann.tmdb2.entities.ReleaseDatesResults;
import com.uwetrottmann.tmdb2.entities.ReviewResultsPage;
import com.uwetrottmann.tmdb2.entities.Status;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.entities.Translations;
import com.uwetrottmann.tmdb2.entities.Videos;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface MoviesService {
    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") String var1);


    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") String var1, @Query("language") String var2, @Query("append_to_response") AppendToResponse var3);

    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") String var1, @Query("language") String var2, @Query("append_to_response") AppendToResponse var3, @QueryMap Map<String, String> var4);


    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") int var1, @Query("language") String var2);

    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") int var1, @Query("language") String var2, @Query("append_to_response") AppendToResponse var3);

    @GET("movie/{movie_id}")
    Call<Movie> summary(@Path("movie_id") int var1, @Query("language") String var2, @Query("append_to_response") AppendToResponse var3, @QueryMap Map<String, String> var4);

    @GET("movie/{movie_id}/account_states")
    Call<AccountStates> accountStates(@Path("movie_id") int var1);

    @GET("movie/{movie_id}/alternative_titles")
    Call<AlternativeTitles> alternativeTitles(@Path("movie_id") int var1, @Query("country") String var2);

    @GET("movie/{movie_id}/changes")
    Call<Changes> changes(@Path("movie_id") int var1, @Query("start_date") TmdbDate var2, @Query("end_date") TmdbDate var3, @Query("page") Integer var4);

    @GET("movie/{movie_id}/credits")
    Call<Credits> credits(@Path("movie_id") int var1);

    @GET("movie/{movie_id}/external_ids")
    Call<MovieExternalIds> externalIds(@Path("movie_id") int var1, @Query("language") String var2);

    @GET("movie/{movie_id}/images")
    Call<Images> images(@Path("movie_id") int var1, @Query("language") String var2);

    @GET("movie/{movie_id}/keywords")
    Call<Keywords> keywords(@Path("movie_id") int var1);

    @GET("movie/{movie_id}/lists")
    Call<ListResultsPage> lists(@Path("movie_id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("movie/{movie_id}/similar")
    Call<MovieResultsPage> similar(@Path("movie_id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("movie/{movie_id}/recommendations")
    Call<MovieResultsPage> recommendations(@Path("movie_id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("movie/{movie_id}/release_dates")
    Call<ReleaseDatesResults> releaseDates(@Path("movie_id") int var1);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResultsPage> reviews(@Path("movie_id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("movie/{movie_id}/translations")
    Call<Translations> translations(@Path("movie_id") int var1);

    @GET("movie/{movie_id}/videos")
    Call<Videos> videos(@Path("movie_id") int var1, @Query("language") String var2);

    @GET("movie/latest")
    Call<Movie> latest();

    @GET("movie/now_playing")
    Call<MovieResultsPage> nowPlaying(@Query("page") Integer var1, @Query("language") String var2, @Query("region") String var3);

    @GET("movie/popular")
    Call<MovieResultsPage> popular(@Query("page") Integer var1, @Query("language") String var2, @Query("region") String var3);

    @GET("movie/top_rated")
    Call<MovieResultsPage> topRated(@Query("page") Integer var1, @Query("language") String var2, @Query("region") String var3);

    @GET("movie/upcoming")
    Call<MovieResultsPage> upcoming(@Query("page") Integer var1, @Query("language") String var2, @Query("region") String var3);

    @POST("movie/{movie_id}/rating")
    Call<Status> addRating(@Path("movie_id") Integer var1, @Body RatingObject var2);

    @DELETE("movie/{movie_id}/rating")
    Call<Status> deleteRating(@Path("movie_id") Integer var1);
}
