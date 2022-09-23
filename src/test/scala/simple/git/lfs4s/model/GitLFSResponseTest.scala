package simple.git.lfs4s.model

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._
import java.time.Instant

class GitLFSResponseTest extends AnyFunSuite {
  test("Can encode to json") {
    val response = GitLFSResponse(
      transfer = "basic",
      objects = List(
        GitLFSResponseObject(
          oid = "hoge",
          size = 1,
          authenticated = true,
          actions = Actions(
            upload = Some(Href("https://example.com")),
            download = None
          ),
          expiresAt = Instant.ofEpochMilli(20000)
        )
      ),
      hashAlgo = "sha256"
    )
    response.toJson.noSpaces shouldBe
      """{"transfer":"basic","objects":[{"oid":"hoge","size":1,"authenticated":true,"actions":{"upload":{"url":"https://example.com"},"download":null},"expires_at":"1970-01-01T00:00:20Z"}],"hash_algo":"sha256"}""".stripMargin.stripMargin
  }
}
