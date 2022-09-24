package simple.git.lfs4s.service

import cats.effect.IO
import software.amazon.awssdk.services.s3.model.{
  GetObjectRequest,
  PutObjectRequest
}
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.{
  GetObjectPresignRequest,
  PutObjectPresignRequest
}
import java.net.URL
import java.time.{Duration, Instant}

trait S3PresignedURLIssueService {
  def bucketName: String

  case class PresignedURL(url: URL, expireAt: Instant)

  def issueUploadPresignedRequestURL(key: String): IO[PresignedURL]
  def issueDownloadPresignedRequestURL(key: String): IO[PresignedURL]

}

class S3PresignedURLIssueServiceImpl(val bucketName: String)
    extends S3PresignedURLIssueService {
  def issueUploadPresignedRequestURL(key: String): IO[PresignedURL] = IO {
    val expireDuration = Duration.ofMinutes(10)
    val expireAt = Instant.now().plus(expireDuration)
    val presigner = S3Presigner.create()
    val putObjectRequest =
      PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .contentType("application/octet-stream")
        .build()
    val req = PutObjectPresignRequest
      .builder()
      .putObjectRequest(putObjectRequest)
      .signatureDuration(expireDuration)
      .build()
    val res = presigner.presignPutObject(req)
    PresignedURL(res.url(), expireAt)
  }
  def issueDownloadPresignedRequestURL(key: String): IO[PresignedURL] = IO {
    val expireDuration = Duration.ofMinutes(10)
    val expireAt = Instant.now().plus(expireDuration)
    val presigner = S3Presigner.create()
    val getObjectRequest =
      GetObjectRequest.builder().bucket(bucketName).key(key).build()
    val req = GetObjectPresignRequest
      .builder()
      .getObjectRequest(getObjectRequest)
      .signatureDuration(expireDuration)
      .build()
    val res = presigner.presignGetObject(req)
    PresignedURL(res.url(), expireAt)
  }
}
