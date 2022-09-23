package simple.git.lfs4s.model

import enumeratum._
import io.circe.{Decoder, HCursor}

sealed abstract class Operation(override val entryName: String)
    extends EnumEntry

case object Operation extends Enum[Operation] with CirceEnum[Operation] {
  case object Upload extends Operation("upload")
  case object Download extends Operation("download")
  val values = findValues
}

case class GitLFSRequestObject(
    oid: String,
    size: Int
)

case class GitLFSRequest(
    operation: Operation,
    objects: List[GitLFSRequestObject],
    transfers: List[String],
    ref: Map[String, String],
    hashAlgo: String
)

object GitLFSRequest {
  implicit val requestObjectDecoder: Decoder[GitLFSRequestObject] =
    (c: HCursor) =>
      for {
        oid <- c.downField("oid").as[String]
        size <- c.downField("size").as[Int]
      } yield GitLFSRequestObject(oid, size)

  implicit val requestDecoder: Decoder[GitLFSRequest] = (c: HCursor) =>
    for {
      operation <- c.downField("operation").as[Operation]
      objects <- c
        .downField("objects")
        .as[List[GitLFSRequestObject]]
      transfers <- c.downField("transfers").as[List[String]]
      ref <- c.downField("ref").as[Map[String, String]]
      hashAlgo <- c.downField("hash_algo").as[String]
    } yield GitLFSRequest(operation, objects, transfers, ref, hashAlgo)
}
