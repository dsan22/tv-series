package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;

public interface WatchListDao extends JpaRepository<WatchListEntity,Integer>, JpaSpecificationExecutor<WatchListEntity> {
}
