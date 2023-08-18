package rs.ac.ni.pmf.rwa.tvseries.data.entity;

import lombok.*;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(unique=true)
    String username;
    String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    List<WatchListEntity> watchedTvSeries;

    @Enumerated(EnumType.STRING)
    Roles role;


    @Builder.Default
    boolean accountNonExpired=true;
    @Builder.Default
    boolean accountNonLocked=true;
    @Builder.Default
    boolean credentialsNonExpired=true;
    @Builder.Default
    boolean enabled=true;



}
