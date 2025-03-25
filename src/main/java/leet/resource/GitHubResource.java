package leet.resource;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import leet.service.GitHubService;
import leet.model.RepositoryDto;

@Path("/repositories")
public class GitHubResource {

    private final GitHubService gitHubService;

    public GitHubResource(GitHubService gitHubService){
        this.gitHubService = gitHubService;
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<RepositoryDto> getRepos(@PathParam("username") String username) {

        return gitHubService.getReposWithBranches(username);
    }
}