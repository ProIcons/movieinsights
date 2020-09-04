package gr.movieinsights.util;

import gr.movieinsights.domain.Vote;

public class CalculationUtils {
    public static double calculateVoteLogarithmicScore(Vote vote, boolean high, int base) {
        return getLogarithmicScore(vote.getAverage(), vote.getVotes(), high, base);
    }

    public static double calculateVoteLogarithmicScore(Vote vote, boolean high) {
        return calculateVoteLogarithmicScore(vote, high, 2);
    }

    public static double getLogarithmicScore(double value, double weight, boolean high, int base) {
        double weightLog = Math.log(weight) / Math.log(base);
        return high ?
            value * weightLog :
            value / weightLog;
    }
}

