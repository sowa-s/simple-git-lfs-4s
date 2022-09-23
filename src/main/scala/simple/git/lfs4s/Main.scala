package simple.git.lfs4s

import cats.effect._
import cats.effect.std.Console
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.parse
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.circe._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Method, Uri}

object Main extends IOApp {

  val AWS_LAMBDA_RUNTIME_API = sys.env.getOrElse(
    "AWS_LAMBDA_RUNTIME_API",
    throw new Exception("AWS_LAMBDA_RUNTIME_API is not found")
  )
  val INVOCATION_ID = sys.env.getOrElse(
    "INVOCATION_ID",
    throw new Exception("INVOCATION_ID is not found")
  )
  val S3_BUCKET_NAME = sys.env.getOrElse(
    "S3_BUCKET_NAME",
    throw new Exception("S3_BUCKET_NAME is not found")
  )

  override def run(args: List[String]): IO[ExitCode] = {
    (for {
      request <- parse(args.mkString("")).flatMap(_.as[Request])
//      body = request.body.getOrElse(
//        Json.obj(("error", Json.fromString("empty body")))
//      )

    } yield for {
      _ <- Console[IO].println("logging test")
      response <- postLambdaResponse(body = args.mkString("").asJson)
        .as(ExitCode.Success)
    } yield response)
      .getOrElse(
        postLambdaResponse(body = args.mkString("error").asJson)
          .as(ExitCode.Success)
      )
  }
  def postLambdaResponse(body: Json): IO[Unit] = {
    val request = org.http4s.Request
      .apply[IO](
        Method.POST,
        Uri.unsafeFromString(
          s"http://${AWS_LAMBDA_RUNTIME_API}/2018-06-01/runtime/invocation/$INVOCATION_ID/response"
        )
      )
      .withEntity(Response.success(body).asJson)
    EmberClientBuilder.default[IO].build.use { client =>
      client.expect[Unit](request)
    }
  }
}
