package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.repository.MovieInsightsGeneralRepository;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralBasicDTO;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import gr.movieinsights.service.util.IBasicDataProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovieInsightsGeneral}.
 */
@Service
@Transactional
public class MovieInsightsGeneralService
    extends BaseMovieInsightsService<MovieInsightsGeneral, MovieInsightsGeneralDTO, MovieInsightsGeneralBasicDTO, MovieInsightsGeneralRepository, MovieInsightsGeneralMapper, MovieInsightsGeneralBasicMapper> {

    private final MovieInsightsGeneralBasicMapper movieInsightsGeneralBasicMapper;

    private final MovieInsightsPerYearService movieInsightsPerYearService;

    public MovieInsightsGeneralService(MovieInsightsGeneralRepository movieInsightsGeneralRepository, MovieInsightsGeneralMapper movieInsightsGeneralMapper, MovieInsightsGeneralBasicMapper movieInsightsGeneralBasicMapper, MovieInsightsPerYearService movieInsightsPerYearService) {
        super(movieInsightsGeneralRepository, movieInsightsGeneralMapper);
        this.movieInsightsGeneralBasicMapper = movieInsightsGeneralBasicMapper;
        this.movieInsightsPerYearService = movieInsightsPerYearService;
    }

    public Optional<MovieInsightsGeneralDTO> get() {
        return repository.findFirst().map(mapper::toDto);
    }

    public Optional<MovieInsightsGeneralBasicDTO> getBasic() {
        return repository.findFirst().map(movieInsightsGeneralBasicMapper::toDto);
    }

    @Override
    public MovieInsightsGeneralBasicMapper getBasicMapper() {
        return movieInsightsGeneralBasicMapper;
    }

    public Optional<MovieInsightsPerYearDTO> findByYear(int year) {
        return movieInsightsPerYearService.findGeneral(year);
    }

    @Override
    public Optional<MovieInsightsPerYearDTO> findByYear(long id, int year) {
        throw new UnsupportedOperationException();
    }
}
