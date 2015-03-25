#!/bin/bash
set -e
rm -rf *.zip
./gradlew clean check

EXIT_STATUS=0

exit $EXIT_STATUS
