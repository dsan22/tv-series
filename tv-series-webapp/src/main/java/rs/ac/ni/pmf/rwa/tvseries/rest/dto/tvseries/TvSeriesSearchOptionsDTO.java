package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import rs.ac.ni.pmf.rwa.tvseries.shared.TvSeriesSortByField;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TvSeriesSearchOptionsDTO {

    @Schema(description = "Page to return")
    Integer page;

    @Schema(description = "Number of elements on one page")
    Integer pageSize;

    @Schema(description = "Substring to search in Tv Series name")
    String titleSearch;

    @Schema(description = "Maximum number of episodes that returned Tv Series would have")
    Integer maxNumberOfEpisodes;

    @Schema(description = "Minimum number of episodes that returned Tv Series would have")
    Integer minNumberOfEpisodes;

    @Schema(description = "Maximum rating  that returned Tv Series would have")
    Double maxRating;

    @Schema(description = "Minimum rating  that returned Tv Series would have")
    Double minRating;


    TvSeriesSortByField sortBy;


    Sort.Direction sortDirection;

}