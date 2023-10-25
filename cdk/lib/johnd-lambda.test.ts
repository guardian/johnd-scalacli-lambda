import { App } from "aws-cdk-lib";
import { Template } from "aws-cdk-lib/assertions";
import { JohndLambda } from "./johnd-lambda";

describe("The JohndLambda stack", () => {
  it("matches the snapshot", () => {
    const app = new App();
    const stack = new JohndLambda(app, "JohndLambda", { stack: "playground", stage: "TEST" });
    const template = Template.fromStack(stack);
    expect(template.toJSON()).toMatchSnapshot();
  });
});
