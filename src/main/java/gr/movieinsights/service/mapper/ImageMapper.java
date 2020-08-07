package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {VoteMapper.class, MovieMapper.class, PersonMapper.class, CreditMapper.class})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    @Mapping(source = "vote.id", target = "voteId")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "credit.id", target = "creditId")
    ImageDTO toDto(Image image);

    @Mapping(source = "voteId", target = "vote")
    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "creditId", target = "credit")
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
