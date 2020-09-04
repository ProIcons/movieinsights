package gr.movieinsights.service.mapper.credit;

import gr.movieinsights.domain.Credit;
import gr.movieinsights.service.dto.credit.BasicCreditDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BasicPersonMapper.class})
public interface DependentByMovieCreditMapper extends EntityMapper<BasicCreditDTO, Credit> {

    BasicCreditDTO toDto(Credit credit);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "creditId",ignore = true)
    @Mapping(target = "personTmdbId",ignore = true)
    @Mapping(target = "movieTmdbId",ignore = true)
    Credit toEntity(BasicCreditDTO creditDTO);

    default Credit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
}
