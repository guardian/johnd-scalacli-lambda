#!/usr/bin/env bash

set -e
set -x

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd "$DIR/cdk"

yarn install --frozen-lockfile
yarn tsc
yarn lint
yarn test
yarn synth
