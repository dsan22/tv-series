package rs.ac.ni.pmf.rwa.tvseries.data.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tvSeries")
public class TvSeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    Integer numberOfEpisodes;
    @OneToMany(mappedBy = "tvSeries",cascade = CascadeType.ALL, orphanRemoval = true)
    List<WatchListEntity> usersWatched;

    @Transient
    Double averageRating;

    @PostLoad
    public void calculateRating()
    {
         averageRating= usersWatched.stream()
                .mapToInt( WatchListEntity::getRating)
                .average()
                .orElse(0.0d);
    }




}
