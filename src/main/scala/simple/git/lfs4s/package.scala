package simple.git
import org.slf4j.{Logger, LoggerFactory}
import simple.git.lfs4s.service.S3PresignedURLIssueServiceImpl
package object lfs4s {
  val AWS_LAMBDA_RUNTIME_API = sys.env.getOrElse(
    "AWS_LAMBDA_RUNTIME_API",
    throw new Exception("AWS_LAMBDA_RUNTIME_API is not found")
  )
  val INVOCATION_ID = sys.env.getOrElse(
    "INVOCATION_ID",
    throw new Exception("INVOCATION_ID is not found")
  )
  val S3_BUCKET_NAME = sys.env.getOrElse(
    "S3_BUCKET_NAME",
    throw new Exception("S3_BUCKET_NAME is not found")
  )

  lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
  lazy val s3PresignedURLIssueService = new S3PresignedURLIssueServiceImpl(
    S3_BUCKET_NAME
  )
}
