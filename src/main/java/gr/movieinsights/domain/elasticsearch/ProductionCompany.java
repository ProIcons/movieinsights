package gr.movieinsights.domain.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "company")
@Setting(settingPath = "/config/elasticsearch/default.analyzer.json")
public class ProductionCompany extends BaseElasticSearchIndex<gr.movieinsights.domain.ProductionCompany> {
    ProductionCompany() {

    }
    private String logoPath;

    public ProductionCompany(gr.movieinsights.domain.ProductionCompany entity, Long movieCount, Long voteCount, Double totalRatings) {
        super(entity, movieCount, voteCount, totalRatings);
        this.logoPath = entity.getLogoPath();
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
