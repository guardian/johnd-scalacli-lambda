import { GuApiLambda } from "@guardian/cdk";
import type { GuStackProps } from "@guardian/cdk/lib/constructs/core";
import { GuStack } from "@guardian/cdk/lib/constructs/core";
import type { App } from "aws-cdk-lib";
import {Runtime} from "aws-cdk-lib/aws-lambda";
import type {CfnFunction} from "aws-cdk-lib/aws-sam";

export class JohndLambda extends GuStack {
  constructor(scope: App, id: string, props: GuStackProps, buildNumber: string) {
    super(scope, id, props);

    const lambda = new GuApiLambda(this, "my-lambda", {
      fileName: `johnd-lambda-${buildNumber}.jar`,
      handler: "Handler::handleRequest",
      runtime: Runtime.JAVA_17,
      monitoringConfiguration: {
        noMonitoring: true,
      },
      app: "johnd-lambda",
      api: {
        id: "johnd-lambda-api",
        description: `This is an example AAAAPI ${buildNumber}`,
      },
      functionName: `johnd-lambda-${this.stage}`,
      enableVersioning: true,
    });
    (lambda.node.defaultChild as CfnFunction).addPropertyOverride("SnapStart", { ApplyOn: "PublishedVersions" });

  }

}
