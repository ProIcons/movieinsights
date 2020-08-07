package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.ProductionCompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionCompany} and its DTO {@link ProductionCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductionCompanyMapper extends EntityMapper<ProductionCompanyDTO, ProductionCompany> {


    @Mapping(target = "banReasons", ignore = true)
    @Mapping(target = "removeBanReasons", ignore = true)
    @Mapping(target = "movies", ignore = true)
    @Mapping(target = "removeMovies", ignore = true)
    ProductionCompany toEntity(ProductionCompanyDTO productionCompanyDTO);

    default ProductionCompany fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setId(id);
        return productionCompany;
    }
}
