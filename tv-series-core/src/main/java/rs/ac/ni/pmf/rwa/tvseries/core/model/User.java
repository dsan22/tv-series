package rs.ac.ni.pmf.rwa.tvseries.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    String username;
    String password;
    @Builder.Default
    UserAccess userAccess=UserAccess.builder().build();
}
