package gr.movieinsights.service.mapper.credit;

import gr.movieinsights.domain.Credit;
import gr.movieinsights.service.dto.credit.BasicCreditDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class, BasicPersonMapper.class})
public interface BasicCreditMapper extends EntityMapper<BasicCreditDTO, Credit> {
    @Override
    @Mapping(target = "creditId",ignore = true)
    @Mapping(target = "personTmdbId",ignore = true)
    @Mapping(target = "movieTmdbId",ignore = true)
    Credit toEntity(BasicCreditDTO dto);

    @Override
    BasicCreditDTO toDto(Credit entity);
}
