package simple.git.lfs4s

import cats.data.EitherT
import cats.effect._
import cats.effect.std.Console
import io.circe.{DecodingFailure, Json}
import io.circe.parser.parse
import io.circe.syntax._
import io.circe.generic.auto._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    (for {
      request <- parse(args.head).flatMap(_.as[Request])
      body <- request.body.toRight(
        DecodingFailure.Reason.CustomReason("Body is empty")
      )
    } yield for {
      response <- Console[IO]
        .print(
          Response.success(body).asJson
        )
        .as(ExitCode.Success)
    } yield response)
      .getOrElse(Console[IO].print("error").as(ExitCode.Error))
  }
}
