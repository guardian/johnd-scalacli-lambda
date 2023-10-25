import { GuApiLambda } from "@guardian/cdk";
import type { GuStackProps } from "@guardian/cdk/lib/constructs/core";
import { GuStack } from "@guardian/cdk/lib/constructs/core";
import type { App } from "aws-cdk-lib";
import {Runtime} from "aws-cdk-lib/aws-lambda";

export class JohndLambda extends GuStack {
  constructor(scope: App, id: string, props: GuStackProps) {
    super(scope, id, props);

    new GuApiLambda(this, "my-lambda", {
      fileName: "johnd-lambda.jar",
      handler: "Handler::handleRequest",
      runtime: Runtime.JAVA_17,
      monitoringConfiguration: {
        noMonitoring: true,
      },
      app: "johnd-lambda",
      api: {
        id: "johnd-lambda-api",
        description: "This is an example API",
      },
    });

  }

}
