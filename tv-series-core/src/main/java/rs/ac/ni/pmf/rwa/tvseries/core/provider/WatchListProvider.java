package rs.ac.ni.pmf.rwa.tvseries.core.provider;

import org.springframework.data.domain.Page;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;

import java.util.List;
import java.util.Optional;

public interface WatchListProvider {

    void addToWatchList(final String username, final WatchedTvSeries watchedTvSeries);

    Page<TvSeries> getTvSeriesByUsername(String username, WatchTvSeriesSearchOptions searchOptions);

    Optional<TvSeries> getTvSeriesOnWatchListById(String username, Integer tvSeriesId);

    void update(WatchedTvSeries fromDto, String username);

    void delete(String username, Integer tvSeriesId);


}
