package simple.git.lfs4s

import io.circe.Json
import io.circe.syntax._

case class Response(
    statusCode: Int,
    headers: Map[String, String],
    body: String
)
object Response {
  def success(body: Json): Response = Response(
    200,
    Map(("Content-Type" -> "application/vnd.git-lfs+json")),
    body.noSpaces
  )
}
