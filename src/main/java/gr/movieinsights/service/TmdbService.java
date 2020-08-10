package gr.movieinsights.service;

import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Person;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import gr.movieinsights.config.tmdb.MovieInsightsTmdb;
import gr.movieinsights.models.TmdbImportEntity;
import gr.movieinsights.domain.enumeration.TmdbEntityType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class TmdbService {
    private final Logger log = LoggerFactory.getLogger(TmdbService.class);


    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_yyyy");
    private final Calendar calendar = Calendar.getInstance();
    private final Gson gson = new Gson();

    private final MovieInsightsTmdb tmdb;
    private final OkHttpClient okHttpClient;


    public TmdbService(MovieInsightsTmdb tmdb, OkHttpClient okHttpClient) {
        this.tmdb = tmdb;
        this.okHttpClient = okHttpClient;
    }


    public TmdbImportEntity getTmdbDailyExportFile(TmdbEntityType entityType) {
        int tries = 0;
        do {
            try {
                String formattedDate = simpleDateFormat.format(calendar.getTime());
                Request request = new Request.Builder().url("http://files.tmdb.org/p/exports/" + entityType.getExportIdentifier() + "_" + formattedDate + ".json.gz").build();

                log.debug("[{}] Download Started: http://files.tmdb.org/p/exports/{}_{}.json.gz", entityType, entityType.getExportIdentifier(), formattedDate);
                Response response = okHttpClient.newCall(request).execute();

                StringBuilder sb = new StringBuilder();
                sb.append("{\"results\":[");
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.body() != null) {
                        response.body().close();
                    }
                    response.close();
                    log.warn("[{}] It seems there's no export for this date: {}. Trying a previous day...", entityType, simpleDateFormat.format(calendar.getTime()));
                    calendar.add(Calendar.DATE, -1);
                    tries++;
                    continue;
                }
                log.debug("[{}] Download Completed. Uncompressing...", entityType);
                try (GzipCompressorInputStream in = new GzipCompressorInputStream(response.body().byteStream())) {
                    BufferedReader bR = new BufferedReader(new InputStreamReader(in));
                    String line;
                    int i = 0;
                    while ((line = bR.readLine()) != null) {
                        if (i > 0) sb.append(",");
                        sb.append(line);
                        i++;
                    }
                    sb.append("]}");
                }
                log.debug("[{}] {}_{}.json.gz Uncompressed", entityType, entityType.getExportIdentifier(), formattedDate);
                response.body().close();
                response.close();
                TmdbImportEntity tmdbEntity = gson.fromJson(sb.toString(), TmdbImportEntity.class);
                for (TmdbImportEntity.Entity entity : tmdbEntity.getResults()) {
                    entity.setEntityType(entityType);
                }
                return tmdbEntity;

            } catch (IOException exc) {
                log.error("[{}] There was a network error: {}. Aborting...", entityType, exc.getMessage());
                return null;
                //calendar.add(Calendar.DATE, -1);
                //tries++;
            }
        } while (tries < 5);
        log.error("[{}] No exports found for the last 5 days. Permanently Aborting.", entityType);
        return null;
    }

    public Call<Movie> getMovieCallByImdbId(String imdbId) {
        return tmdb.moviesService2().summary(imdbId, "en_US", new AppendToResponse(AppendToResponseItem.CREDITS));
    }

    public Movie getMovieByImdbId(String imdbId) throws IOException {
        return getMovieCallByImdbId(imdbId).execute().body();
    }

    public Call<Movie> getMovieCallByTmdbId(Long imdbId) {
        return tmdb.moviesService2().summary(imdbId.intValue(), "en_US", new AppendToResponse(AppendToResponseItem.CREDITS));
    }

    public Movie getMovieByTmdbId(Long tmdbId) throws IOException {
        return getMovieCallByTmdbId(tmdbId).execute().body();
    }

    public Call<Movie> getMovieCallByTmdbId(int imdbId) {
        return tmdb.moviesService2().summary(imdbId, "en_US", new AppendToResponse(AppendToResponseItem.CREDITS));
    }

    public Movie getMovieByTmdbId(int tmdbId) throws IOException {
        return getMovieCallByTmdbId(tmdbId).execute().body();
    }

    public Call<Person> getPersonCallByTmdbId(int tmdbId) {
        return tmdb.personService().summary(tmdbId, "en_US");
    }

    public Person getPersonByTmdbId(int tmdbId) throws IOException {
        return getPersonCallByTmdbId(tmdbId).execute().body();
    }


}
