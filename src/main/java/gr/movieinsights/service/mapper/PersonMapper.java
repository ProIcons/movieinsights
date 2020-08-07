package gr.movieinsights.service.mapper;


import gr.movieinsights.domain.*;
import gr.movieinsights.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {


    @Mapping(target = "credits", ignore = true)
    @Mapping(target = "removeCredits", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(target = "banReasons", ignore = true)
    @Mapping(target = "removeBanReasons", ignore = true)
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
