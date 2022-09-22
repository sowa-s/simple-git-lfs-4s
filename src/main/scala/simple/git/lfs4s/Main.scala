package simple.git.lfs4s

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router
import scala.concurrent.duration._

object Main extends IOApp {

  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }
  val httpApp = Router("/" -> helloWorldService).orNotFound


  override def run(args: List[String]): IO[ExitCode] = IO {
    println("Hello!!!")
  }.as(ExitCode.Success)
//
//  override def run(args: List[String]): IO[ExitCode] = EmberServerBuilder
//    .default[IO]
//    .withHost(ipv4"0.0.0.0")
//    .withPort(port"8080")
//    .withHttpApp(httpApp)
//    .build
//    .use(_ => IO.never)
//    .as(ExitCode.Success)
}
