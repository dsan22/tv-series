package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.TvSeriesSearchSpecification;

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



       log.info("Returned Tv Series with id [{}]",id);


        return Optional.ofNullable(TvSeriesEntityMapper.fromEntity(tvSeriesEntity));


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

    @Override
    public Page<TvSeries> getAllTvSeries(TvSeriesSearchOptions tvSeriesSearchOptions) {

        int page = 0;
        if(tvSeriesSearchOptions.getPage() != null && tvSeriesSearchOptions.getPage() > 0) {
            page = tvSeriesSearchOptions.getPage() - 1;
        }

        int pageSize = 10;
        if(tvSeriesSearchOptions.getPageSize() != null && tvSeriesSearchOptions.getPageSize() > 0) {
            pageSize = tvSeriesSearchOptions.getPageSize();
        }



        return tvSeriesDao.findAll(new TvSeriesSearchSpecification(tvSeriesSearchOptions),PageRequest.of(page,pageSize))
                .map(TvSeriesEntityMapper::fromEntity);


    }
}
