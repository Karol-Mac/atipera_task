package leet.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GitHubExceptionMapper implements ExceptionMapper<GitHubUserNotFoundException> {

    @Override
    public Response toResponse(GitHubUserNotFoundException ex) {
        var errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus());

        return Response.status(ex.getStatus())
                .entity(errorResponse)
                .build();
    }
}