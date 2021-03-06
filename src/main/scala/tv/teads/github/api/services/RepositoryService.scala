package tv.teads.github.api.services

import scala.concurrent.{ExecutionContext, Future}

import tv.teads.github.api.GithubApiClientConfig
import tv.teads.github.api.model._

class RepositoryService(config: GithubApiClientConfig) extends GithubService(config) with GithubApiCodecs {

  def listTags(repository: String)(implicit ec: ExecutionContext): Future[List[Tag]] =
    fetchMultiple[Tag](
      s"repos/${config.owner}/$repository/tags",
      s"Fetching tags for repository $repository failed"
    )

  def fetchAllRepositories(implicit ec: ExecutionContext): Future[List[Repository]] =
    fetchAllPages[Repository](
      s"orgs/${config.owner}/repos",
      s"Fetching all repositories failed"
    )

}
