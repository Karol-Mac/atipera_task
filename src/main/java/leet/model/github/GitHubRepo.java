package leet.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
public class GitHubRepo {
    private String name;
    @JsonProperty("fork")
    private boolean isFork;
    private Owner owner;


    @Data
    public static class Owner {
        @JsonProperty("login")
        private String login;
    }
}