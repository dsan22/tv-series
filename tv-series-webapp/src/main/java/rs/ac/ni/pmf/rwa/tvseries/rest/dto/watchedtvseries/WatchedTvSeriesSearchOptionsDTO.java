package rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries;


import io.swagger.v3.oas.annotations.media.Schema;
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
public class WatchedTvSeriesSearchOptionsDTO {

    @Schema(description = "Page to return")
    Integer page;

    @Schema(description = "Number of elements on one page")
    Integer pageSize;

    @Schema(description = "Show tv series that user is not finished watching ")
    boolean showUnfinishedTvSeries;
    @Schema(description = "Show tv series that user is  finished watching ")
    boolean showFinishedTvSeries;

    @Schema(description = "Substring that tv series title should have")
    String titleSearch;

    WatchedTvSeriesSortByField sortBy;

    Sort.Direction sortDirection;

}
