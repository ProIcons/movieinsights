package gr.movieinsights.service.util.wrappers.movieinsights;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.service.util.MovieInsightsProcessor;
import gr.movieinsights.service.util.wrappers.movieinsights.dependent.GenreWrapper;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("000000")
public class MovieInsightsGenreWrapper extends MovieInsightsWrapper<MovieInsightsPerGenre, GenreWrapper, Genre> {
    protected MovieInsightsGenreWrapper() {
    }

    public MovieInsightsGenreWrapper(MovieInsightsProcessor processor, GenreWrapper source) {
        super(processor, source);
    }

    @Override
    protected MovieInsightsPerGenre getContainer() {
        MovieInsightsPerGenre movieInsightsPerGenre = new MovieInsightsPerGenre();
        movieInsightsPerGenre.setGenre(getSource().object);
        return movieInsightsPerGenre;
    }
}
