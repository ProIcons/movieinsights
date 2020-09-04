package gr.movieinsights.service.util;

import gr.movieinsights.service.util.wrappers.movieinsights.dependent.BaseWrapper;

public class Total<E> {
    private int totalEntities = 0;
    private int totalEntitiesPerMovie = 0;
    private int totalMoviesWithEntities = 0;
    private E mostPopularEntity = null;
    private int mostPopularEntityMovieCount = 0;
    private E leastPopularEntity = null;
    private int leastPopularEntityMovieCount = 0;

    public double getAverageCount() {
        return totalMoviesWithEntities > 0 ? (double) totalEntitiesPerMovie / totalMoviesWithEntities : 0;
    }

    public void increaseMoviesWithEntities() {
        totalMoviesWithEntities++;
    }

    public void submitMostPopular(BaseWrapper<E> wrapper) {
        this.mostPopularEntity = wrapper.object;
        this.mostPopularEntityMovieCount = wrapper.count;
    }

    public void submitLeastPopular(BaseWrapper<E> wrapper) {
        this.leastPopularEntity = wrapper.object;
        this.leastPopularEntityMovieCount = wrapper.count;
    }

    public void setTotalEntities(int totalEntities) {
        this.totalEntities = totalEntities;
    }

    public int getTotalEntities() {
        return totalEntities;
    }

    public int getTotalEntitiesPerMovie() {
        return totalEntitiesPerMovie;
    }

    public int getTotalMoviesWithEntities() {
        return totalMoviesWithEntities;
    }

    public E getMostPopularEntity() {
        return mostPopularEntity;
    }

    public int getMostPopularEntityMovieCount() {
        return mostPopularEntityMovieCount;
    }

    public E getLeastPopularEntity() {
        return leastPopularEntity;
    }

    public int getLeastPopularEntityMovieCount() {
        return leastPopularEntityMovieCount;
    }

    public void increaseEntitiesPerMovie() {
        totalEntitiesPerMovie++;
    }


    public void clear() {
        this.totalEntities = 0;
        this.leastPopularEntity = null;
        this.leastPopularEntityMovieCount = 0;
        this.mostPopularEntity = null;
        this.mostPopularEntityMovieCount = 0;
        this.totalEntitiesPerMovie = 0;
        this.totalMoviesWithEntities = 0;
    }
}
