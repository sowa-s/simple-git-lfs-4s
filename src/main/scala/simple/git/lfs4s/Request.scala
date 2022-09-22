package simple.git.lfs4s

import io.circe.Json

case class Request(
    path: String,
    body: Option[Json]
)
