package leet.utils;

import jakarta.enterprise.context.ApplicationScoped;
import leet.model.RepositoryDto;
import leet.model.github.GitHubBranch;
import leet.model.github.GitHubRepo;

import java.util.List;

@ApplicationScoped
public class RepositoryMapper {

    private final BranchMapper branchMapper;

    public RepositoryMapper(BranchMapper branchMapper){
        this.branchMapper = branchMapper;
    }

    public RepositoryDto mapGitHubRepoToRepositoryDto(GitHubRepo githubRepo, List<GitHubBranch> branches){
        RepositoryDto repositoryDto = new RepositoryDto();
        repositoryDto.setName(githubRepo.getName());
        repositoryDto.setOwnerLogin(githubRepo.getOwner().getLogin());

        repositoryDto.setBranches(branchMapper.mapListOfGitHubBranchToListOfBranchDto(branches));
        return repositoryDto;
    }
}