package gr.movieinsights.service.util;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;

public class QueryConfiguration {

    public static QueryConfiguration CreateDefault() {
        return new QueryConfiguration();
    }

    QueryConfiguration() {
        functionBoostMode = CombineFunction.AVG;
        functionScript = "_score * (Math.log(1+doc['score'].value)/Math.log(2))";
        functionWeight = 0.5f;
        minimumScore = 8.5f;
        indexFuzziness = Fuzziness.AUTO;
        indexBoost = 0.3f;
        searchFuzziness = Fuzziness.ZERO;
        searchBoost = 1f;
        searchOperator = Operator.AND;
        indexOperator = Operator.AND;
        boost = 1;
    }

    public QueryConfiguration(String functionScript, float boost, float functionWeight, CombineFunction functionBoostMode, float minimumScore, Fuzziness indexFuzziness, float indexBoost, Fuzziness searchFuzziness, float searchBoost, Operator indexOperator, Operator searchOperator) {
        this.functionScript = functionScript;
        this.functionWeight = functionWeight;
        this.functionBoostMode = functionBoostMode;
        this.minimumScore = minimumScore;
        this.indexFuzziness = indexFuzziness;
        this.indexBoost = indexBoost;
        this.searchFuzziness = searchFuzziness;
        this.searchBoost = searchBoost;
        this.searchOperator = searchOperator;
        this.indexOperator = indexOperator;
        this.boost = boost;
    }

    private String functionScript;
    private float functionWeight;
    private CombineFunction functionBoostMode;
    private float minimumScore;
    private Fuzziness indexFuzziness;
    private float indexBoost;
    private Operator indexOperator;
    private Fuzziness searchFuzziness;
    private float searchBoost;
    private Operator searchOperator;
    private float boost;


    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public Operator getSearchOperator() {
        return searchOperator;
    }

    public void setSearchOperator(Operator searchOperator) {
        this.searchOperator = searchOperator;
    }

    public Operator getIndexOperator() {
        return indexOperator;
    }

    public void setIndexOperator(Operator indexOperator) {
        this.indexOperator = indexOperator;
    }

    public String getFunctionScript() {
        return functionScript;
    }

    public void setFunctionScript(String functionScript) {
        this.functionScript = functionScript;
    }

    public float getFunctionWeight() {
        return functionWeight;
    }

    public void setFunctionWeight(float functionWeight) {
        this.functionWeight = functionWeight;
    }

    public CombineFunction getFunctionBoostMode() {
        return functionBoostMode;
    }

    public void setFunctionBoostMode(CombineFunction functionBoostMode) {
        this.functionBoostMode = functionBoostMode;
    }

    public float getMinimumScore() {
        return minimumScore;
    }

    public void setMinimumScore(float minimumScore) {
        this.minimumScore = minimumScore;
    }

    public Fuzziness getIndexFuzziness() {
        return indexFuzziness;
    }

    public void setIndexFuzziness(Fuzziness indexFuzziness) {
        this.indexFuzziness = indexFuzziness;
    }

    public float getIndexBoost() {
        return indexBoost;
    }

    public void setIndexBoost(float indexBoost) {
        this.indexBoost = indexBoost;
    }

    public Fuzziness getSearchFuzziness() {
        return searchFuzziness;
    }

    public void setSearchFuzziness(Fuzziness searchFuzziness) {
        this.searchFuzziness = searchFuzziness;
    }

    public float getSearchBoost() {
        return searchBoost;
    }

    public void setSearchBoost(float searchBoost) {
        this.searchBoost = searchBoost;
    }
}
