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
  if [ ${projects[i]} == "qualitas" -o ${projects[i]} == "qualitas-parent" ]
  then
    echo "Skipping ${projects[i]}";
    continue;
  fi
  echo "------------------------------------------------------------------------------";
  echo "Changing version of ${projects[i]} to $newVersion";
  echo "------------------------------------------------------------------------------";
  cd ${projects[i]};
  mvn -q versions:set -DnewVersion=$newVersion -DallowSnapshots=true
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

cd qualitas-buildtools
mvn -q install
cd ..

projects=(qualitas-parent qualitas)

for ((i=0; i < ${#projects[@]}; i++))
do
  echo "------------------------------------------------------------------------------";
  echo "Changing version of ${projects[i]} to $newVersion";
  echo "------------------------------------------------------------------------------";
  cd ${projects[i]};
  mvn -q versions:set install -DnewVersion=$newVersion
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

projects=(`ls | grep qualitas`)

for ((i=0; i < ${#projects[@]}; i++))
do
  echo "------------------------------------------------------------------------------";
  echo "Changing parent version of ${projects[i]} to $newVersion";
  echo "------------------------------------------------------------------------------";
  cd ${projects[i]};
  mvn -q versions:update-parent -DallowSnapshots=true
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
