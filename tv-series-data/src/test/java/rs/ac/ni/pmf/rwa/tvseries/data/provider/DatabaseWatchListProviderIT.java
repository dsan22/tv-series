package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.WatchListDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.WatchedTvSeriesSearchSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseWatchListProviderIT {

    @Mock
    private UserDao userDao;
    @Mock
    private TvSeriesDao tvSeriesDao;
    @Mock
    private WatchListDao watchListDao;

    @InjectMocks
    private DatabaseWatchListProvider databaseWatchListProvider;

    @Test
    public void shouldAddToWatchList( ){
        String username="username";
        Integer tvSeriesId=1;
        WatchedTvSeries watchedTvSeries=mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        UserEntity userEntity=mock(UserEntity.class);
        when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(tvSeriesDao.findById(tvSeriesId)).thenReturn(Optional.of(mock(TvSeriesEntity.class)));

        databaseWatchListProvider.addToWatchList(username,watchedTvSeries);
        verify(userDao).save(userEntity);

    }

    @Test
    public void shouldGetTvSeriesByUsername( ){
        WatchTvSeriesSearchOptions searchOptions=mock(WatchTvSeriesSearchOptions.class);
        String username="username";

        Page<WatchListEntity> page=mock(Page.class);
        when(watchListDao.findAll(
                any(WatchedTvSeriesSearchSpecification.class),
                any(PageRequest.class))
        ).thenReturn(page);
        Page<TvSeries> returnedPage= databaseWatchListProvider.getTvSeriesByUsername(username, searchOptions);
        assertThat(returnedPage).isEqualTo(page.map(TvSeriesEntityMapper::fromWatchListEntity));



        WatchTvSeriesSearchOptions searchOptions2=mock(WatchTvSeriesSearchOptions.class);
        when(searchOptions2.getPageSize()).thenReturn(1);
        when(searchOptions2.getPage()).thenReturn(10);
        String username2="username";

        Page<WatchListEntity> page2=mock(Page.class);
        when(watchListDao.findAll(
                any(WatchedTvSeriesSearchSpecification.class),
                any(PageRequest.class))
        ).thenReturn(page2);
        Page<TvSeries> returnedPage2= databaseWatchListProvider.getTvSeriesByUsername(username2, searchOptions2);
        assertThat(returnedPage2).isEqualTo(page2.map(TvSeriesEntityMapper::fromWatchListEntity));


    }

    @Test
    public void shouldGetTvSeriesOnWatchList( ){
     String username="username";
     Integer tvSeriesId=1;
     TvSeriesEntity tvSeriesEntity=mock(TvSeriesEntity.class);
     when(tvSeriesEntity.getId()).thenReturn(tvSeriesId);

     WatchListEntity watchListEntity=mock(WatchListEntity.class);
     when(watchListEntity.getTvSeries()).thenReturn(tvSeriesEntity);

     UserEntity userEntity=mock(UserEntity.class);
     when(userEntity.getWatchedTvSeries()).thenReturn(List.of(watchListEntity));

     when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));

     Optional<TvSeries> returned=databaseWatchListProvider.getTvSeriesOnWatchListById(username,tvSeriesId);

     assertThat(returned.isPresent()).isTrue();
     assertThat(returned.get()).isEqualTo(TvSeriesEntityMapper.fromWatchListEntity(watchListEntity));



        String username2="username";
        Integer tvSeriesId2=1;

        UserEntity userEntity2=mock(UserEntity.class);
        when(userEntity.getWatchedTvSeries()).thenReturn(new ArrayList<>());

        when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));

        Optional<TvSeries> returned2=databaseWatchListProvider.getTvSeriesOnWatchListById(username2,tvSeriesId2);

        assertThat(returned2.isPresent()).isFalse();


    }


    @Test
    public void shouldUpdateWatchList( ){
        String username="username";
        Integer tvSeriesId=1;
        WatchedTvSeries watchedTvSeries=mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);

        TvSeriesEntity tvSeriesEntity=mock(TvSeriesEntity.class);
        when(tvSeriesEntity.getId()).thenReturn(tvSeriesId);

        WatchListEntity watchListEntity=mock(WatchListEntity.class);
        when(watchListEntity.getTvSeries()).thenReturn(tvSeriesEntity);

        UserEntity userEntity=mock(UserEntity.class);
        when(userEntity.getWatchedTvSeries()).thenReturn(List.of(watchListEntity));
        when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));


        databaseWatchListProvider.update(watchedTvSeries,username);
        verify(userDao).save(userEntity);

    }

    @Test
    public void shouldDeleteTvSeriesFromWatchList( ){
       String username="username";
       Integer tvSeriesId=1;
        TvSeriesEntity tvSeriesEntity=mock(TvSeriesEntity.class);

        WatchListEntity watchListEntity=mock(WatchListEntity.class);

        UserEntity userEntity=mock(UserEntity.class);

        List<WatchListEntity> list=  new java.util.ArrayList<>(List.of(watchListEntity));

        list.removeIf(watchListEntity1 -> true);

        when(userEntity.getWatchedTvSeries()).thenReturn(list);
        when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));

       when(userDao.findByUsername(username)).thenReturn(Optional.of(userEntity));

       databaseWatchListProvider.delete(username,tvSeriesId);
       verify(userDao).save(userEntity);


    }
}
