package gr.movieinsights.domain.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "person")
@Setting(settingPath = "/config/elasticsearch/default.analyzer.json")
public class Person extends BaseElasticSearchIndex<gr.movieinsights.domain.Person> {
    Person() {

    }
    public Person(gr.movieinsights.domain.Person entity, Long movieCount, Long voteCount, Double totalRatings) {
        super(entity, movieCount, voteCount, totalRatings);
        this.popularity = entity.getPopularity();
        this.profilePath = entity.getProfilePath();
        this.setScore(this.getScore() * Math.log(1 + popularity) / Math.log(2));
    }

    private Double popularity;

    private String profilePath;

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
