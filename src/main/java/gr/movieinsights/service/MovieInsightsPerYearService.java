package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.repository.MovieInsightsPerYearRepository;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.year.MovieInsightsPerYearMapper;
import gr.movieinsights.service.util.BaseService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovieInsightsPerYear}.
 */
@Service
@Transactional
public class MovieInsightsPerYearService
    extends BaseService<MovieInsightsPerYear, MovieInsightsPerYearDTO, MovieInsightsPerYearRepository, MovieInsightsPerYearMapper>
    implements IBasicDataProviderService<MovieInsightsPerYear, MovieInsightsPerYearDTO, MovieInsightsPerYearDTO, MovieInsightsPerYearRepository, MovieInsightsPerYearMapper, MovieInsightsPerYearMapper> {

    private final Logger log = LoggerFactory.getLogger(MovieInsightsPerYearService.class);

    private final MovieInsightsPerYearMapper MovieInsightsPerYearMapper;

    public MovieInsightsPerYearService(MovieInsightsPerYearRepository movieInsightsPerYearRepository, MovieInsightsPerYearMapper movieInsightsPerYearMapper, MovieInsightsPerYearMapper MovieInsightsPerYearMapper) {
        super(movieInsightsPerYearRepository, movieInsightsPerYearMapper);
        this.MovieInsightsPerYearMapper = MovieInsightsPerYearMapper;
    }

    @Override
    public MovieInsightsPerYearMapper getBasicMapper() {
        return MovieInsightsPerYearMapper;
    }


    /**
     * Get one movieInsightsPerYear by year.
     *
     * @param year
     *     the year of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerYearDTO> findByYear(int year) {
        log.debug("Request to get MovieInsightsPerYear : {}", year);
        return repository.findByYear(year).map(mapper::toDto);
    }

    /**
     * Get one movieInsightsPerYear by year.
     *
     * @param year
     *     the year of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerYearDTO> findByYearBasic(int year) {
        log.debug("Request to get MovieInsightsPerYear : {}", year);
        return repository.findByYear(year).map(MovieInsightsPerYearMapper::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByCompany(long companyId, int year) {
        return getRepository().findByCompany(companyId, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByCountry(String countryIso, int year) {
        return getRepository().findByCountry(countryIso, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByCountryId(long id, int year) {
        return getRepository().findByCountryId(id, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByGenre(long genreId, int year) {
        return getRepository().findByGenre(genreId, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByGenre(String name, int year) {
        return getRepository().findByGenre(name, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findByPerson(long personId, int year) {
        return getRepository().findByPerson(personId, year).map(getMapper()::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> findGeneral(int year) {
        return getRepository().findGeneral(year).map(getMapper()::toDto);
    }
}
