#!/bin/bash
projects=('qualitas' 'qualitas-buildtools' 'qualitas-parent' 'qualitas-utils-zip' 'qualitas-utils-xslt' 'qualitas-engines-api-configuration' 'qualitas-engines-api-core' 'qualitas-engines-api-deployment' 'qualitas-engines-api-factory' 'qualitas-engines-api-instrumentation' 'qualitas-engines-api-invocation' 'qualitas-engines-api-validation' 'qualitas-engines-api-resolution' 'qualitas-engines-ode-core' 'qualitas-engines-ode-deployment' 'qualitas-engines-ode-factory' 'qualitas-engines-ode-instrumentation' 'qualitas-engines-ode-invocation' 'qualitas-engines-ode-validation' 'qualitas-engines-ode-resolution' 'qualitas-engines-ode-spring' 'qualitas-integration-api' 'qualitas-integration-amqp' 'qualitas-services-security-spring' 'qualitas-internal-installation' 'qualitas-webapp');
for ((i=0; i < ${#projects[@]}; i++))
do
  echo "";
  echo "------------------------------------------------------------------------------";
  echo "Installing ${projects[i]}";
  echo "------------------------------------------------------------------------------";
  echo "";
  cd ${projects[i]};
  mvn clean compiler:compile install
  if [ $? -ne 0 ]
  then
    echo "";
    echo "------------------------------------------------------------------------------";
    echo "Installation of ${projects[i]} failed, please see error message above.";
    echo "------------------------------------------------------------------------------";
    echo "";
    exit;
  fi
  cd ..
done

echo "";
echo "------------------------------------------------------------------------------";
echo "Installation succeeded.";
echo "------------------------------------------------------------------------------";
echo "";


