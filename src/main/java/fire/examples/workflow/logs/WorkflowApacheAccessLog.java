/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fire.examples.workflow.logs;

import fire.context.JobContext;
import fire.context.JobContextImpl;
import fire.nodes.logs.NodeApacheFileAccessLog;
import fire.nodes.util.NodePrintFirstNRows;
import fire.nodes.sessionize.NodeSessionize;
import fire.util.spark.CreateSparkContext;
import fire.workflowengine.ConsoleWorkflowContext;
import fire.workflowengine.Workflow;
import fire.workflowengine.WorkflowContext;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by jayantshekhar
 */
public class WorkflowApacheAccessLog {

    //--------------------------------------------------------------------------------------

    public static void main(String[] args) {

        // create spark context
        JavaSparkContext ctx = CreateSparkContext.create(args);
        // create workflow context
        WorkflowContext workflowContext = new ConsoleWorkflowContext();
        // create job context
        JobContext jobContext = new JobContextImpl(ctx, workflowContext);

        execute(jobContext);

        // stop the context
        ctx.stop();
    }

    //--------------------------------------------------------------------------------------

    private static void execute(JobContext jobContext) {

        Workflow wf = new Workflow();

        // pdf node
        NodeApacheFileAccessLog log = new NodeApacheFileAccessLog(1, "apache log node", "data/apacheaccesslog_new.log");
        wf.addNode(log);

        // print first 3 rows node
        NodePrintFirstNRows nodePrintFirstNRows = new NodePrintFirstNRows(2, "print first 3 rows", 50);
        wf.addLink(log, nodePrintFirstNRows);

        // sessionize
        NodeSessionize nodeSessionize = new NodeSessionize(3, "sessionize", "timestamp", "ipAddress");
        wf.addLink(nodePrintFirstNRows, nodeSessionize);


        // print first 3 rows node
        nodePrintFirstNRows = new NodePrintFirstNRows(4, "print first 3 rows", 50);
        wf.addLink(nodeSessionize, nodePrintFirstNRows);

        wf.execute(jobContext);
    }

}
