package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.specification.TvSeriesSearchSpecification;

import java.util.List;


public interface TvSeriesDao extends JpaRepository<TvSeriesEntity,Integer>, JpaSpecificationExecutor<TvSeriesEntity> {

}
