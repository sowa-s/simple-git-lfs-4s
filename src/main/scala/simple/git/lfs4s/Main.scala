package simple.git.lfs4s

import cats.effect._
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.parse
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.circe._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Method, Uri}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    (for {
      request <- parse(args.mkString("")).flatMap(_.as[Request])
    } yield for {
      response <- Router.route(request)
      lambdaResponse <- postLambdaResponse(response = response)
        .as(ExitCode.Success)
    } yield lambdaResponse)
      .getOrElse(
        postLambdaResponse(Response.success(args.mkString("error").asJson))
          .as(ExitCode.Success)
      )
  }
  def postLambdaResponse(response: Response): IO[Unit] = {
    val request = org.http4s.Request
      .apply[IO](
        Method.POST,
        Uri.unsafeFromString(
          s"http://${AWS_LAMBDA_RUNTIME_API}/2018-06-01/runtime/invocation/$INVOCATION_ID/response"
        )
      )
      .withEntity(response.asJson)
    EmberClientBuilder.default[IO].build.use { client =>
      client.expect[Unit](request)
    }
  }

}
