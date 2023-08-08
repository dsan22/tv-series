package rs.ac.ni.pmf.rwa.tvseries.rest.dto.user;

import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

@Value
@Builder
public class UserAccessDTO {

    @Builder.Default
    Roles role=Roles.USER;
    @Builder.Default
    boolean accountNonExpired=true;
    @Builder.Default
    boolean accountNonLocked=true;
    @Builder.Default
    boolean credentialsNonExpired=true;
    @Builder.Default
    boolean enabled=true;
}
