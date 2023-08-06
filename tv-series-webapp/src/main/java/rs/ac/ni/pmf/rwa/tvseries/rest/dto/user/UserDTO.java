package rs.ac.ni.pmf.rwa.tvseries.rest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    @Schema(description = "Users username",example = "user",defaultValue = "user")
    String username;

    @Schema(description = "Users password",example = "1234",defaultValue = "1234")
    String password;
}
