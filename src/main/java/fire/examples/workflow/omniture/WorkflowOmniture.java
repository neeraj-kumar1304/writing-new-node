package fire.examples.workflow.omniture;


import fire.context.JobContext;
import fire.context.JobContextImpl;
import fire.nodes.dataset.NodeDatasetStructured;
import fire.nodes.etl.NodeJoin;
import fire.nodes.util.NodePrintFirstNRows;
import fire.util.spark.CreateSparkContext;
import fire.workflowengine.ConsoleWorkflowContext;
import fire.workflowengine.DatasetType;
import fire.workflowengine.Workflow;
import fire.workflowengine.WorkflowContext;
import org.apache.spark.api.java.JavaSparkContext;

public class WorkflowOmniture {

    //--------------------------------------------------------------------------------------

    public static void main(String[] args) {

        // create spark context
        JavaSparkContext ctx = CreateSparkContext.create(args);
        // create workflow context
        WorkflowContext workflowContext = new ConsoleWorkflowContext();
        // create job context
        JobContext jobContext = new JobContextImpl(ctx, workflowContext);

        try {
            omniture(jobContext);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        // stop the context
        ctx.stop();
    }

    //--------------------------------------------------------------------------------------

    // omniture workflow
    private static void omniture(JobContext jobContext) throws Exception {

        Workflow wf = new Workflow();

        // products node
        NodeDatasetStructured products = new NodeDatasetStructured(1, "products node", "data/omniture/products.tsv", DatasetType.CSV, "\t",
                "url category", "string string",
                "text text");
      //  products.filterLinesContaining = "category";
        wf.addNode(products);


        // print first 2 rows
        NodePrintFirstNRows printFirstNRows1 = new NodePrintFirstNRows(2, "print first rows", 20);
       wf.addLink(products, printFirstNRows1);


        // users node
        NodeDatasetStructured users = new NodeDatasetStructured(3, "users node", "data/omniture/users.tsv", DatasetType.CSV, "\t",
                "SWID BIRTH_DT GENDER_CD", "string string string",
                "text text text");
        products.filterLinesContaining = "BIRTH_DT";
        wf.addNode(users);



        // print first 2 rows
        NodePrintFirstNRows printFirstNRows2 = new NodePrintFirstNRows(4, "print first rows", 20);
        wf.addLink(users, printFirstNRows2);

        NodeDatasetStructured omniture = new NodeDatasetStructured(7, "join node", "data/omniture/omniture.tsv", DatasetType.CSV, "\t",
                "C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 C10 C11 url C13 C14 C15 C16 C17 C18 C19 C20 C21 C22 C23 C24 C25 C26 C27 C28 C29 C30 C31 C32 C33 C34 C35 C36 C37 C38 C39 C40 C41 C42 C43 C44 C45 C46 C47 C48 C49 C50 C51 C52 C53 C54 C55 C56 C57 C58 C59 C60 C61 C62 C63 C64 C65 C66 C67 C68 C69 C70 C71 C72 C73 C74 C75 C76 C77 C78 C79 C80 C81 C82 C83 C84 C85 C86 C87 C88 C89 C90 C91 C92 C93 C94 C95 C96 C97 C98 C99 C100 C101 C102 C103 C104 C105 C106 C107 C108 C109 C110 C111 C112 C113 C114 C115 C116 C117 C118 C119 C120 C121 C122 C123 C124 C125 C126 C127 C128 C129 C130 C131 C132 C133 C134 C135 C136 C137 C138 C139 C140 C141 C142 C143 C144 C145 C146 C147 C148 C149 C150 C151 C152 C153 C154 C155 C156 C157 C158 C159 C160 C161 C162 C163 C164 C165 C166 C167 C168 C169 C170 C171 C172 ", "string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string",
                "text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text");
        wf.addNode(omniture);


        NodeDatasetStructured employee = new NodeDatasetStructured(80, "join node", "data/omniture/Employee.csv", DatasetType.CSV, ",",
                "Eid Ename Did", "string string string",
                "text text text");
        wf.addNode(employee);


        NodeDatasetStructured Departement = new NodeDatasetStructured(81, "join node", "data/omniture/Department.csv", DatasetType.CSV, ",",
                "Did Dname", "string string",
                "text text");
        wf.addNode(Departement);




        NodePrintFirstNRows printFirstNRows3 = new NodePrintFirstNRows(4, "print first rows", 20);
        wf.addLink(users, printFirstNRows3);
        // join node
        NodeJoin join = new NodeJoin(5, "join node",  "url");
        wf.addLink(products, join);
        wf.addLink(omniture, join);


        // join node
        NodeJoin join1 = new NodeJoin(82, "join node1",  "Did");
        wf.addLink(employee, join1);
        wf.addLink(Departement, join1);

        // print first 2 rows
      NodePrintFirstNRows printFirstNRows5 = new NodePrintFirstNRows(83, "print first rows", 20);
        wf.addLink(join, printFirstNRows5);

        // execute the workflow
        wf.execute(jobContext);

    }

}
