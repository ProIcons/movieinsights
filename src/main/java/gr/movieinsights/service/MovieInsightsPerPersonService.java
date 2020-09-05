package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.repository.MovieInsightsPerPersonRepository;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonBasicDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.person.MovieInsightsPerPersonBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.person.MovieInsightsPerPersonMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import io.vavr.Tuple2;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MovieInsightsPerPerson}.
 */
@Service
@Transactional
public class MovieInsightsPerPersonService
    extends BaseMovieInsightsService<MovieInsightsPerPerson, MovieInsightsPerPersonDTO, MovieInsightsPerPersonBasicDTO, MovieInsightsPerPersonRepository, MovieInsightsPerPersonMapper, MovieInsightsPerPersonBasicMapper> {

    private final MovieInsightsPerPersonBasicMapper basicMovieInsightsPerPersonMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsPerPersonService(MovieInsightsPerPersonRepository movieInsightsPerPersonRepository, MovieInsightsPerPersonMapper movieInsightsPerPersonMapper, MovieInsightsPerPersonBasicMapper basicMovieInsightsPerPersonMapper, MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsPerPersonRepository, movieInsightsPerPersonMapper);
        this.basicMovieInsightsPerPersonMapper = basicMovieInsightsPerPersonMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    protected List<Integer> getYears(Tuple2<Long,CreditRole> idRolePair) {
        List<Integer> years;
        synchronized (yearMap) {
            if (!yearMap.containsKey(idRolePair)) {
                yearMap.put(idRolePair, (years = repository.getYears(idRolePair._1,idRolePair._2)));
            } else {
                years = yearMap.get(idRolePair);
            }
        }
        return years;
    }


    @Override
    public MovieInsightsPerPersonBasicMapper getBasicMapper() {
        return basicMovieInsightsPerPersonMapper;
    }

    /**
     * Get one MovieInsightsPerPerson by person and role.
     *
     * @param id
     *     the id of the person of the entity.
     * @param role
     *     the role of the person of this movieinsight calculation
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerPersonDTO> findByPersonId(Long id, CreditRole role) {
        log.debug("Request to get MovieInsightsPerPerson By PersonId : {}", id);
        return repository.findByPerson_IdAndAs(id, role).map(getMapper()::toDto);
    }

    /**
     * Get one MovieInsightsPerPerson by person and role.
     *
     * @param id
     *     the id of the person of the entity.
     * @param role
     *     the role of the person of this movieinsight calculation
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerPersonBasicDTO> findByPersonIdBasic(Long id, CreditRole role) {
        log.debug("Request to get MovieInsightsPerPerson : {}", id);
        Optional<MovieInsightsPerPersonBasicDTO> movieInsightsPerPersonBasicDTO = repository.findByPerson_IdAndAs(id, role).map(getBasicMapper()::toDto);
        movieInsightsPerPersonBasicDTO.ifPresent(m->m.setYears(getYears(new Tuple2<>(id,role))));
        return movieInsightsPerPersonBasicDTO;
    }

    /**
     * Get all MovieInsightsPerPerson by person.
     *
     * @param id
     *     the id of the person of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerPersonDTO> findByPersonId(Long id) {
        log.debug("Request to get MovieInsightsPerPerson By PersonId : {}", id);
        return repository.findByPerson_Id(id).stream().map(getMapper()::toDto).collect(Collectors.toList());
    }

    /**
     * Get aall MovieInsightsPerPerson by person.
     *
     * @param id
     *     the id of the person of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<MovieInsightsPerPersonBasicDTO> findByPersonIdBasic(Long id) {
        log.debug("Request to get MovieInsightsPerPerson : {}", id);
        return repository.findByPerson_Id(id).stream().map(getBasicMapper()::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long personId, int year) {
        return movieInsightsPerYearService.findByPerson(personId, year);
    }

}
