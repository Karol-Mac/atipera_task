package leet.utils;

import jakarta.enterprise.context.ApplicationScoped;
import leet.model.BranchDto;
import leet.model.github.GitHubBranch;

import java.util.List;

@ApplicationScoped
public class BranchMapper {

    public List<BranchDto> mapListOfGitHubBranchToListOfBranchDto(List<GitHubBranch> branches){
        return branches.stream()
                .map(this::mapGitHubBranchToBranchDto)
                .toList();
    }

    private BranchDto mapGitHubBranchToBranchDto(GitHubBranch branch){
        return new BranchDto(branch.getName(), branch.getCommit().getSha());
    }
}
