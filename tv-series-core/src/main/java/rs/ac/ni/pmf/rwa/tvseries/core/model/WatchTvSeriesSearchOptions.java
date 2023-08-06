package rs.ac.ni.pmf.rwa.tvseries.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import rs.ac.ni.pmf.rwa.tvseries.shared.WatchedTvSeriesSortByField;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchTvSeriesSearchOptions {


    Integer page;

    Integer pageSize;

    @Builder.Default
    boolean showUnfinishedTvSeries=false;

    @Builder.Default
    boolean showFinishedTvSeries=false;

    String titleSearch;


    WatchedTvSeriesSortByField sortBy;


    Sort.Direction sortDirection;
}
