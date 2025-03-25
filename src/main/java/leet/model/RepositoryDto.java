package leet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RepositoryDto {

    public String name;
    @JsonProperty("owner_login")
    private String ownerLogin;

    private List<BranchDto> branches;
}