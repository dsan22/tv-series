package rs.ac.ni.pmf.rwa.tvseries.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TvSeries {
    Integer id;
    String name;
    Integer numberOfEpisodes;
    Double averageRating;
    Integer usersRating;
    Integer episodesWatched;
}
