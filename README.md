# atipera_task
This project is created using java 17 and Quarkus framework, version 3.19.4.
Project is a solution for the task provided by Atipera.
It fetches non-fork repositories with their branches for a specific GitHub user.

## Running the application:
### In dev mode 
You can run the application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

### From packaged JAR
The application can be packaged using:

```shell script
./mvnw clean package
```

## Using the application
The application exposes a REST endpoint to fetch non-fork repositories
with their branches for a specific GitHub user.

### REST Endpoint
```curl
/GET /repositories/{username}
```
Example usage:
```shell
curl --location 'http://localhost:8080/repositories/Karol-Mac'
```

## Project Structure
1. Model Classes

   - leet.model.github.GitHubRepo - represents a GitHub repository.
   - fields:
       - String name: The name of the repository.
       - boolean isFork: Indicates whether the repository is a fork.
       - Owner owner: inner class representing the owner of the repository.
           - Owner: The owner of the repository.
             - String login: The login of the owner.
      
   - class name: leet.model.github.GitHubBranch - represents a branch in a GitHub repository.
   - fields:
      - String name: The name of the branch.
      - Commit commit: inner class representing the commit of the branch.
        - Commit: The commit of the branch.
          - String sha: The SHA of the commit.

   - leet.model.RepositoryDto - data transfer object for a repository with its branches.
   - fields:
     - String name: The name of the repository.
     - String ownerLogin: The login of the repository owner.
     - List<BranchDto> branches: The list of branches in the repository.

   - leet.model.BranchDto - data transfer object for a repository with its branches.
   - fields:
       - String name: The name of the repository.
       - String lastCommitSha: The SHA of the last commit in the branch.

2. Client Classes

   - leet.client.GitHubClient - defines the REST client for interacting with the GitHub API.
   - methods:
      - Uni<List<GitHubRepo>> getRepos(String username): Fetches the list of repositories for a given user.
      - Uni<List<GitHubBranch>> getBranches(String username, String repo): Fetches the list of branches for a given repository.

3. Service Classes

   - leet.service.GitHubService - provides services for interacting with GitHub data.
   - methods:
     - Multi<RepositoryDto> getReposWithBranches(String username): Fetches non-fork repositories with their branches for a specific user.
     - Uni<RepositoryDto> fetchBranchesForRepo(GitHubRepo githubRepo): Fetches the branches for a given repository.

4. Mapper Classes

    - leet.mapper.RepositoryMapper - maps GitHub repository and branch entities to data transfer objects.
    - methods:
      - RepositoryDto mapGitHubRepoToRepositoryDto(GitHubRepo githubRepo, List<GitHubBranch> branches):
        Maps a GitHub repository and its branches to a RepositoryDto.

    - leet.mapper.BranchMapper - maps GitHub branch entities to data transfer objects.
    - methods:
      - List<BranchDto> mapListOfGitHubBranchToListOfBranchDto(List<GitHubBranch> branches): Maps a list of GitHub branches to a list of BranchDto.
      - BranchDto mapGitHubBranchToBranchDto(GitHubBranch branch): Maps a GitHub branch to a BranchDto.

5. Resource Classes
   - leet.resource.GitHubResource - REST resource for GitHub-related endpoints.
   - endpoints:
     - GET /repositories/{username}: Fetches non-fork repositories with their branches for a specific user.
       - produces: application/json
       - returns: Multi<RepositoryDto>
       - path parameters:
         - username: The username of the GitHub user.

6. Test Classes
   - leet.GitHubResourceIT - contains integration test for the GitHub resource.
   - test:
     - shouldFetchNonForkRepositoriesWithBranches(): fetching non-fork repositories with their branches for a specific user.
     - steps:
       This test method performs the following steps:
       1. Sends a GET request to the `/repositories/{username}` endpoint with a test username.
       2. Verifies that the response status code is 200 (OK).
       3. Extracts the response body and maps it to a list of `RepositoryDto` objects.
       4. Asserts that the list of repositories is not empty.
       5. For each repository, it asserts:
           - The repository name is not blank.
           - The repository owner login matches the test username.
           - The list of branches is not empty.
           - For each branch, it asserts:
               - The branch name is not blank.
               - The last commit SHA is not blank.
