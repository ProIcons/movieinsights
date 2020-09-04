package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.elasticsearch.ProductionCompany;
import gr.movieinsights.repository.MovieInsightsPerCompanyRepository;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyBasicDTO;
import gr.movieinsights.service.dto.movieinsights.company.MovieInsightsPerCompanyDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.company.MovieInsightsPerCompanyBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.company.MovieInsightsPerCompanyMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovieInsightsPerCompany}.
 */
@Service
@Transactional
public class MovieInsightsPerCompanyService
    extends BaseMovieInsightsService<MovieInsightsPerCompany, MovieInsightsPerCompanyDTO, MovieInsightsPerCompanyBasicDTO, MovieInsightsPerCompanyRepository, MovieInsightsPerCompanyMapper, MovieInsightsPerCompanyBasicMapper> {

    private final MovieInsightsPerCompanyBasicMapper basicMovieInsightsPerCompanyMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsPerCompanyService(MovieInsightsPerCompanyRepository movieInsightsPerCompanyRepository, MovieInsightsPerCompanyMapper movieInsightsPerCompanyMapper, MovieInsightsPerCompanyBasicMapper basicMovieInsightsPerCompanyMapper, MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsPerCompanyRepository, movieInsightsPerCompanyMapper);
        this.basicMovieInsightsPerCompanyMapper = basicMovieInsightsPerCompanyMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    @Override
    public MovieInsightsPerCompanyBasicMapper getBasicMapper() {
        return basicMovieInsightsPerCompanyMapper;
    }

    /**
     * Get one MovieInsightsPerCompany by company.
     *
     * @param id
     *     the id of the company of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCompanyDTO> findByCompanyId(Long id) {
        log.debug("Request to get MovieInsightsPerCompany By CompanyId : {}", id);
        return repository.findByCompany_Id(id).map(getMapper()::toDto);
    }

    /**
     * Get one MovieInsightsPerCompany by company.
     *
     * @param id
     *     the id of the company of the entity.
     *
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovieInsightsPerCompanyBasicDTO> findByCompanyIdBasic(Long id) {
        log.debug("Request to get MovieInsightsPerCompany by CompanyId : {}", id);
        return repository.findByCompany_Id(id).map(getBasicMapper()::toDto);
    }

    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long companyId, int year) {
        return movieInsightsPerYearService.findByCompany(companyId, year);
    }

}
