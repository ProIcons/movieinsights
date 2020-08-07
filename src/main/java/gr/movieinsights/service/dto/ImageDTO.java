package gr.movieinsights.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link gr.movieinsights.domain.Image} entity.
 */
public class ImageDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long tmdbId;

    @NotNull
    private Integer aspectRatio;

    @NotNull
    private String filePath;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    private String iso6391;


    private Long voteId;

    private Long movieId;

    private Long personId;

    private Long creditId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public Integer getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Integer aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDTO)) {
            return false;
        }

        return id != null && id.equals(((ImageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", tmdbId=" + getTmdbId() +
            ", aspectRatio=" + getAspectRatio() +
            ", filePath='" + getFilePath() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", iso6391='" + getIso6391() + "'" +
            ", voteId=" + getVoteId() +
            ", movieId=" + getMovieId() +
            ", personId=" + getPersonId() +
            ", creditId=" + getCreditId() +
            "}";
    }
}
