package rs.ac.ni.pmf.rwa.tvseries.core.provider;

import org.springframework.data.domain.Page;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;

import java.util.List;
import java.util.Optional;

public interface TvSeriesProvider {
    Optional<TvSeries> getTvSeriesById(final Integer id);



    void saveTvSeries(final TvSeries tvSeries);

    void removeTvSeries(final Integer id);

    void updateTvSeries(TvSeries tvSeries);

    Page<TvSeries> getAllTvSeries(TvSeriesSearchOptions tvSeriesSearchOptions);
}
