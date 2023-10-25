import "source-map-support/register";
import { GuRoot } from "@guardian/cdk/lib/constructs/root";
import { JohndLambda } from "../lib/johnd-lambda";

const app = new GuRoot();
new JohndLambda(app, "JohndLambda-euwest-1-CODE", { stack: "playground", stage: "CODE", env: { region: "eu-west-1" } });
