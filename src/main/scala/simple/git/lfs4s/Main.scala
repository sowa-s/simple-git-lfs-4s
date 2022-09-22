package simple.git.lfs4s

import cats.effect._
import cats.effect.std.Console
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._

object Main extends IOApp {

  case class Response(
      statusCode: Int,
      headers: Map[String, String],
      body: String
  )

  override def run(args: List[String]): IO[ExitCode] = Console[IO]
    .print(
      Response(
        statusCode = 200,
        headers = Map.empty,
        body = "{\"test\":1}"
      ).asJson
    )
    .as(ExitCode.Success)
}
