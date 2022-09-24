package simple.git.lfs4s.model

import io.circe.generic.extras.Configuration
import io.circe._
import io.circe.syntax._
import io.circe.generic.extras.auto._

import java.time.Instant

case class Href(url: String, header: Map[String, String])

case class Actions(
    upload: Option[Href],
    download: Option[Href]
)

case class GitLFSResponseObject(
    oid: String,
    size: Int,
    authenticated: Boolean,
    actions: Actions,
    expiresAt: Instant
)

case class GitLFSResponse(
    transfer: String,
    objects: List[GitLFSResponseObject],
    hashAlgo: String
) {
  implicit val jsonConfig: Configuration =
    Configuration.default.withSnakeCaseMemberNames
  def toJson: Json = this.asJson
}
