package gr.movieinsights.domain.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "country")
@Setting(settingPath = "/config/elasticsearch/default.analyzer.json")
public class ProductionCountry extends BaseElasticSearchIndex<gr.movieinsights.domain.ProductionCountry> {
    ProductionCountry() {

    }
    private String iso31661;

    public ProductionCountry(gr.movieinsights.domain.ProductionCountry entity, Long movieCount, Long voteCount, Double totalRatings) {
        super(entity, movieCount, voteCount, totalRatings);
        this.iso31661 = entity.getIso31661();
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }
}
