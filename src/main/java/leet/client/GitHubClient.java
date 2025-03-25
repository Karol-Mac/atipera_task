package leet.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import leet.model.github.GitHubBranch;
import leet.model.github.GitHubRepo;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/")
@RegisterRestClient(configKey = "github-api")
public interface GitHubClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<GitHubRepo>> getRepos(@PathParam("username") String username);

    @GET
    @Path("/repos/{username}/{repo}/branches")
    Uni<List<GitHubBranch>> getBranches(@PathParam("username") String username, @PathParam("repo") String repo);
}