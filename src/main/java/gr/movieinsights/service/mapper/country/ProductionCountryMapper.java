package gr.movieinsights.service.mapper.country;


import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.dto.country.ProductionCountryDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProductionCountry} and its DTO {@link ProductionCountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class})
public interface ProductionCountryMapper extends EntityMapper<ProductionCountryDTO, ProductionCountry> {
    @Mapping(target = "removeMovies", ignore = true)
    ProductionCountry toEntity(ProductionCountryDTO productionCountryDTO);

    @Override
    ProductionCountryDTO toDto(ProductionCountry entity);

    default ProductionCountry fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductionCountry productionCountry = new ProductionCountry();
        productionCountry.setId(id);
        return productionCountry;
    }
}
