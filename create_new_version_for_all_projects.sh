#!/bin/bash
newVersion=$1;
if [ -z $newVersion ]
then
  echo "New version not set. Please pass new version as a first argument to this script.";
  exit 1;
fi

# root project

projects=(qualitas)

for ((i=0; i < ${#projects[@]}; i++))
do
  echo "------------------------------------------------------------------------------";
  echo "Changing version of ${projects[i]} to $newVersion";
  echo "------------------------------------------------------------------------------";
  cd ${projects[i]};
  mvn -q -P fastinstall versions:set -DnewVersion=$newVersion
  if [ $? -ne 0 ]
  then
    echo "------------------------------------------------------------------------------";
    echo "Setting new version of ${projects[i]} failed, please see error message above.";
    echo "------------------------------------------------------------------------------";
    echo "";
    exit;
  fi
  mvn -q -P fastinstall install
  cd ..
done

# parent project

projects=('qualitas' 'qualitas-buildtools' 'qualitas-parent' 'qualitas-utils-zip' 'qualitas-utils-xslt' 'qualitas-utils-dom' 'qualitas-engines-api-configuration' 'qualitas-engines-api-core' 'qualitas-engines-api-deployment' 'qualitas-engines-api-factory' 'qualitas-engines-api-instrumentation' 'qualitas-engines-api-invocation' 'qualitas-engines-api-validation' 'qualitas-engines-api-resolution' 'qualitas-engines-ode-core' 'qualitas-engines-ode-deployment' 'qualitas-engines-ode-factory' 'qualitas-engines-ode-instrumentation' 'qualitas-engines-ode-invocation' 'qualitas-engines-ode-validation' 'qualitas-engines-ode-resolution' 'qualitas-engines-ode-spring' 'qualitas-integration-api' 'qualitas-integration-amqp' 'qualitas-services-security-spring' 'qualitas-execution-monitor-webservice' 'qualitas-internal-bpel-instrumentation' 'qualitas-internal-bpel-spring' 'qualitas-internal-execution-monitor' 'qualitas-internal-installation' 'qualitas-webapp');

for ((i=0; i < ${#projects[@]}; i++))
do
  echo "------------------------------------------------------------------------------";
  echo "Changing parent version of ${projects[i]} to $newVersion";
  echo "------------------------------------------------------------------------------";
  cd ${projects[i]};
  mvn -q -P fastinstall versions:update-parent clean -DparentVersion=$newVersion -DallowSnapshots=true
  if [ $? -ne 0 ]
  then
    echo "------------------------------------------------------------------------------";
    echo "Setting new version of ${projects[i]} failed, please see error message above.";
    echo "------------------------------------------------------------------------------";
    echo "";
    exit;
  fi
  mvn -q -P fastinstall install  
  cd ..
done

echo "------------------------------------------------------------------------------";
echo "Version change succeeded.";
echo "------------------------------------------------------------------------------";
echo "";
