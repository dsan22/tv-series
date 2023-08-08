package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.UserAccess;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.user.UserAccessDTO;

@Component
public class UserAccessMapper {
    public UserAccess fromDto(UserAccessDTO dto){
        return UserAccess
                .builder()
                .role(dto.getRole())
                .enabled(dto.isEnabled())
                .credentialsNonExpired(dto.isCredentialsNonExpired())
                .accountNonLocked(dto.isAccountNonLocked())
                .accountNonExpired(dto.isAccountNonExpired())
                .build();
    }

    public UserAccessDTO toDto(UserAccess userAccess){
        return UserAccessDTO
                .builder()
                .role(userAccess.getRole())
                .enabled(userAccess.isEnabled())
                .credentialsNonExpired(userAccess.isCredentialsNonExpired())
                .accountNonLocked(userAccess.isAccountNonLocked())
                .accountNonExpired(userAccess.isAccountNonExpired())
                .build();
    }
}
