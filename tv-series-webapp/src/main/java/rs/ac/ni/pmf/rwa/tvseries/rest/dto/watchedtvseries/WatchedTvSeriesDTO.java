package rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.rwa.tvseries.shared.TvSeriesSortByField;

@Value
@Builder
public class WatchedTvSeriesDTO {

    @Schema(description = "Identifier of watched Tv Series",example = "1",defaultValue = "1")
    Integer tvSeriesId;

    @Schema(description = "Users rating for watched Tv Series",example = "9",defaultValue = "9")
    Integer rating;

    @Schema(description = "Number of episodes that user watched",example = "1",defaultValue = "1")
    Integer episodesWatched;
}
