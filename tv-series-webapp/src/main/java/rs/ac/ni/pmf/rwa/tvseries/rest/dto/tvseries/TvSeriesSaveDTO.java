package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesSaveDTO {

    @Schema(description = "User identifier",nullable = true, example = "null",defaultValue = "null")
    Integer id;

    @Schema(description = "Tv Series name", example = "Game of Thrones",defaultValue  = "Game of Thrones")
    String name;
    @Schema(description = "Number of episodes Tv Series have", example = "10",defaultValue = "10")
    Integer numberOfEpisodes;
}
