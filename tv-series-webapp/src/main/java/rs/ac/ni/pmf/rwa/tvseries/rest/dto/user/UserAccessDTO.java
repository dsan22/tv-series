package rs.ac.ni.pmf.rwa.tvseries.rest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

@Value
@Builder
public class UserAccessDTO {

    @Schema(description = "Users role",example = "user",defaultValue = "user")
    @Builder.Default
    Roles role=Roles.USER;
    @Schema(description = "Is User account non expired",example = "true",defaultValue = "true")
    @Builder.Default
    boolean accountNonExpired=true;
    @Schema(description = "Is User account non locked",example = "true",defaultValue = "true")
    @Builder.Default
    boolean accountNonLocked=true;
    @Schema(description = "Is User credentials non expired",example = "true",defaultValue = "true")
    @Builder.Default
    boolean credentialsNonExpired=true;
    @Schema(description = "Is User account enabled",example = "true",defaultValue = "true")
    @Builder.Default
    boolean enabled=true;
}
