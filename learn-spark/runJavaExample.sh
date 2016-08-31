#!/bin/bash

CURRENT_PATH=`pwd`
JAR_PATH=${CURRENT_PATH}/java-example/target/first-example-1.0-SNAPSHOT.jar

DEMO_TEST_FILE=${CURRENT_PATH}/java-example/src/test/resources/loremipsum.txt

OUTPUT_DIR=${CURRENT_PATH}/java-output

SPARK_HOME=${CURRENT_PATH}/spark-1.3.0-bin-hadoop2.4
SPARK_MASTER=local

if [ -d "${OUTPUT_DIR}" ]; then
  echo "Directory ${OUTPUT_DIR} already exists..."
  echo "Please delete the directory ${OUTPUT_DIR} first..."
  echo "Use: rm -rf ${OUTPUT_DIR}"
  exit -1
fi

${SPARK_HOME}/bin/spark-submit --class org.sparkexample.WordCount --master ${SPARK_MASTER} ${JAR_PATH} ${DEMO_TEST_FILE} ${OUTPUT_DIR}
