package simple.git.lfs4s

import io.circe.Json

case class Request(
    resource: String,
    path: String,
    httpMethod: String,
    headers: Map[String, String],
    multiValueHeaders: Json,
    queryStringParameters: Option[Json],
    multiValueQueryStringParameters: Option[Json],
    pathParameters: Map[String, String],
    stageVariables: Option[Json],
    requestContext: Map[String, String],
    body: Option[Json],
    isBase64Encoded: Boolean
)
