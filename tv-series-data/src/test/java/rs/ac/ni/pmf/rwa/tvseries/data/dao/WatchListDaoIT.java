package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.data.TestConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.WatchedTvSeriesSearchSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WatchListDaoIT {

    @Autowired
    private WatchListDao watchListDao;
    @Autowired
    private  UserDao userDao;
    @Autowired
    private TvSeriesDao tvSeriesDao;

    private static final UserEntity user1=UserEntity.builder().username("username1").build();
    private static final UserEntity user2=UserEntity.builder().username("username2").build();

    private  static  final TvSeriesEntity tvSeries1=TvSeriesEntity.builder().name("TvSeries1").numberOfEpisodes(10).build();

    private static  final TvSeriesEntity tvSeries2=TvSeriesEntity.builder().name("TvSeries2").numberOfEpisodes(10).build();
    private static  final List<WatchListEntity> DEFAULT_WATCH_LIST= Arrays.asList(
            WatchListEntity
                    .builder()
                    .episodesWatched(10)
                    .rating(9)
                    .user(user1)
                    .tvSeries(tvSeries1)
                    .build(),
            WatchListEntity
                    .builder()
                    .episodesWatched(4)
                    .rating(8)
                    .user(user1)
                    .tvSeries(tvSeries2)
                    .build(),
            WatchListEntity
                    .builder()
                    .episodesWatched(4)
                    .rating(8)
                    .user(user2)
                    .tvSeries(tvSeries1)
                    .build(),
            WatchListEntity
                    .builder()
                    .episodesWatched(9)
                    .rating(9)
                    .user(user2)
                    .tvSeries(tvSeries2)
                    .build()
    );

    @BeforeEach
    public void initData()
    {
        tvSeriesDao.save(tvSeries1);
        tvSeriesDao.save(tvSeries2);
        userDao.save(user1);
        userDao.save(user2);

        watchListDao.saveAll(DEFAULT_WATCH_LIST);
    }

    @Test
    public void shouldGetAllWatchlist()
    {
        WatchTvSeriesSearchOptions searchOptions= WatchTvSeriesSearchOptions
                .builder()
                .build();
        final List<WatchListEntity> watchList = watchListDao.findAll(new WatchedTvSeriesSearchSpecification(searchOptions,"username1"));

        List<WatchListEntity> expectedList=DEFAULT_WATCH_LIST
                .stream()
                .filter(watchListEntity -> Objects.equals(watchListEntity.getUser().getUsername(), "username1"))
                .toList();
        assertThat(watchList).hasSize(expectedList.size());
    }


    @Test
    public void shouldGetAllUnfinishedTvSeries()
    {
        WatchTvSeriesSearchOptions searchOptions= WatchTvSeriesSearchOptions
                .builder()
                .showUnfinishedTvSeries(true)
                .build();
        final List<WatchListEntity> watchList = watchListDao.findAll(new WatchedTvSeriesSearchSpecification(searchOptions,"username1"));

        List<WatchListEntity> expectedList=DEFAULT_WATCH_LIST
                .stream()
                .filter(watchListEntity -> !Objects.equals(watchListEntity.getEpisodesWatched(), watchListEntity.getTvSeries().getNumberOfEpisodes())&&
                        Objects.equals(watchListEntity.getUser().getUsername(), "username1")
                       )
                .toList();
        assertThat(watchList).hasSize(expectedList.size());
    }
    @Test
    public void shouldGetAllFinishedTvSeries()
    {
        WatchTvSeriesSearchOptions searchOptions= WatchTvSeriesSearchOptions
                .builder()
                .showFinishedTvSeries(true)
                .build();
        final List<WatchListEntity> watchList = watchListDao.findAll(new WatchedTvSeriesSearchSpecification(searchOptions,"username1"));

        List<WatchListEntity> expectedList=DEFAULT_WATCH_LIST
                .stream()
                .filter(watchListEntity -> Objects.equals(watchListEntity.getEpisodesWatched(), watchListEntity.getTvSeries().getNumberOfEpisodes())&&
                        Objects.equals(watchListEntity.getUser().getUsername(), "username1")
                )
                .toList();
        assertThat(watchList).hasSize(expectedList.size());
    }

    @Test
    public void shouldGetTvSeriesByName()
    {
        WatchTvSeriesSearchOptions searchOptions= WatchTvSeriesSearchOptions
                .builder()
                .titleSearch("TvSeries1")
                .build();
        final List<WatchListEntity> watchList = watchListDao.findAll(new WatchedTvSeriesSearchSpecification(searchOptions,"username1"));

        List<WatchListEntity> expectedList=DEFAULT_WATCH_LIST
                .stream()
                .filter(watchListEntity -> Objects.equals("TvSeries1", watchListEntity.getTvSeries().getName())&&
                        Objects.equals(watchListEntity.getUser().getUsername(), "username1")
                )
                .toList();
        assertThat(watchList).hasSize(expectedList.size());
    }

}
