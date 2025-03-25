package leet.model.github;

import lombok.Data;

@Data
public class GitHubBranch {
    private String name;
    private Commit commit;

    @Data
    public static class Commit {
        private String sha;
    }
}
