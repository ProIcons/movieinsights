package gr.movieinsights.service.mapper.credit;

import gr.movieinsights.domain.Credit;
import gr.movieinsights.service.dto.credit.CreditDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class})
public interface DependentByPersonCreditMapper extends EntityMapper<CreditDTO, Credit> {
    @Mapping(target = "person",ignore = true)
    CreditDTO toDto(Credit credit);

    @Mapping(target = "person",ignore = true)
    @Mapping(target = "creditId",ignore = true)
    @Mapping(target = "personTmdbId",ignore = true)
    @Mapping(target = "movieTmdbId",ignore = true)
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
