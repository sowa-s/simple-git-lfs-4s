package simple.git.lfs4s

import io.circe.generic.auto._
import io.circe.parser.parse
import org.scalatest.funsuite.AnyFunSuite

class RequestTest extends AnyFunSuite {
  test("Can parse Lambda requests for Proxy integration") {
    val request =
      """{
        |   "resource":"/test/{proxy+}",
        |   "path":"/test/batch",
        |   "httpMethod":"POST",
        |   "headers":{
        |      "content-type":"application/json",
        |      "Host":"hoge.execute-api.ap-northeast-1.amazonaws.com",
        |      "X-Amzn-Trace-Id":"Root=1-632ca795-11111111",
        |      "X-Forwarded-For":"127.0.0.1",
        |      "X-Forwarded-Port":"443",
        |      "X-Forwarded-Proto":"https"
        |   },
        |   "multiValueHeaders":{
        |      "content-type":[
        |         "application/json"
        |      ],
        |      "Host":[
        |         "hoge.execute-api.ap-northeast-1.amazonaws.com"
        |      ],
        |      "X-Amzn-Trace-Id":[
        |         "Root=1-632ca795-11111111"
        |      ],
        |      "X-Forwarded-For":[
        |         "127.0.0.1"
        |      ],
        |      "X-Forwarded-Port":[
        |         "443"
        |      ],
        |      "X-Forwarded-Proto":[
        |         "https"
        |      ]
        |   },
        |   "queryStringParameters":null,
        |   "multiValueQueryStringParameters":null,
        |   "pathParameters":{
        |      "proxy":"batch"
        |   },
        |   "stageVariables":null,
        |   "requestContext":{
        |      "resourceId":"z7vq30",
        |      "resourcePath":"/test/{proxy+}",
        |      "httpMethod":"POST",
        |      "extendedRequestId":"aaaaaaaa",
        |      "requestTime":"22/Sep/2022:18:21:09"
        |   },
        |   "body": "{\"hoge\": \"fuga\"}",
        |   "isBase64Encoded": false
        |}""".stripMargin

    parse(request).flatMap(_.as[Request]) match {
      case Right(value) => succeed
      case Left(value)  => fail(value)
    }
  }
}
