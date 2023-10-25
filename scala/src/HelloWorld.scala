//> using toolkit latest
//> using dep com.amazonaws:aws-lambda-java-core:1.2.3

import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger, RequestHandler}
// > using dep com.amazonaws:aws-lambda-java-events:3.11.3

@main
def main():Unit =
  val jsonString = """{"name": "Peter", "age": 13, "pets": ["Toolkitty", "Scaniel"]}"""
  val json: ujson.Value  = ujson.read(jsonString)
  println(json("name").str)
  println(Handler.handleRequest("cheese", new Context() {
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
  }))

object Handler extends RequestHandler[String, String]:
  override def handleRequest(input: String, context: Context): String =
    s"Hello, $input!"
