package us.livebythecode.architecture.referencemodels.testing.exception;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<BadInputAddressException> {
	@Override
	public Response toResponse(BadInputAddressException exception) {
		ApiError error = new ApiError(LocalDate.now().toString()+"T"+LocalTime.now().toString(), Status.BAD_REQUEST.getStatusCode(), "Invalid Input Address", exception.getMessage());
		//return Response.status(Status.BAD_REQUEST).entity(error).build();
        return Response.status(Response.Status.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity(error)
                .build();

	}
}