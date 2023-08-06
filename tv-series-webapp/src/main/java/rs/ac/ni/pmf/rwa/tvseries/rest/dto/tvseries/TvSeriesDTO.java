package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesDTO  {


    @Schema(description = "Tv Series identifier", example = "1")

    Integer id;

    @Schema(description = "Tv Series name", example = "Game of Thrones")
    String name;
    @Schema(description = "Number of episodes Tv Series have", example = "10")
    Integer numberOfEpisodes;

    @Schema(description = "Average rating of Tv Series ", example = "8.4")
    Double averageRating;

}
