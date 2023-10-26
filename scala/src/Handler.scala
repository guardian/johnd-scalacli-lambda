//> using toolkit latest
//> using dep com.amazonaws:aws-lambda-java-core:1.2.3
//> using dep com.amazonaws:aws-lambda-java-events:3.11.3

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger, RequestHandler}
import scala.jdk.CollectionConverters.*
import upickle.default.*

object Handler extends RequestHandler[APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent]:
  override def handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent =
    val parsedInput = read[HandlerRequest](input.getBody)
    val response = handle(input.getHeaders.asScala.toMap, parsedInput)
    val output = write(response)
    new APIGatewayProxyResponseEvent()
      .withStatusCode(200)
      .withHeaders(Map("Content-type" -> "application/json").asJava)
      .withBody(output)

  def handle(headers: Map[String, String], handlerRequest: HandlerRequest): HandlerResponse =
    val user = GetUser.response()
    HandlerResponse("Hello " + headers.get("name") + " and " + handlerRequest.name + " date was " + user.date_of_birth)

case class HandlerResponse(message: String) derives ReadWriter

case class HandlerRequest(name: String) derives ReadWriter
