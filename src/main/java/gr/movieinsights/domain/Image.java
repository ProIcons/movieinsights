package gr.movieinsights.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "image")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @NotNull
    @Column(name = "aspect_ratio", nullable = false)
    private Integer aspectRatio;

    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "iso_6391")
    private String iso6391;

    @OneToOne
    @JoinColumn(unique = true)
    private Vote vote;

    @ManyToOne
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private Credit credit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public Image tmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
        return this;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public Integer getAspectRatio() {
        return aspectRatio;
    }

    public Image aspectRatio(Integer aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public void setAspectRatio(Integer aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public Image filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getHeight() {
        return height;
    }

    public Image height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Image width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getIso6391() {
        return iso6391;
    }

    public Image iso6391(String iso6391) {
        this.iso6391 = iso6391;
        return this;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public Vote getVote() {
        return vote;
    }

    public Image vote(Vote vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Movie getMovie() {
        return movie;
    }

    public Image movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public Image person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Credit getCredit() {
        return credit;
    }

    public Image credit(Credit credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", aspectRatio=" + getAspectRatio() +
            ", filePath='" + getFilePath() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", iso6391='" + getIso6391() + "'" +
            "}";
    }
}
