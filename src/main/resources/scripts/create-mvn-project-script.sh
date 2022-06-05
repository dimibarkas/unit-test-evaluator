#!/bin/bash

while getopts p:m:x: flag
do
  # shellcheck disable=SC2220
  case "${flag}" in
    p)  path=${OPTARG};;
    m)  mutators=${OPTARG};;
    x)  x=${OPTARG};;
  esac
done

# copy pom template with needed dependencies
echo "copy pom.xml template"
value=$(<"$x")

# switch to temporary directory
cd -- "$path"|| exit

# create maven project from archetype
mvn archetype:generate -DgroupId=com.test.app -DartifactId=testapp -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

# switch to maven project directory
cd testapp || exit

# remove generated pom.xml file
rm pom.xml

# create new pom.xml file from template
echo "$value" > pom.xml

#optional, put custom mutators in pom.xml file
echo "$mutators"
