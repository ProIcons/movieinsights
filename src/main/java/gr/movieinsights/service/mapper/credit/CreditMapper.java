package gr.movieinsights.service.mapper.credit;


import gr.movieinsights.domain.Credit;
import gr.movieinsights.service.dto.credit.CreditDTO;
import gr.movieinsights.service.mapper.EntityMapper;
import gr.movieinsights.service.mapper.movie.BasicMovieMapper;
import gr.movieinsights.service.mapper.person.BasicPersonMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Credit} and its DTO {@link CreditDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasicMovieMapper.class, BasicPersonMapper.class})
public interface CreditMapper extends EntityMapper<CreditDTO, Credit> {

    CreditDTO toDto(Credit credit);

    Credit toEntity(CreditDTO creditDTO);

    default Credit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
}
