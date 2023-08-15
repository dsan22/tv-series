package rs.ac.ni.pmf.rwa.tvseries.data.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchTvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.shared.TvSeriesSortByField;
import rs.ac.ni.pmf.rwa.tvseries.shared.WatchedTvSeriesSortByField;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WatchedTvSeriesSearchSpecification implements Specification<WatchListEntity> {

    private final WatchTvSeriesSearchOptions searchOptions;
    private final String username;

    @Override
    public Predicate toPredicate(Root<WatchListEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList=new ArrayList<>();

        Join< WatchListEntity, TvSeriesEntity> tvSeriesJoin=root.join("tvSeries",JoinType.LEFT);
        Join<WatchListEntity, UserEntity> userJoin=root.join("user",JoinType.LEFT);


        Path<String> name=tvSeriesJoin.get("name");
        Path<String> username=userJoin.get("username");

        Path<Integer> totalEpisodes=tvSeriesJoin.get("numberOfEpisodes");
        Path<Integer> watchedEpisodes=root.get("episodesWatched");



        predicateList.add(criteriaBuilder.equal(username,this.username));


        if(searchOptions.getTitleSearch()!=null){
            String param="%"+searchOptions.getTitleSearch()+"%";
            predicateList.add(criteriaBuilder.like(name,param));
        }

        if(searchOptions.isShowFinishedTvSeries()){
            predicateList.add(criteriaBuilder.equal(totalEpisodes,watchedEpisodes)) ;

        }
        if(searchOptions.isShowUnfinishedTvSeries()){
            predicateList.add(criteriaBuilder.notEqual(totalEpisodes,watchedEpisodes)) ;
        }



        WatchedTvSeriesSortByField sortBy = searchOptions.getSortBy();
        if(sortBy != null) {
            Path<?> propertyToSortBy = switch (sortBy) {
                case usersRating -> root.get("rating");
            };

            Sort.Direction direction = searchOptions.getSortDirection();
            if(direction == null || direction == Sort.Direction.ASC) {
                query.orderBy(criteriaBuilder.asc(propertyToSortBy));
            }
            else {
                query.orderBy(criteriaBuilder.desc(propertyToSortBy));
            }
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
