package simple.git.lfs4s

import cats.effect.IO
import io.circe.{Decoder, DecodingFailure}
import org.http4s.ParseFailure
import simple.git.lfs4s.command.{
  DownloadRequestProcessor,
  UploadRequestProcessor
}
import simple.git.lfs4s.model.{GitLFSRequest, GitLFSResponse}
import simple.git.lfs4s.model.Operation.{Download, Upload}
import simple.git.lfs4s.service.S3PresignedURLIssueServiceImpl

object Router {

  def route(request: Request): IO[Response] = {
    val body = request.body
      .map(_.as[GitLFSRequest])
      .getOrElse(Left(DecodingFailure.apply("body is empty", List.empty)))

    (request.path, body) match {
      case (_, Left(value)) => IO.raiseError(value)
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
