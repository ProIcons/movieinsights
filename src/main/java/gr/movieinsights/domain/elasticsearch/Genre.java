package gr.movieinsights.domain.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "genre")
@Setting(settingPath = "/config/elasticsearch/default.analyzer.json")
public class Genre extends BaseElasticSearchIndex<gr.movieinsights.domain.Genre> {
    Genre() {

    }
    public Genre(gr.movieinsights.domain.Genre entity, Long movieCount, Long voteCount, Double totalRatings) {
        super(entity, movieCount, voteCount, totalRatings);
    }

}
