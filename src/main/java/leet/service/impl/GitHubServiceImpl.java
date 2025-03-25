package leet.service.impl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import leet.client.GitHubClient;
import leet.exception.GitHubUserNotFoundException;
import leet.model.RepositoryDto;
import leet.model.github.GitHubRepo;
import leet.service.GitHubService;
import leet.utils.RepositoryMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.function.Predicate;

@ApplicationScoped
public class GitHubServiceImpl implements GitHubService {

    private final GitHubClient gitHubClient;

    private final RepositoryMapper repositoryMapper;

    public GitHubServiceImpl(@RestClient GitHubClient gitHubClient, RepositoryMapper repositoryMapper){
        this.gitHubClient = gitHubClient;
        this.repositoryMapper = repositoryMapper;
    }

    @Override
    public Multi<RepositoryDto> getReposWithBranches(String username){
        return gitHubClient.getRepos(username)
                .onFailure(ClientWebApplicationException.class)
                        .recoverWithUni(ex -> {
                                ClientWebApplicationException webEx = (ClientWebApplicationException) ex;
                                return webEx.getResponse().getStatus() == 404 ?
                                        Uni.createFrom().failure(new GitHubUserNotFoundException("GitHub user not found"))
                                        : Uni.createFrom().failure(ex);
                        })
                .onItem().transformToMulti(repos ->
                        Multi.createFrom().iterable(repos.stream()
                                .filter(Predicate.not(GitHubRepo::isFork))
                                .toList()))
                .onItem().transformToUniAndMerge(this::fetchBranchesForRepo);
    }

    private Uni<RepositoryDto> fetchBranchesForRepo(GitHubRepo githubRepo){
        return gitHubClient.getBranches(githubRepo.getOwner().getLogin(), githubRepo.getName())
                .map(branches -> repositoryMapper.mapGitHubRepoToRepositoryDto(githubRepo, branches));
    }
}