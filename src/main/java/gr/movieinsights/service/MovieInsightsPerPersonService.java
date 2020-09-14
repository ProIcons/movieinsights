package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.Person;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.repository.MovieInsightsPerPersonRepository;
import gr.movieinsights.service.dto.movieinsights.MovieInsightsDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonBasicDTO;
import gr.movieinsights.service.dto.movieinsights.person.MovieInsightsPerPersonDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.dto.person.BasicPersonDTO;
import gr.movieinsights.service.dto.person.PersonDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.person.MovieInsightsPerPersonBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.person.MovieInsightsPerPersonMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import gr.movieinsights.service.mapper.person.PersonMapper;
import gr.movieinsights.service.mapper.util.MappingUtils;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import io.vavr.Tuple2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link MovieInsightsPerPerson}.
 */
@Service
@Transactional
public class MovieInsightsPerPersonService
    extends BaseMovieInsightsService<MovieInsightsPerPerson, MovieInsightsPerPersonDTO, MovieInsightsPerPersonBasicDTO, MovieInsightsPerPersonRepository, MovieInsightsPerPersonMapper, MovieInsightsPerPersonBasicMapper> {

    private final MovieInsightsPerPersonBasicMapper basicMovieInsightsPerPersonMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;
    private final PersonService personService;
    private final MovieInsightsMapper movieInsightsMapper;

    public MovieInsightsPerPersonService(MovieInsightsPerPersonRepository movieInsightsPerPersonRepository, MovieInsightsPerPersonMapper movieInsightsPerPersonMapper, MovieInsightsPerPersonBasicMapper basicMovieInsightsPerPersonMapper, MovieInsightsPerYearService movieInsightsPerYearService, PersonService personService, MovieInsightsMapper movieInsightsMapper) {
        super(movieInsightsPerPersonRepository, movieInsightsPerPersonMapper);
        this.basicMovieInsightsPerPersonMapper = basicMovieInsightsPerPersonMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
        this.personService = personService;
        this.movieInsightsMapper = movieInsightsMapper;
    }

    protected List<Integer> getYears(Tuple2<Long, CreditRole> idRolePair) {
        List<Integer> years;
        synchronized (yearMap) {
            if (!yearMap.containsKey(idRolePair)) {
                yearMap.put(idRolePair, (years = repository.getYears(idRolePair._1, idRolePair._2)));
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
        //movieInsightsPerPersonBasicDTO.ifPresent(m -> m.setYears(getYears(new Tuple2<>(id, role))));
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
    public Optional<MultiPersonView<PersonMapper, PersonDTO>> findByPersonId(Long id) {
        log.debug("Request to get MovieInsightsPerPerson By PersonId : {}", id);

        List<MovieInsightsPerPerson> movieInsightsPerPeople = repository.findByPerson_Id(id);
        if (movieInsightsPerPeople.size() == 0)
            return Optional.empty();

        return Optional.of(new MultiPersonView<>(personService.getMapper(), movieInsightsPerPeople));
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
    public Optional<MultiPersonView<BasicPersonMapper, BasicPersonDTO>> findByPersonIdBasic(Long id) {
        log.debug("Request to get MovieInsightsPerPerson : {}", id);
        List<MovieInsightsPerPerson> movieInsightsPerPeople = repository.findByPerson_Id(id);
        if (movieInsightsPerPeople.size() == 0)
            return Optional.empty();

        return Optional.of(new MultiPersonView<>(personService.getBasicMapper(), movieInsightsPerPeople));
    }


    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long personId, int year) {
        throw new UnsupportedOperationException();
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(long personId, CreditRole role, int year) {
        return movieInsightsPerYearService.findByPerson(personId, role, year);
    }

    public Optional<MultiPersonView<BasicPersonMapper, BasicPersonDTO>> findByYearMulti(long personId, int year) {
        List<MovieInsightsPerPerson> movieInsightsPerPeople = repository.findByYear(personId, year);
        if (movieInsightsPerPeople.size() == 0)
            return Optional.empty();
        else {
            return Optional.of(new MultiPersonView<>(personService.getBasicMapper(), movieInsightsPerPeople, true));
        }
    }


    public class MultiPersonView<TPersonMapper extends EntityMapper<TPersonDTO, Person>, TPersonDTO> {
        private final Set<CreditRole> roles;
        private TPersonDTO person;
        private Integer year;
        private boolean isPerYear;
        private MovieInsightsDTO actor;
        private List<List<Object>> actorYearData;
        private MovieInsightsDTO producer;
        private List<List<Object>> producerYearData;
        private MovieInsightsDTO writer;
        private List<List<Object>> writerYearData;
        private MovieInsightsDTO director;
        private List<List<Object>> directorYearData;

        public MultiPersonView(TPersonMapper personMapper, List<MovieInsightsPerPerson> movieInsightsPerPeople) {
            this(personMapper, movieInsightsPerPeople, false);
        }

        public MultiPersonView(TPersonMapper personMapper, List<MovieInsightsPerPerson> movieInsightsPerPeople, boolean perYear) {
            if (movieInsightsPerPeople == null)
                throw new NullPointerException();
            actorYearData = new ArrayList<>();
            producerYearData = new ArrayList<>();
            writerYearData = new ArrayList<>();
            directorYearData = new ArrayList<>();
            roles = new HashSet<>();

            for (MovieInsightsPerPerson movieInsightsPerPerson : movieInsightsPerPeople) {
                person = personMapper.toDto(movieInsightsPerPerson.getPerson());
                if (perYear) {
                    isPerYear = true;
                    Optional<MovieInsightsPerYearDTO> movieInsightsPerYear = movieInsightsPerPerson.getMovieInsightsPerYears().stream().findFirst().map(movieInsightsPerYearService::mapToDto);
                    if (movieInsightsPerYear.isPresent()) {
                        year = movieInsightsPerYear.get().getEntity();
                        switch (movieInsightsPerPerson.getAs()) {
                            case ACTOR -> actor = movieInsightsPerYear.get().getMovieInsights();
                            case WRITER -> writer = movieInsightsPerYear.get().getMovieInsights();
                            case DIRECTOR -> director = movieInsightsPerYear.get().getMovieInsights();
                            case PRODUCER -> producer = movieInsightsPerYear.get().getMovieInsights();
                        }
                    }
                } else {
                    isPerYear = false;
                    year = null;
                    MovieInsightsDTO movieInsightsDTO = movieInsightsMapper.toDto(movieInsightsPerPerson.getMovieInsights());
                    List<List<Object>> yearData = MappingUtils.calculateMovieInsightsPerYearsToTotals(movieInsightsPerPerson);
                    switch (movieInsightsPerPerson.getAs()) {
                        case ACTOR -> {
                            actor = movieInsightsDTO;
                            actorYearData = yearData;
                        }
                        case WRITER -> {
                            writer = movieInsightsDTO;
                            writerYearData = yearData;
                        }
                        case DIRECTOR -> {
                            director = movieInsightsDTO;
                            directorYearData = yearData;
                        }
                        case PRODUCER -> {
                            producer = movieInsightsDTO;
                            producerYearData = yearData;
                        }
                    }
                }
                roles.add(movieInsightsPerPerson.getAs());
            }
        }

        public List<List<Object>> getActorYearData() {
            return actorYearData;
        }

        public List<List<Object>> getProducerYearData() {
            return producerYearData;
        }

        public List<List<Object>> getWriterYearData() {
            return writerYearData;
        }

        public List<List<Object>> getDirectorYearData() {
            return directorYearData;
        }

        public Integer getYear() {
            return year;
        }

        public boolean isPerYear() {
            return isPerYear;
        }

        public Set<CreditRole> getRoles() {
            return roles;
        }

        public TPersonDTO getPerson() {
            return person;
        }

        public MovieInsightsDTO getActor() {
            return actor;
        }

        public MovieInsightsDTO getProducer() {
            return producer;
        }

        public MovieInsightsDTO getWriter() {
            return writer;
        }

        public MovieInsightsDTO getDirector() {
            return director;
        }
    }
}
