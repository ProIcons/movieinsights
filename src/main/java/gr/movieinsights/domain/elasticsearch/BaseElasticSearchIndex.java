package gr.movieinsights.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.movieinsights.domain.IdentifiedNamedEntity;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import javax.validation.constraints.NotNull;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseElasticSearchIndex<T extends IdentifiedNamedEntity> {

    BaseElasticSearchIndex() {

    }
    public BaseElasticSearchIndex(T entity, Long movieCount, Long voteCount, Double totalRatings) {
        this.score = score;
        this.id = entity.getId();
        this.name = entity.getName();

        double voteCountAvg = (double)voteCount/movieCount;
        double voteAverage = totalRatings/movieCount;

        this.score = voteAverage * Math.log(1+voteCountAvg)/Math.log(2) * Math.log(1+movieCount)/Math.log(2);
    }

    private Long id;


    @NotNull
    @MultiField(mainField = @Field(type = Text, fielddata = true,analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_index"), otherFields = {@InnerField(suffix = "verbatim", type = Keyword)})
    private String name;

    private Double score;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
