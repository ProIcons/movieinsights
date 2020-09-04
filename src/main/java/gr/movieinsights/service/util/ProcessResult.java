package gr.movieinsights.service.util;

import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.MovieInsightsPerGenre;
import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;

import java.util.List;

public class ProcessResult {
    public static ProcessResult of(List<MovieInsightsPerYear> movieInsightsPerYear, List<MovieInsightsPerPerson> movieInsightsPerPerson, List<MovieInsightsPerCompany> movieInsightsPerCompany, List<MovieInsightsPerCountry> movieInsightsPerCountry, List<MovieInsightsPerGenre> movieInsightsPerGenre, MovieInsightsGeneral movieInsightsGeneral, List<MovieInsights> movieInsights) {
        return new ProcessResult(movieInsightsPerYear, movieInsightsPerPerson,
            movieInsightsPerCompany, movieInsightsPerCountry, movieInsightsPerGenre, movieInsightsGeneral, movieInsights);
    }

    private final List<MovieInsightsPerYear> movieInsightsPerYear;
    private final List<MovieInsightsPerPerson> movieInsightsPerPerson;
    private final List<MovieInsightsPerCompany> movieInsightsPerCompany;
    private final List<MovieInsightsPerCountry> movieInsightsPerCountry;
    private final List<MovieInsightsPerGenre> movieInsightsPerGenre;
    private final MovieInsightsGeneral movieInsightsGeneral;
    private final List<MovieInsights> movieInsights;

    public ProcessResult(List<MovieInsightsPerYear> movieInsightsPerYear, List<MovieInsightsPerPerson> movieInsightsPerPerson, List<MovieInsightsPerCompany> movieInsightsPerCompany, List<MovieInsightsPerCountry> movieInsightsPerCountry, List<MovieInsightsPerGenre> movieInsightsPerGenre, MovieInsightsGeneral movieInsightsGeneral, List<MovieInsights> movieInsights) {
        this.movieInsightsPerYear = movieInsightsPerYear;
        this.movieInsightsPerPerson = movieInsightsPerPerson;
        this.movieInsightsPerCompany = movieInsightsPerCompany;
        this.movieInsightsPerCountry = movieInsightsPerCountry;
        this.movieInsightsPerGenre = movieInsightsPerGenre;
        this.movieInsightsGeneral = movieInsightsGeneral;
        this.movieInsights = movieInsights;
    }

    public List<MovieInsightsPerYear> getMovieInsightsPerYear() {
        return movieInsightsPerYear;
    }

    public List<MovieInsightsPerPerson> getMovieInsightsPerPerson() {
        return movieInsightsPerPerson;
    }

    public List<MovieInsightsPerCompany> getMovieInsightsPerCompany() {
        return movieInsightsPerCompany;
    }

    public List<MovieInsightsPerCountry> getMovieInsightsPerCountry() {
        return movieInsightsPerCountry;
    }

    public List<MovieInsightsPerGenre> getMovieInsightsPerGenre() {
        return movieInsightsPerGenre;
    }

    public MovieInsightsGeneral getMovieInsightsGeneral() {
        return movieInsightsGeneral;
    }

    public List<MovieInsights> getMovieInsights() {
        return movieInsights;
    }
}
