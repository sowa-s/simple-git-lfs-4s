package simple.git.lfs4s.command

import cats.effect.IO
import cats.implicits._
import simple.git.lfs4s.model._
import simple.git.lfs4s.service.S3PresignedURLIssueService

object DownloadRequestProcessor {
  def execute(
      service: S3PresignedURLIssueService,
      request: GitLFSRequest
  ): IO[GitLFSResponse] = {

    request.objects
      .map(o => {
        service
          .issueDownloadPresignedRequestURL(key = o.oid)
          .map(url =>
            GitLFSResponseObject(
              oid = o.oid,
              size = o.size,
              authenticated = true,
              actions = Actions(
                upload = None,
                download = Some(
                  Href(
                    url.url.toString,
                    header = Map.empty
                  )
                )
              ),
              expiresAt = url.expireAt
            )
          )
      })
      .sequence
      .map(objects =>
        GitLFSResponse(
          transfer = "basic",
          objects = objects,
          hashAlgo = request.hashAlgo
        )
      )
  }
}
