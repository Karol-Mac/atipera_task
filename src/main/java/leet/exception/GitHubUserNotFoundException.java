package leet.exception;

import lombok.Getter;


@Getter
public class GitHubUserNotFoundException extends RuntimeException {
    private final int status;

    public GitHubUserNotFoundException(String message) {
        super(message);
        this.status = 404;
    }
}