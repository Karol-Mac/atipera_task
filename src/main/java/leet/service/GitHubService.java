package leet.service;

import io.smallrye.mutiny.Multi;
import leet.model.RepositoryDto;

public interface GitHubService {
    Multi<RepositoryDto> getReposWithBranches(String username);
}
