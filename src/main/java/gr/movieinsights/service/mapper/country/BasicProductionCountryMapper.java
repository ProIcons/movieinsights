package gr.movieinsights.service.mapper.country;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.dto.country.BasicProductionCountryDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BasicProductionCountryMapper extends EntityMapper<BasicProductionCountryDTO, ProductionCountry> {
    @Override
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "movies",ignore = true)
    @Mapping(target = "removeMovies",ignore = true)
    ProductionCountry toEntity(BasicProductionCountryDTO dto);

    @Override
    BasicProductionCountryDTO toDto(ProductionCountry entity);
}
