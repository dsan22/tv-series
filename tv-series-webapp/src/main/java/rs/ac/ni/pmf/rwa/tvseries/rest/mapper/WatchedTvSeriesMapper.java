package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

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

    public WatchTvSeriesSearchOptions fromDtoSearchOptions (WatchedTvSeriesSearchOptionsDTO dto)
    {
        return WatchTvSeriesSearchOptions.builder()
                .page(dto.getPage())
                .pageSize(dto.getPageSize())
                .showUnfinishedTvSeries(dto.isShowUnfinishedTvSeries())
                .showFinishedTvSeries(dto.isShowFinishedTvSeries())
                .titleSearch(dto.getTitleSearch())
                .sortBy(dto.getSortBy())
                .sortDirection(dto.getSortDirection())

                .build();
    }
}
