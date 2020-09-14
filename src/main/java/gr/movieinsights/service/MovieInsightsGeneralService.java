package gr.movieinsights.service;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.repository.MovieInsightsGeneralRepository;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralBasicDTO;
import gr.movieinsights.service.dto.movieinsights.general.MovieInsightsGeneralDTO;
import gr.movieinsights.service.dto.movieinsights.year.MovieInsightsPerYearDTO;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralBasicMapper;
import gr.movieinsights.service.mapper.movieinsights.general.MovieInsightsGeneralMapper;
import gr.movieinsights.service.util.BaseMovieInsightsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private Long movieInsightsGeneralId = null;

    private Optional<MovieInsightsGeneral> getFirst() {
        Optional<MovieInsightsGeneral> movieInsightsGeneralOptional;
        if (movieInsightsGeneralId == null) {
            List<MovieInsightsGeneral> movieInsightsGeneralList = repository.findAllWithEagerRelationships();
            movieInsightsGeneralOptional = movieInsightsGeneralList.stream().findFirst();
            movieInsightsGeneralOptional.ifPresent(movieInsightsGeneral -> movieInsightsGeneralId = movieInsightsGeneral.getId());
        } else {
            movieInsightsGeneralOptional = repository.findById(movieInsightsGeneralId);
            if (movieInsightsGeneralOptional.isEmpty()) {
                List<MovieInsightsGeneral> movieInsightsGeneralList = repository.findAllWithEagerRelationships();
                movieInsightsGeneralOptional = movieInsightsGeneralList.stream().findFirst();
                movieInsightsGeneralOptional.ifPresent(movieInsightsGeneral -> movieInsightsGeneralId = movieInsightsGeneral.getId());
            }
        }
        return movieInsightsGeneralOptional;
    }

    public Optional<MovieInsightsGeneralDTO> get() {
        return getFirst().map(mapper::toDto);
    }

    public Optional<MovieInsightsGeneralBasicDTO> getBasic() {
        Optional<MovieInsightsGeneralBasicDTO> movieInsightsGeneralBasicDTO = getFirst().map(movieInsightsGeneralBasicMapper::toDto);
        //todo fix movieInsightsGeneralBasicDTO.ifPresent((m) -> m.setYearData(getYears(null)));
        return movieInsightsGeneralBasicDTO;
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

    @Override
    public void clear() {
        super.clear();
        movieInsightsGeneralId = null;
    }
}
