package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.WatchListProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.WatchListDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.WatchedTvSeriesSearchSpecification;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;

import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
public class DatabaseWatchListProvider  implements WatchListProvider {

    final UserDao userDao;
    final TvSeriesDao tvSeriesDao;

    final WatchListDao watchListDao;




    @Override
    public void addToWatchList(String username, WatchedTvSeries watchedTvSeries) {

        Integer tvSeriesId=watchedTvSeries.getTvSeriesId();
        Integer rating= watchedTvSeries.getRating();
        Integer episodesWatched=watchedTvSeries.getEpisodesWatched();

        UserEntity user= userDao.findByUsername(username).orElseThrow(()-> new UnknownUserException(username));
        TvSeriesEntity tvSeries= tvSeriesDao.findById(tvSeriesId).orElseThrow(()->new UnknownTvSeriesException(tvSeriesId));
        user.getWatchedTvSeries().add(
                WatchListEntity.
                        builder()
                        .tvSeries(tvSeries)
                        .rating(rating)
                        .user(user)
                        .episodesWatched(episodesWatched)
                        .build()
        );
        log.info("Tv Series with id[{}] is added to users[{}] watch list ",tvSeriesId,username);
        userDao.save(user);
    }




    @Override
    public Page<TvSeries> getTvSeriesByUsername(String username, WatchTvSeriesSearchOptions searchOptions) {

        int page = 0;
        if(searchOptions.getPage() != null && searchOptions.getPage() > 0) {
            page = searchOptions.getPage() - 1;
        }

        int pageSize = 10;
        if(    searchOptions.getPageSize() != null && searchOptions.getPageSize() > 0) {
            pageSize = searchOptions.getPageSize();
        }

        return watchListDao.findAll(
                new WatchedTvSeriesSearchSpecification(searchOptions,username),
                PageRequest.of(page,pageSize)
                ).map(TvSeriesEntityMapper::fromWatchListEntity);

    }



    @Override
    public Optional<TvSeries> getTvSeriesOnWatchListById(String username, Integer tvSeriesId) {

        UserEntity user= userDao.findByUsername(username).orElseThrow(()-> new UnknownUserException(username));


        for(WatchListEntity entity: user.getWatchedTvSeries()){
            if (entity.getTvSeries().getId().equals(tvSeriesId)){
                log.info("Returned Tv Series with id[{}] from users[{}] watch list  ",tvSeriesId,username);
                return Optional.of(TvSeriesEntityMapper.fromWatchListEntity(entity));
            }
        }

        log.info("Tv Series with id[{}] is not founded on users[{}] watch list  ",tvSeriesId,username);
        return Optional.empty();
    }

    @Override
    public void update(WatchedTvSeries watchedTvSeries, String username) {

        Integer tvSeriesId=watchedTvSeries.getTvSeriesId();
        Integer rating= watchedTvSeries.getRating();
        Integer episodesWatched=watchedTvSeries.getEpisodesWatched();

        UserEntity user= userDao.findByUsername(username).orElseThrow(()-> new UnknownUserException(username));
        for (WatchListEntity watchList :user.getWatchedTvSeries()){
            if(watchList.getTvSeries().getId().equals(tvSeriesId)){
                watchList.setRating(rating);
                watchList.setEpisodesWatched(episodesWatched);
            }

        }
        log.info("Entry of Tv Series with id[{}] is updated on Users[{}] watchlist  ",tvSeriesId,username);
        userDao.save(user);

    }

    @Override
    public void delete(String username, Integer tvSeriesId) {
        UserEntity user= userDao.findByUsername(username).orElseThrow(()-> new UnknownUserException(username));

        user.getWatchedTvSeries().removeIf(watchList -> watchList.getTvSeries().getId().equals(tvSeriesId));

        log.info("Removed Tv Series with id[{}] from users[{}] watch list  ",tvSeriesId,username);
        userDao.save(user);
    }


}
