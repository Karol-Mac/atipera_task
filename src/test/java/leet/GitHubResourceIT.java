package leet;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import leet.model.RepositoryDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusIntegrationTest
public class GitHubResourceIT {

    private static final String TEST_USERNAME = "octocat";

    @Test
    void shouldFetchNonForkRepositoriesWithBranches() {

        List<RepositoryDto> repositories = when()
                .get("/repositories/{username}", TEST_USERNAME)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", RepositoryDto.class);


        assertThat(repositories)
                .isNotEmpty()
                .allSatisfy(repo -> {
                    assertThat(repo.getName()).isNotBlank();
                    assertThat(repo.getOwnerLogin()).isEqualTo(TEST_USERNAME);

                    assertThat(repo.getBranches())
                            .isNotEmpty()
                            .allSatisfy(branch -> {
                                assertThat(branch.getName()).isNotBlank();
                                assertThat(branch.getLastCommitSha()).isNotBlank();
                            });
                });
    }
}