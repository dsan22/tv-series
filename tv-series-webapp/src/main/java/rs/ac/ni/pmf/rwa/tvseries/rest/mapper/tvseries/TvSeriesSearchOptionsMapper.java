package rs.ac.ni.pmf.rwa.tvseries.rest.mapper.tvseries;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSearchOptionsDTO;

@Component
public class TvSeriesSearchOptionsMapper {

    public TvSeriesSearchOptions fromDto (TvSeriesSearchOptionsDTO dto)
    {
        return TvSeriesSearchOptions.builder()
                .page(dto.getPage())
                .pageSize(dto.getPageSize())
                .maxNumberOfEpisodes(dto.getMaxNumberOfEpisodes())
                .minNumberOfEpisodes(dto.getMinNumberOfEpisodes())
                .titleSearch(dto.getTitleSearch())
                .maxRating(dto.getMaxRating())
                .minRating(dto.getMinRating())
                .sortBy(dto.getSortBy())
                .sortDirection(dto.getSortDirection())

                .build();
    }
}
