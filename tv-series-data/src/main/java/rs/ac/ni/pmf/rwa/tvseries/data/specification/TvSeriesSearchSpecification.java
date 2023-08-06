package rs.ac.ni.pmf.rwa.tvseries.data.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeriesSearchOptions;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;
import rs.ac.ni.pmf.rwa.tvseries.shared.TvSeriesSortByField;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TvSeriesSearchSpecification implements Specification<TvSeriesEntity> {

    private final TvSeriesSearchOptions searchOptions;

    @Override
    public Predicate toPredicate(Root<TvSeriesEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList=new ArrayList<>();



        Join<TvSeriesEntity,WatchListEntity> watchListJoin=root.join("usersWatched",JoinType.LEFT);




        Path<String> name=root.get("name");
        Path<Integer> numberOfEpisodes=root.get("numberOfEpisodes");

        if(searchOptions.getMaxNumberOfEpisodes()!=null){
            predicateList.add(criteriaBuilder.le(numberOfEpisodes,searchOptions.getMaxNumberOfEpisodes()));
        }

        if(searchOptions.getMinNumberOfEpisodes()!=null){
            predicateList.add(criteriaBuilder.ge(numberOfEpisodes,searchOptions.getMinNumberOfEpisodes()));
        }

        if(searchOptions.getMaxRating()!=null){
            Subquery<Double> subquery = query.subquery(Double.class);
            subquery.from(root.getJavaType());
            subquery.select(criteriaBuilder.coalesce(criteriaBuilder.avg(watchListJoin.get("rating")), 0.0));

            Predicate predicate = criteriaBuilder.lessThan(subquery.getSelection(), searchOptions.getMaxRating());

            predicateList.add(predicate);

        }

        if(searchOptions.getMinRating()!=null){
            Subquery<Double> subquery = query.subquery(Double.class);
            subquery.from(root.getJavaType());

            subquery.select(criteriaBuilder.coalesce(criteriaBuilder.avg(watchListJoin.get("rating")), 0.0));

            Predicate predicate = criteriaBuilder.greaterThan(subquery.getSelection(), searchOptions.getMinRating());

            predicateList.add(predicate);
        }

        if(searchOptions.getTitleSearch()!=null){
            String param="%"+searchOptions.getTitleSearch()+"%";
            predicateList.add(criteriaBuilder.like(name,param));
        }



        TvSeriesSortByField sortBy = searchOptions.getSortBy();
        if(sortBy != null) {
            Path<?> propertyToSortBy = switch (sortBy) {
                case rating -> root.get("rating");
                case numberOfEpisodes -> root.get("numberOfEpisodes");
                case name -> root.get("name");
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
