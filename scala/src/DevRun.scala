
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