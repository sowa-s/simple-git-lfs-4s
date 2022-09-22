package simple.git.lfs4s

import cats.effect._
import cats.effect.std.Console
import io.circe.syntax._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = Console[IO].print(args.asJson).as(ExitCode.Success)
}
