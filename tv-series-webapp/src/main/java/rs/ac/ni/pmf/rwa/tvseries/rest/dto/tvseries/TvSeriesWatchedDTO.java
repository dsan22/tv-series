package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesWatchedDTO {

    @Schema(description = "Tv Series identifier", example = "1")
    Integer id;

    @Schema(description = "Tv Series name", example = "Game of Thrones",defaultValue  = "Game of Thrones")
    String name;

    @Schema(description = "Number of episodes Tv Series have", example = "10",defaultValue = "10")
    Integer numberOfEpisodes;

    @Schema(description = "Rating that User gave to this Tv Series", example = "9",defaultValue = "9")
    Integer usersRating;

    @Schema(description = "Number of episodes that User have watched", example = "10",defaultValue = "10")
    Integer episodesWatched;
}
