//> using toolkit latest
//> using dep com.amazonaws:aws-lambda-java-core:1.2.3
//> using dep com.amazonaws:aws-lambda-java-events:3.11.3

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger, RequestHandler}
import scala.jdk.CollectionConverters.*
import upickle.default.*

@main
def main(): Unit =
  val testInput = new APIGatewayProxyRequestEvent()
    .withBody("""{"name":"bodyJohn"}""")
    .withHeaders(Map("name" -> "headerJohn").asJava)
  println(Handler.handleRequest(testInput, dummyContext))

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

import sttp.client4.quick.*
import sttp.client4.Response
object GetUser {

  def main(args: Array[String]): Unit = {
    println("respnse is: " + response())
  }

  case class UserResponse(date_of_birth: String) derives ReadWriter

  def response(): UserResponse =
    val res = quickRequest
      .get(uri"https://random-data-api.com/api/v2/users")
      .send()
    read[UserResponse](res.body)

}

val dummyContext = new Context() {
  override def getAwsRequestId: String = ???

  override def getLogGroupName: String = ???

  override def getLogStreamName: String = ???

  override def getFunctionName: String = ???

  override def getFunctionVersion: String = ???

  override def getInvokedFunctionArn: String = ???

  override def getIdentity: CognitoIdentity = ???

  override def getClientContext: ClientContext = ???

  override def getRemainingTimeInMillis: Int = ???

  override def getMemoryLimitInMB: Int = ???

  override def getLogger: LambdaLogger = ???
}