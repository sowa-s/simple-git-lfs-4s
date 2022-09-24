package simple.git.lfs4s

import cats.effect.IO
import io.circe.{DecodingFailure, Json}
import simple.git.lfs4s.command.{
  DownloadRequestProcessor,
  UploadRequestProcessor
}
import simple.git.lfs4s.model.GitLFSRequest
import simple.git.lfs4s.model.GitLFSRequest._
import simple.git.lfs4s.model.Operation.{Download, Upload}
import io.circe.syntax._
import io.circe.parser.parse

object Router {

  def route(request: Request): IO[Response] = {
    val body: Either[DecodingFailure, GitLFSRequest] = request.body
      .toRight(DecodingFailure.apply("body is empty", List.empty))
      .flatMap(v =>
        parse(v).left.flatMap(e =>
          Left(
            DecodingFailure.apply(s"parse failure. ${e.getMessage}", List.empty)
          )
        )
      )
      .flatMap(_.as[GitLFSRequest])

    (request.path, body) match {
      case (_, Left(value))                                                =>
        IO.raiseError(
          new Exception(
            value.getMessage() + "body: " + request.body
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
