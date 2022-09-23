package simple.git.lfs4s.model

import io.circe.parser.parse
import org.scalatest.funsuite.AnyFunSuite

class GitLFSRequestTest extends AnyFunSuite {
  test("Can parse GitLFS Request") {
    val uploadRequest =
      """
        |{
        |"operation":"upload",
        |"objects":[{"oid":"90ec0230f29b8681119c61d817a0147281dc6d607be1c97f4c4876feba53fbce","size":2845017}],
        |"transfers":["basic","ssh","lfs-standalone-file"],
        |"ref":{"name":"refs/heads/main"},"hash_algo":"sha256"
        |}
        |""".stripMargin
    parse(uploadRequest).flatMap(_.as[GitLFSRequest]) match {
      case Right(value) => succeed
      case Left(error)  => fail(error.getMessage)
    }
  }
}
