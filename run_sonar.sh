#!/bin/bash

mvn -Djacoco.agent.path=~/.m2/repository/org/jacoco/org.jacoco.agent/0.5.6.201201232323/org.jacoco.agent-0.5.6.201201232323-runtime.jar -Djacoco.file.path=/tmp/qualitas-jacoco -f qualitas/pom.xml -q clean install

mvn -Dsonar.jacoco.itReportPath=/tmp/qualitas-jacoco -f qualitas/pom.xml -q generate-sources sonar:sonar
