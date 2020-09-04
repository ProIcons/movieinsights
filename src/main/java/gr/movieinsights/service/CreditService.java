package gr.movieinsights.service;

import gr.movieinsights.domain.Credit;
import gr.movieinsights.repository.CreditRepository;
import gr.movieinsights.service.dto.credit.CreditDTO;
import gr.movieinsights.service.dto.movie.MovieDTO;
import gr.movieinsights.service.dto.person.PersonDTO;
import gr.movieinsights.service.mapper.credit.CreditMapper;
import gr.movieinsights.service.util.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Credit}.
 */
@Service
@Transactional
public class CreditService extends BaseService<Credit,CreditDTO,CreditRepository,CreditMapper> {

    private final PersonService personService;

    private final MovieService movieService;

    public CreditService(CreditRepository creditRepository, CreditMapper creditMapper, PersonService personService, MovieService movieService) {
        super(creditRepository,creditMapper);
        this.personService = personService;
        this.movieService = movieService;
    }

    /**
     * Save a credit by using tmdb ids.
     *
     * @param creditDTO the entity to save.
     * @return the persisted entity.
     */
    public void saveByTmdbIds(CreditDTO creditDTO) {
        Optional<PersonDTO> person = personService.findByTmdbId(creditDTO.getPersonTmdbId());
        Optional<MovieDTO> movie = movieService.findByTmdbId(creditDTO.getMovieTmdbId());
        if (!person.isPresent() || !movie.isPresent()) {
            throw new EntityNotFoundException("Person with TMDb ID: " + creditDTO.getPersonTmdbId() + " and/or Movie with TMDb ID: " + creditDTO.getMovieTmdbId() + " are not persistent in database.");
        }
        creditDTO.setPerson(person.get());
        creditDTO.setMovie(movie.get());
        saveDTO(creditDTO);
    }

}
