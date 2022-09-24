package simple.git.lfs4s

import cats.effect.IO
import io.circe.DecodingFailure
import simple.git.lfs4s.command.{
  DownloadRequestProcessor,
  UploadRequestProcessor
}
import simple.git.lfs4s.model.GitLFSRequest
import simple.git.lfs4s.model.GitLFSRequest._
import simple.git.lfs4s.model.Operation.{Download, Upload}

object Router {

  def route(request: Request): IO[Response] = {
    logger.info("Route")
    val body = request.body
      .map(_.as[GitLFSRequest])
      .getOrElse(Left(DecodingFailure.apply("body is empty", List.empty)))

    (request.path, body) match {
      case (_, Left(value))                                                =>
        IO.raiseError(
          new Exception(
            value.getMessage() + "body: " + request.body.map(_.noSpaces)
          )
        )
      case ("/objects/batch", Right(value)) if value.operation == Upload   =>
        UploadRequestProcessor
          .execute(s3PresignedURLIssueService, value)
          .map(r => Response.success(r.toJson))
      case ("/objects/batch", Right(value)) if value.operation == Download =>
        DownloadRequestProcessor
          .execute(s3PresignedURLIssueService, value)
          .map(r => Response.success(r.toJson))
      case _ => IO.raiseError(new UnknownError("unknown path"))
    }
  }
}
