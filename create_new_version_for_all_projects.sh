#!/bin/bash
newVersion=$1;
if [ -z $newVersion ]
then
  echo "New version not set. Please pass new version as a first argument to this script.";
  exit 1;
fi

projects=(`ls | grep qualitas`)

for ((i=0; i < ${#projects[@]}; i++))
do
  echo "------------------------------------------------------------------------------";
  echo "Changing version of ${projects[i]} to $newVersion";
  cd ${projects[i]};
  mvn versions:set -DnewVersion=$newVersion
  if [ $? -ne 0 ]
  then
    echo "------------------------------------------------------------------------------";
    echo "Setting new version of ${projects[i]} failed, please see error message above.";
    echo "------------------------------------------------------------------------------";
    echo "";
    exit;
  fi
  cd ..
done

echo "------------------------------------------------------------------------------";
echo "Version change succeeded.";
echo "------------------------------------------------------------------------------";
echo "";
