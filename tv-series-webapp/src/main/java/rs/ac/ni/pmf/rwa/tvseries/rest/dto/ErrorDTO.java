package rs.ac.ni.pmf.rwa.tvseries.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDTO
{

	@Schema(description = "Code for occurred error")
	ErrorCode code;

	@Schema(description = "Details of occurred error")
	String details;
}
