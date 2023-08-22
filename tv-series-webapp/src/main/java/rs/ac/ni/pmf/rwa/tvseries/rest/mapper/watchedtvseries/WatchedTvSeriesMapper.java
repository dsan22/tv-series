package rs.ac.ni.pmf.rwa.tvseries.rest.mapper.watchedtvseries;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries.WatchedTvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries.WatchedTvSeriesSearchOptionsDTO;

@Component
public class WatchedTvSeriesMapper {



    public WatchedTvSeries fromDto(final WatchedTvSeriesDTO dto)
    {
        return WatchedTvSeries.builder()
                .tvSeriesId(dto.getTvSeriesId())
                .rating(dto.getRating())
                .episodesWatched(dto.getEpisodesWatched())
                .build();
    }


}
