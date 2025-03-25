package leet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchDto {
    private String name;

    @JsonProperty("last_commit_sha")
    private String lastCommitSha;
}
