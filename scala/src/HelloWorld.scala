//> using toolkit latest
//> using dep com.amazonaws:aws-lambda-java-core:1.2.3
//> using dep com.amazonaws:aws-lambda-java-events:3.11.3

import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger, RequestHandler}
import scala.jdk.CollectionConverters.*

@main
def main():Unit =
  val jsonString = """{"name": "Peter", "age": 13, "pets": ["Toolkitty", "Scaniel"]}"""
  val json: ujson.Value  = ujson.read(jsonString)
  println(json("name").str)
  val testInput = new APIGatewayProxyRequestEvent()
    .withBody("cheese")
  println(Handler.handleRequest(testInput, dummyContext))

object Handler extends RequestHandler[APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent]:
  override def handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent =
    new APIGatewayProxyResponseEvent()
      .withStatusCode(200)
      .withHeaders(Map("Content-type" -> "text/plain").asJava)
      .withBody("Hello " + input.getBody)


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