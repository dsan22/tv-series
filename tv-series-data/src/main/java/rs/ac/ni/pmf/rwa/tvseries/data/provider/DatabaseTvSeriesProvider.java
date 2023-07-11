package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DatabaseTvSeriesProvider implements TvSeriesProvider {

    private final TvSeriesDao tvSeriesDao;
    @Override
    public Optional<TvSeries> getTvSeriesById(Integer id) {
        Optional<TvSeriesEntity> optionalTvSeriesEntity=tvSeriesDao.findById(id);

        if(optionalTvSeriesEntity.isEmpty()){
            log.info("Tv Series with id[{}] is not founded",id);
            return Optional.empty();
        }
        TvSeriesEntity tvSeriesEntity=optionalTvSeriesEntity.get();

       Double averageRating= tvSeriesEntity.getUsersWatched().stream()
                .mapToInt( WatchListEntity::getRating)
                .average()
               .orElse(0.0d);

       log.info("Returned Tv Series with id [{}]",id);
        return Optional.ofNullable(TvSeriesEntityMapper.fromEntityWithRating(tvSeriesEntity,averageRating));


    }

    @Override
    public List<TvSeries> getAllTvSeries() {
        log.info("Returned list of all Tv Series");
        return tvSeriesDao.findAll().stream()
                .map((entity)->{
                            Double averageRating= entity.getUsersWatched().stream()
                                    .mapToInt( WatchListEntity::getRating)
                                    .average()
                                    .orElse(0.0d);
                            return TvSeriesEntityMapper.fromEntityWithRating(entity,averageRating);
                        }

                       )
                .collect(Collectors.toList());

    }

    @Override
    public void saveTvSeries(TvSeries tvSeries) {

        log.info("Saved Tv Series");
        tvSeriesDao.save(TvSeriesEntityMapper.toEntity(tvSeries));
    }

    @Override
    public void removeTvSeries(Integer id) {

        log.info("Deleted Tv Series with id[{}]",id);
        tvSeriesDao.deleteById(id);
    }

    @Override
    public void updateTvSeries(TvSeries tvSeries) {
        log.info("Updated Tv Series");
        tvSeriesDao.save(TvSeriesEntityMapper.toEntity(tvSeries)
        );
    }
}
