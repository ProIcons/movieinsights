package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.service.dto.ProductionCountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProductionCountry} and its DTO {@link ProductionCountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductionCountryMapper extends EntityMapper<ProductionCountryDTO, ProductionCountry> {


    @Mapping(target = "movies", ignore = true)
    @Mapping(target = "removeMovies", ignore = true)
    ProductionCountry toEntity(ProductionCountryDTO productionCountryDTO);

    default ProductionCountry fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductionCountry productionCountry = new ProductionCountry();
        productionCountry.setId(id);
        return productionCountry;
    }
}
