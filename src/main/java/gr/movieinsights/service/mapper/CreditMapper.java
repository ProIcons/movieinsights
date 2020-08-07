package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.CreditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Credit} and its DTO {@link CreditDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class, PersonMapper.class})
public interface CreditMapper extends EntityMapper<CreditDTO, Credit> {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "person.id", target = "personId")
    CreditDTO toDto(Credit credit);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "personId", target = "person")
    Credit toEntity(CreditDTO creditDTO);

    default Credit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
}