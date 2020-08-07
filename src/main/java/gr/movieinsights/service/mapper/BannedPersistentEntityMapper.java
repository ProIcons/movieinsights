package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.BannedPersistentEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BannedPersistentEntity} and its DTO {@link BannedPersistentEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class, PersonMapper.class, ProductionCompanyMapper.class})
public interface BannedPersistentEntityMapper extends EntityMapper<BannedPersistentEntityDTO, BannedPersistentEntity> {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "productionCompany.id", target = "productionCompanyId")
    BannedPersistentEntityDTO toDto(BannedPersistentEntity bannedPersistentEntity);

    @Mapping(target = "bannedEntity", ignore = true)
    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "productionCompanyId", target = "productionCompany")
    BannedPersistentEntity toEntity(BannedPersistentEntityDTO bannedPersistentEntityDTO);

    default BannedPersistentEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        BannedPersistentEntity bannedPersistentEntity = new BannedPersistentEntity();
        bannedPersistentEntity.setId(id);
        return bannedPersistentEntity;
    }
}
