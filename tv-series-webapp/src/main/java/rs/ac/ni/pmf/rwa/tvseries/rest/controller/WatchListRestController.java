package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.service.WatchListService;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesWatchedDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries.WatchedTvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.watchedtvseries.WatchedTvSeriesSearchOptionsDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.tvseries.TvSeriesMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.user.UserMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.watchedtvseries.WatchedTvSeriesMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.watchedtvseries.WatchedTvSeriesSearchOptionsMapper;

@SecurityRequirement(name = "default")
@RestController
@RequiredArgsConstructor
public class WatchListRestController {
    private final WatchListService watchListService;
    private final TvSeriesMapper tvSeriesMapper;

    private  final WatchedTvSeriesMapper watchedTvSeriesMapper;

    private  final WatchedTvSeriesSearchOptionsMapper watchedTvSeriesSearchOptionsMapper;

    @PreAuthorize("#username == authentication.name")
    @PostMapping("/{username}/watch-list")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToWatchList(
            @PathVariable(name = "username")  String username,
            @RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO
            )
    {

        watchListService.addToWatchList(username,watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO));
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('ADMIN')")
    @GetMapping("/{username}/watch-list/")
    @ResponseStatus(HttpStatus.OK)
    public Page<TvSeriesWatchedDTO> getWatchListSearch(
            @PathVariable(name = "username")  String username,
            @ParameterObject WatchedTvSeriesSearchOptionsDTO searchOptions
    )
    {
        return watchListService.getTvSeriesByUsername(username,watchedTvSeriesSearchOptionsMapper.fromDto(searchOptions)).map(tvSeriesMapper::toDtoWatched);
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @GetMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.OK)
    public TvSeriesWatchedDTO getTvSeriesOnWatchList(
            @PathVariable(name = "username")  String username,
             @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        return tvSeriesMapper.toDtoWatched(watchListService.getTvSeriesOnWatchListById(username,tvSeriesId)) ;
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @PutMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWatchList(@RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO,
                           @PathVariable(name = "username")  String username,
                           @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        watchListService.update(watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO), username,tvSeriesId);
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @DeleteMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatchList(
            @PathVariable(name = "username")  String username,
            @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        watchListService.delete(username,tvSeriesId);
    }


}
