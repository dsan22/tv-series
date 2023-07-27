package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.service.TvSeriesService;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSaveDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSearchOptionsDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.TvSeriesMapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@SecurityRequirement(name = "default")
@RestController
@RequiredArgsConstructor
public class TvSeriesRestController {
    private final TvSeriesService tvSeriesService;

    private final TvSeriesMapper tvSeriesMapper;






    @GetMapping("/tv-series")
    public Page<TvSeriesDTO> getAllTvSeries(
            @ParameterObject TvSeriesSearchOptionsDTO tvSeriesSearchOptions
            )
    {
        return tvSeriesService.getAllTvSeries(tvSeriesMapper.fromDtoSearchOptions(tvSeriesSearchOptions)).map(tvSeriesMapper::toDto);

    }


    @GetMapping("/tv-series/{id}")
    public TvSeriesDTO getTvSeriesById(@PathVariable(name = "id") final Integer id)
    {
        final TvSeries tvSeries = tvSeriesService.getTvSeriesById(id);
        return tvSeriesMapper.toDto(tvSeries);
    }

    @PostMapping("/tv-series")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTvSeries(@RequestBody final TvSeriesSaveDTO tvSeriesDTO)
    {

        tvSeriesService.createTvSeries(tvSeriesMapper.fromDtoSave(tvSeriesDTO));
    }

    @PutMapping("/tv-series/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTvSeries(@RequestBody final TvSeriesSaveDTO tvSeriesDTO,@PathVariable(value = "id") Integer id)
    {
        tvSeriesService.update(tvSeriesMapper.fromDtoSave(tvSeriesDTO), id);
    }
    @DeleteMapping("/tv-series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTvSeries(@PathVariable(value = "id") Integer id)
    {
        tvSeriesService.delete(id);
    }



//    @PostMapping("/tv-series/create-from-file")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createTvSeriesFromFile()
//    {
//        // code for scraping Tv Series
//        //File downloaded from https://github.com/WittmannF/imdb-tv-ratings/blob/master/data/top-seasons-full.csv
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Dusan\\Desktop\\Master\\Web\\tvSeries.csv"));
//            String mystring;
//
//            while ((mystring = reader.readLine()) != null)
//            {
//                String[] tvSeriesValues = mystring.split(",");
//                String name=tvSeriesValues[0];
//                Integer season=Integer.parseInt(tvSeriesValues[1]);
//                Integer numberOfEpisodes=Integer.parseInt(tvSeriesValues[2]);
//
//                if(season!=1){
//                    name+=" Season "+season;
//                }
//                TvSeries tvSeries=TvSeries.builder()
//                        .name(name)
//                        .numberOfEpisodes(numberOfEpisodes)
//                        .build();
//
//                tvSeriesService.createTvSeries(tvSeries);
//
//
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

}
