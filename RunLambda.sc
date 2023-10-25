#!/usr/bin/env -S scala-cli shebang
//> using toolkit latest

import os.{CommandResult, Shellable}

val updateLambda = List[Shellable](
  "aws",
  "lambda",
  "update-function-code",
  "--function-name",
  "johnd-function",
  "--zip-file",
  "fileb://foo.jar",
  "--profile",
  "developerPlayground"
)
//aws lambda invoke --function-name my-function out --log-type Tail
val executeLambda = List[Shellable](
  "aws",
  "lambda",
  "invoke",
  "--function-name",
  "johnd-function",
  "--cli-binary-format",
  "raw-in-base64-out",
  "--payload",
  """"John"""",
  "output",
  "--log-type",
  "Tail",
  "--profile",
  "developerPlayground"
)

//os.proc(updateLambda.toArray: _*).call().out.lines().foreach(println)

os.proc(executeLambda.toArray: _*).call().out.lines().foreach(println)
