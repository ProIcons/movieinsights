package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.repository.MovieInsightsGeneralRepository;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralBasicDTO;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralMapper;
import gr.movieinsights.service.util.BaseService;
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
    extends BaseService<MovieInsightsGeneral, MovieInsightsGeneralDTO, MovieInsightsGeneralRepository, MovieInsightsGeneralMapper>
    implements IBasicDataProviderService<MovieInsightsGeneral, MovieInsightsGeneralDTO, MovieInsightsGeneralBasicDTO, MovieInsightsGeneralRepository, MovieInsightsGeneralMapper, MovieInsightsGeneralBasicMapper> {

    private final MovieInsightsGeneralBasicMapper movieInsightsGeneralBasicMapper;

    public MovieInsightsGeneralService(MovieInsightsGeneralRepository movieInsightsGeneralRepository, MovieInsightsGeneralMapper movieInsightsGeneralMapper, MovieInsightsGeneralBasicMapper movieInsightsGeneralBasicMapper) {
        super(movieInsightsGeneralRepository, movieInsightsGeneralMapper);
        this.movieInsightsGeneralBasicMapper = movieInsightsGeneralBasicMapper;
    }

    public Optional<MovieInsightsGeneralDTO> get() {
        return repository.findFirst().map(mapper::toDto);
    }

    public Optional<MovieInsightsGeneralBasicDTO> getBasic() {
        return repository.findFirst().map(movieInsightsGeneralBasicMapper::toDto);
    }

    public Optional<MovieInsightsPerYearDTO> getByYear(int year) {
        return null;
    }

    @Override
    public MovieInsightsGeneralBasicMapper getBasicMapper() {
        return movieInsightsGeneralBasicMapper;
    }
}
