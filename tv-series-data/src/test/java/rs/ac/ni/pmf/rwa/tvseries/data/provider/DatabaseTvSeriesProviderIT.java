package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.TestConfiguration;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.TvSeriesSearchSpecification;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseTvSeriesProviderIT {

    @Mock
    private TvSeriesDao tvSeriesDao;

    @InjectMocks
    private DatabaseTvSeriesProvider databaseTvSeriesProvider;

    @Test
    public void shouldGetTvSeriesById( ){
        TvSeriesEntity entity=mock(TvSeriesEntity.class);
        Integer id=1;

        when(tvSeriesDao.findById(id)).thenReturn(Optional.of(entity));

        Optional<TvSeries>  returned=databaseTvSeriesProvider.getTvSeriesById(id);
        assertThat(returned.isPresent()).isTrue();
        assertThat(returned.get()).isEqualTo(TvSeriesEntityMapper.fromEntity(entity));


        when(tvSeriesDao.findById(id)).thenReturn(Optional.empty());

        Optional<TvSeries>  returned2=databaseTvSeriesProvider.getTvSeriesById(id);
        assertThat(returned2.isPresent()).isFalse();
    }


    @Test
    public void shouldSaveTvSeries( ){
        TvSeries tvSeries=mock(TvSeries.class);
        databaseTvSeriesProvider.saveTvSeries(tvSeries);
        verify(tvSeriesDao).save(TvSeriesEntityMapper.toEntity(tvSeries));
    }

    @Test
    public void shouldRemoveTvSeries( ){
        Integer id=1;
        databaseTvSeriesProvider.removeTvSeries(id);
        verify(tvSeriesDao).deleteById(id);
    }

    @Test
    public void shouldUpdateTvSeries( ){
        TvSeries tvSeries=mock(TvSeries.class);
        databaseTvSeriesProvider.updateTvSeries(tvSeries);
        verify(tvSeriesDao).save(TvSeriesEntityMapper.toEntity(tvSeries));
    }

    @Test
    public void shouldGetAllTvSeries( ){
        TvSeriesSearchOptions searchOptions=mock(TvSeriesSearchOptions.class);

        Page<TvSeriesEntity> page=mock(Page.class);
       when(tvSeriesDao.findAll(
              any(TvSeriesSearchSpecification.class),
              any(PageRequest.class))         )
               .thenReturn(page);
        Page<TvSeries> returnedPage= databaseTvSeriesProvider.getAllTvSeries(searchOptions);
        assertThat(returnedPage).isEqualTo(page.map(TvSeriesEntityMapper::fromEntity));


        TvSeriesSearchOptions searchOptions2=mock(TvSeriesSearchOptions.class);
        when(searchOptions2.getPageSize()).thenReturn(1);
        when(searchOptions2.getPage()).thenReturn(10);

        Page<TvSeriesEntity> page2=mock(Page.class);
        when(tvSeriesDao.findAll(
                any(TvSeriesSearchSpecification.class),
                any(PageRequest.class))         )
                .thenReturn(page2);
        Page<TvSeries> returnedPage2= databaseTvSeriesProvider.getAllTvSeries(searchOptions2);
        assertThat(returnedPage2).isEqualTo(page2.map(TvSeriesEntityMapper::fromEntity));


    }
}
