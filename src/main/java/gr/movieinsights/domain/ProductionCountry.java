package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductionCountry.
 */
@Entity
@Table(name = "production_country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "productioncountry")
public class ProductionCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "iso_31661", nullable = false, unique = true)
    private String iso31661;

    @ManyToMany(mappedBy = "countries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductionCountry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso31661() {
        return iso31661;
    }

    public ProductionCountry iso31661(String iso31661) {
        this.iso31661 = iso31661;
        return this;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public ProductionCountry movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public ProductionCountry addMovies(Movie movie) {
        this.movies.add(movie);
        movie.getCountries().add(this);
        return this;
    }

    public ProductionCountry removeMovies(Movie movie) {
        this.movies.remove(movie);
        movie.getCountries().remove(this);
        return this;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionCountry)) {
            return false;
        }
        return id != null && id.equals(((ProductionCountry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionCountry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", iso31661='" + getIso31661() + "'" +
            "}";
    }
}
