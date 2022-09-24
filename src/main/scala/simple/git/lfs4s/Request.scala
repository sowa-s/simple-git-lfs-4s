package simple.git.lfs4s

case class Request(
    path: String,
    body: Option[String]
)
