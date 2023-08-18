package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.data.TestConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.TvSeriesSearchSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TvSeriesDaoIT {

    @Autowired
    private TvSeriesDao tvSeriesDao;

    private static  final List<TvSeriesEntity> DEFAULT_TV_SERIES= Arrays.asList(
            TvSeriesEntity
                    .builder()
                    .numberOfEpisodes(10)
                    .name("TV_SERIES1")
                    .usersWatched(
                            Arrays.asList(
                                    WatchListEntity.builder().rating(5).build(),
                                    WatchListEntity.builder().rating(6).build(),
                                    WatchListEntity.builder().rating(8).build(),
                                    WatchListEntity.builder().rating(10).build()
                            )
                    )
                    .build(),
            TvSeriesEntity
                    .builder()
                    .numberOfEpisodes(5)
                    .name("TV_SERIES2")
                    .usersWatched(
                            Arrays.asList(
                                    WatchListEntity.builder().rating(2).build(),
                                    WatchListEntity.builder().rating(4).build(),
                                    WatchListEntity.builder().rating(10).build(),
                                    WatchListEntity.builder().rating(9).build()
                            )
                    )
                    .build(),
            TvSeriesEntity
                    .builder()
                    .numberOfEpisodes(12)
                    .name("TV_SERIES3")
                    .usersWatched(
                            Arrays.asList(
                                    WatchListEntity.builder().rating(3).build(),
                                    WatchListEntity.builder().rating(1).build(),
                                    WatchListEntity.builder().rating(10).build(),
                                    WatchListEntity.builder().rating(9).build()
                            )
                    )
                    .build()
    );

    @BeforeEach
    public void initData()
    {
        tvSeriesDao.saveAll(DEFAULT_TV_SERIES);
    }

    @Test
    public void shouldGetAllTvSeries()
    {
        final List<TvSeriesEntity> users = tvSeriesDao.findAll();
        assertThat(users).hasSize(DEFAULT_TV_SERIES.size());
    }

    @Test
    public void shouldSaveTvSeries()
    {
        final TvSeriesEntity toSave =TvSeriesEntity.builder().build();
        tvSeriesDao.save(toSave);
        assertThat(tvSeriesDao.count()).isEqualTo(DEFAULT_TV_SERIES.size()+1);
    }

    @Test
    public void shouldFindTvSeriesById()
    {
        final Optional<TvSeriesEntity> founded =tvSeriesDao.findById(1);
        assertThat(founded.isPresent()).isTrue();
        assertThat(founded.get().getId()).isEqualTo(DEFAULT_TV_SERIES.get(0).getId());

    }

    @Test
    public void shouldDeleteTvSeriesById()
    {
        tvSeriesDao.deleteById(1);
        assertThat(tvSeriesDao.count()).isEqualTo(DEFAULT_TV_SERIES.size()-1);

    }

    @Test
    public void shouldFindTvSeriesBySearchOptionsTitleSearch()
    {
        TvSeriesSearchOptions searchOptions=TvSeriesSearchOptions
                .builder()
                .titleSearch("1")
                .build();

        Page<TvSeriesEntity> page=tvSeriesDao.findAll(new TvSeriesSearchSpecification(searchOptions), PageRequest.of(0,3));

        TvSeriesEntity expectedElement=DEFAULT_TV_SERIES.get(0);
        TvSeriesEntity actualElement=page.getContent().get(0);
        assertThat(expectedElement.getId()).isEqualTo(actualElement.getId());

    }
    @Test
    public void shouldFindTvSeriesBySearchOptionsMaxNumberOfEpisodes()
    {
        TvSeriesSearchOptions searchOptions=TvSeriesSearchOptions
                .builder()
                .maxNumberOfEpisodes(11)
                .build();

        Page<TvSeriesEntity> page=tvSeriesDao.findAll(new TvSeriesSearchSpecification(searchOptions), PageRequest.of(0,3));

        List<TvSeriesEntity>expectedList=  DEFAULT_TV_SERIES.stream().filter(ell-> ell.getNumberOfEpisodes()<=11).toList();
        List<TvSeriesEntity>actualList=  page.getContent();

        for (int i=0;i<actualList.size();i++) {
            assertThat(actualList.get(i).getId()).isEqualTo(expectedList.get(i).getId());
        }
    }



}
