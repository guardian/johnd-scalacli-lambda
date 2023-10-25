#!/usr/bin/env -S scala-cli shebang
//> using toolkit latest

import os.{CommandResult, Shellable}

val createRole = List[Shellable](
  "aws",
  "iam",
  "create-role",
  "--role-name", "johnd-lambda-role",
  "--assume-role-policy-document",
  """{
    |"Version": "2012-10-17",
    |"Statement": [
    |  {
    |    "Effect": "Allow",
    |    "Principal": {"Service": "lambda.amazonaws.com"},
    |    "Action": "sts:AssumeRole"
    |  }
    |]}
    |""".stripMargin,
  "--profile",
  "developerPlayground"
)

val packageLambda = List[Shellable](
  "scala-cli",
  "--power",
  "package",
  ".",
  "-o",
  "lambda.jar",
  "--assembly",
  "--preamble=false",
  "--force" //overwrite
)

def createLambda(arn: String) = List[Shellable](
  "aws",
  "lambda",
  "create-function",
  "--function-name",
  "johnd-function",
  "--runtime",
  "java17",
  "--handler",
  "Handler::handleRequest",
  "--role",
  arn,
  "--zip-file",
  "fileb://lambda.jar",
  "--profile",
  "developerPlayground"
)

val existingRoleArn = Some("arn:aws:iam::702972749545:role/johnd-lambda-role")

os.proc(packageLambda.toArray: _*).call().out.lines().foreach(println)

val arn = existingRoleArn.getOrElse {
  val createRoleOutput = os.proc(createRole.toArray: _*).call().out.lines().mkString("\n")
  println(createRoleOutput)
  Thread.sleep(10*1000)// role takes time to be available, else "The role defined for the function cannot be assumed by Lambda."
  ujson.read(createRoleOutput)("Role")("Arn").str
}

println("Arn is: <" + arn + ">")

os.proc(createLambda(arn).toArray: _*).call().out.lines().foreach(println)
