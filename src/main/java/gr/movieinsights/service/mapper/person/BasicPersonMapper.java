package gr.movieinsights.service.mapper.person;

import gr.movieinsights.domain.Person;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface BasicPersonMapper extends EntityMapper<BasicPersonDTO, Person> {
    @Override
    @Mapping(target = "popularity",ignore = true)
    @Mapping(target = "biography",ignore = true)
    @Mapping(target = "credits",ignore = true)
    @Mapping(target = "removeCredits",ignore = true)
    Person toEntity(BasicPersonDTO dto);

    @Override
    BasicPersonDTO toDto(Person entity);
}
