package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.BannedEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BannedEntity} and its DTO {@link BannedEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BannedEntityMapper extends EntityMapper<BannedEntityDTO, BannedEntity> {



    default BannedEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        BannedEntity bannedEntity = new BannedEntity();
        bannedEntity.setId(id);
        return bannedEntity;
    }
}
