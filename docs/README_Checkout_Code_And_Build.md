## Building

### Check out the code

Check out the code with : **git clone https://github.com/sparkflows/writing-new-node.git**

### Install the Fire jar to the local maven repository

Writing new Node depends on the Fire jar file. The Fire jar file provides the parent class for any new Node. Use the below commands to install the fire jar in your local maven repo.

    mvn install:install-file -Dfile=fire-spark_1_6-core-1.4.2.jar -DgroupId=fire  -DartifactId=fire-spark_1_6-core  -Dversion=1.4.2 -Dpackaging=jar
    
### Build with Maven

    mvn package


# Running the workflow on a Spark Cluster

Use the command below to load example data onto HDFS. It is then used by the example Workflow.

	hadoop fs -put data

Below is the command to execute the example Workflow on a Spark cluster. 

Executors with 1G and 1 vcore each have been specified in the commands. The parameter **'cluster'** specifies that we are running the workflow on a cluster as against locally. This greatly simplifies the development and debugging within the IDE by setting its value to **'local'** or not specifying it.

	spark-submit --class fire.workflows.examples.WorkflowTest --master yarn-client --executor-memory 1G  --num-executors 1  --executor-cores 1  target/writing-new-node-1.4.2-jar-with-dependencies.jar cluster


## Jar files

Building this repo generates the following jar files:

	target/writing-new-node-1.4.2.jar
	target/writing-new-node-1.4.2-jar-with-dependencies.jar

The details for coding a New Node is here : https://github.com/sparkflows/writing-new-node/blob/master/CreatingNewNodes.md


## Display the example Node in fire-ui and run it from there

New nodes written can be made visible in the Sparkflows UI. Thus, the users can start using them immediately.

* Copy the **writing-new-node-1.4.2.jar** to **fire-lib** directory of the sparkflows install
* Copy **testprintnrows.json** to the **nodes** directory under sparkflows install
* Restart fire-ui
* **TestPrintNRows** node would now be visible in the workflow editor window and you can start using it.

