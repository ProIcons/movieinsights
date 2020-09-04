package gr.movieinsights.service.mapper.company;


import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.service.dto.company.ProductionCompanyDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProductionCompany} and its DTO {@link ProductionCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class})
public interface ProductionCompanyMapper extends EntityMapper<ProductionCompanyDTO, ProductionCompany> {
    @Mapping(target = "removeMovies",ignore = true)
    ProductionCompany toEntity(ProductionCompanyDTO productionCompanyDTO);

    @Override
    ProductionCompanyDTO toDto(ProductionCompany entity);

    default ProductionCompany fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setId(id);
        return productionCompany;
    }
}
