package rs.ac.ni.pmf.rwa.tvseries.data.mapper;

import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.UserAccess;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;

public class UserEntityMapper {


    public static User fromEntity(final UserEntity entity)
    {
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .userAccess(
                        UserAccess.builder()
                            .accountNonExpired(entity.isAccountNonExpired())
                            .accountNonLocked(entity.isAccountNonLocked())
                            .credentialsNonExpired(entity.isCredentialsNonExpired())
                            .enabled(entity.isEnabled())
                            .role(entity.getRole())
                            .build()
                )
                .build();
    }

    public static UserEntity toEntity(final User user)
    {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .accountNonExpired(user.getUserAccess().isAccountNonExpired())
                .accountNonLocked(user.getUserAccess().isAccountNonLocked())
                .credentialsNonExpired(user.getUserAccess().isCredentialsNonExpired())
                .enabled(user.getUserAccess().isEnabled())
                .role(user.getUserAccess().getRole())
                .build();
    }

    public static UserAccess accessFromEntity(final UserEntity entity)
    {
        return UserAccess.builder()
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .enabled(entity.isEnabled())
                .role(entity.getRole())
                .build();
    }
}
