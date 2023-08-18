package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSaveDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSearchOptionsDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesWatchedDTO;

@Component
public class TvSeriesMapper {
    public TvSeriesDTO toDto(final TvSeries tvSeries)
    {
        return TvSeriesDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .averageRating(tvSeries.getAverageRating())
                .build();
    }




    public TvSeries fromDtoSave(final TvSeriesSaveDTO dto)
    {
        return TvSeries.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfEpisodes(dto.getNumberOfEpisodes())
                .build();
    }


    public TvSeriesWatchedDTO toDtoWatched(final TvSeries tvSeries)
    {
        return TvSeriesWatchedDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .usersRating(tvSeries.getUsersRating())
                .episodesWatched(tvSeries.getEpisodesWatched())
                .build();
    }



    public TvSeriesSearchOptions fromDtoSearchOptions (TvSeriesSearchOptionsDTO dto)
    {
        return TvSeriesSearchOptions.builder()
                .page(dto.getPage())
                .pageSize(dto.getPageSize())
                .maxNumberOfEpisodes(dto.getMaxNumberOfEpisodes())
                .minNumberOfEpisodes(dto.getMinNumberOfEpisodes())
                .titleSearch(dto.getTitleSearch())
                .maxRating(dto.getMaxRating())
                .minRating(dto.getMinRating())
                .sortBy(dto.getSortBy())
                .sortDirection(dto.getSortDirection())

                .build();
    }



}
