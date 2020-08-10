package gr.movieinsights.service;

import com.uwetrottmann.tmdb2.entities.Movie;
import gr.movieinsights.domain.Vote;
import gr.movieinsights.domain.enumeration.ImdbEntityType;
import gr.movieinsights.models.ImdbImportEntity;
import gr.movieinsights.models.ImdbImportMovie;
import gr.movieinsights.models.ImdbImportRating;
import gr.movieinsights.service.util.CustomCSVTokenizer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.ITokenizer;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ImdbService {
    private final Logger log = LoggerFactory.getLogger(ImdbService.class);
    private final OkHttpClient okHttpClient;

    private Map<String, ImdbImportRating> ratings;
    private Map<String, ImdbImportMovie> titles;


    public ImdbService(OkHttpClient okHttpClient) {

        this.okHttpClient = okHttpClient;
    }

    public void loadRatings() {
        ratings = getImdbData(ImdbImportRating.class);
    }

    public void loadTitles() {
        titles = getImdbData(ImdbImportMovie.class);
    }

    public <T extends ImdbImportEntity> Map<String, T> getImdbData(Class<T> objectClass) {
        ImdbEntityType entityType = ImdbEntityType.getImdbEntityTypeByClass(objectClass);
        int tries = 0;
        do {
            try {
                String url = String.format("https://datasets.imdbws.com/%s.tsv.gz", entityType.getExportIdentifier());
                Request request = new Request.Builder().url(url).build();
                log.debug("Download Started: {}", url);
                Response response = okHttpClient.newCall(request).execute();
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.body() != null) {
                        response.body().close();
                    }
                    response.close();
                }
                try (GzipCompressorInputStream in = new GzipCompressorInputStream(response.body().byteStream())) {
                    BufferedReader bR = new BufferedReader(new InputStreamReader(in));

                    try {

                        ITokenizer tokenizer = new CustomCSVTokenizer(bR, CsvPreference.STANDARD_PREFERENCE);

                        Map<String, T> objectMap = new HashMap<>();
                        ICsvDozerBeanReader csvReader = new CsvDozerBeanReader(tokenizer, CsvPreference.TAB_PREFERENCE);
                        final String[] headers = entityType.getCellHeaderProvider() != null ? entityType.getCellHeaderProvider().apply(csvReader) : csvReader.getHeader(true);
                        csvReader.configureBeanMapping(objectClass, headers);
                        T imdbObject;
                        while ((imdbObject = csvReader.read(objectClass)) != null) {
                            objectMap.put(imdbObject.getTconst(), imdbObject);
                        }
                        return objectMap;
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                }
            } catch (IOException exception) {
                tries++;
                log.warn(exception.getMessage());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        } while (tries < 5);
        return null;
    }

    public Map<String, ImdbImportRating> getRatings() {
        if (ratings == null)
            loadRatings();
        return ratings;
    }

    public Map<String, ImdbImportMovie> getTitles() {
        if (titles == null)
            loadTitles();
        return titles;
    }

    public Vote getVoteForMovie(Movie movie) {

        Vote vote = new Vote();
        ImdbImportRating rating = ratings == null ? null : ratings.getOrDefault(movie.imdb_id, null);

        if (rating != null) {
            vote.setAverage(rating.getAverageRating());
            vote.setVotes(rating.getNumVotes().intValue());
        } else {
            vote.setAverage(movie.vote_average);
            vote.setVotes(movie.vote_count);
        }
        return vote;
    }
}
