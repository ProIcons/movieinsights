package gr.movieinsights.service.mapper.person;


import gr.movieinsights.domain.Person;
import gr.movieinsights.service.dto.person.PersonDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.credit.DependentByPersonCreditMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {DependentByPersonCreditMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "removeCredits",ignore = true)
    @Mapping(target = "credits",ignore = true)
    Person toEntity(PersonDTO personDTO);

    PersonDTO toDto(Person person);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
