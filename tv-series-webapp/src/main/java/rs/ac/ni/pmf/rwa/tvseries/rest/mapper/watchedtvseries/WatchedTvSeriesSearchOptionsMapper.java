package rs.ac.ni.pmf.rwa.tvseries.rest.mapper.watchedtvseries;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries.WatchedTvSeriesSearchOptionsDTO;

@Component
public class WatchedTvSeriesSearchOptionsMapper {

    public WatchTvSeriesSearchOptions fromDto (WatchedTvSeriesSearchOptionsDTO dto)
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
