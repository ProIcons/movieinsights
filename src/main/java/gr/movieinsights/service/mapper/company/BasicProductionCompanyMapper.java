package gr.movieinsights.service.mapper.company;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.service.dto.company.BasicProductionCompanyDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BasicProductionCompanyMapper extends EntityMapper<BasicProductionCompanyDTO, ProductionCompany> {
    @Override
    @Mapping(target = "originCountry",ignore = true)
    @Mapping(target = "movies",ignore = true)
    @Mapping(target = "removeMovies",ignore = true)
    ProductionCompany toEntity(BasicProductionCompanyDTO dto);

    @Override
    BasicProductionCompanyDTO toDto(ProductionCompany entity);
}
