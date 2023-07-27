package rs.ac.ni.pmf.rwa.tvseries.core.model;

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
public class TvSeriesSearchOptions {

    Integer page;
    Integer pageSize;
    String titleSearch;

    Integer maxNumberOfEpisodes;
    Integer minNumberOfEpisodes;

    Double maxRating;

    Double minRating;

    TvSeriesSortByField sortBy;

    Sort.Direction sortDirection;







}