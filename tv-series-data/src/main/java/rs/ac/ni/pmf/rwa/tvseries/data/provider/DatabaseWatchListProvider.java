package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.WatchListProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
public class DatabaseWatchListProvider  implements WatchListProvider {

    final UserDao userDao;
    final TvSeriesDao tvSeriesDao;




    @Override
    public void addToWatchList(String username, WatchedTvSeries watchedTvSeries) {

        Integer tvSeriesId=watchedTvSeries.getTvSeriesId();
        Integer rating= watchedTvSeries.getRating();
        Integer episodesWatched=watchedTvSeries.getEpisodesWatched();

        UserEntity user= userDao.findById(username).orElseThrow(()-> new UnknownUserException(username));
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
    public List<TvSeries>  getTvSeriesByUsername(String username){
        UserEntity user= userDao.findById(username).orElseThrow(()-> new UnknownUserException(username));

        List<TvSeries> list = new ArrayList<TvSeries>() ;
        user.getWatchedTvSeries()
                .forEach(watchListEntity ->
                        {
                            list.add(TvSeries.builder()
                                            .id(watchListEntity.getTvSeries().getId())
                                            .name(watchListEntity.getTvSeries().getName())
                                            .numberOfEpisodes(watchListEntity.getTvSeries().getNumberOfEpisodes())
                                            .rating(Double.valueOf(watchListEntity.getRating()) )
                                            .episodesWatched(watchListEntity.getEpisodesWatched())
                                    .build());
                        });
        log.info("Returned list of all Tv Series watched by user[{}] ",username);
        return list;

    }

    @Override
    public Double getAverageRating(Integer tvSeriesId) {
      TvSeriesEntity tvSeries= tvSeriesDao.findById(tvSeriesId).orElseThrow(()-> new UnknownTvSeriesException(tvSeriesId));
        log.info("Returned average rating of Tv Series with id[{}]  ",tvSeriesId);
      return tvSeries.getUsersWatched().stream()
              .mapToInt( WatchListEntity::getRating)
              .average().orElse(0.0d);


    }

    @Override
    public Optional<TvSeries> getTvSeriesOnWatchListById(String username, Integer tvSeriesId) {

        UserEntity user= userDao.findById(username).orElseThrow(()-> new UnknownUserException(username));


        for(WatchListEntity entity: user.getWatchedTvSeries()){
            if (entity.getTvSeries().getId().equals(tvSeriesId)){
                log.info("Returned Tv Series with id[{}] from users[{}] watch list  ",tvSeriesId,username);
                return Optional.of(TvSeries.builder()
                        .id(entity.getTvSeries().getId())
                        .name(entity.getTvSeries().getName())
                        .numberOfEpisodes(entity.getTvSeries().getNumberOfEpisodes())
                        .rating(Double.valueOf(entity.getRating()) )
                        .episodesWatched(entity.getEpisodesWatched())
                        .build());
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

        UserEntity user= userDao.findById(username).orElseThrow(()-> new UnknownUserException(username));
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
        UserEntity user= userDao.findById(username).orElseThrow(()-> new UnknownUserException(username));

        user.getWatchedTvSeries().removeIf(watchList -> watchList.getTvSeries().getId().equals(tvSeriesId));

        log.info("Removed Tv Series with id[{}] from users[{}] watch list  ",tvSeriesId,username);
        userDao.save(user);
    }
}
