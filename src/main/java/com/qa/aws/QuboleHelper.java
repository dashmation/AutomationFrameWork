package com.qa.aws;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.qa.reporting.LogfourJ;
import com.qa.utility.CommonUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class QuboleHelper {
	 /* Qubole End Points */
    private String QUBOLE_ENDPOINT = "https://api.qubole.com";

    private String QUBOLE_API_VERSION = "api/v1.2";

    protected String Qubole_Job_ID = null;

    private String CREATE_QUBOLE_JOB_API = QUBOLE_ENDPOINT + "/" + QUBOLE_API_VERSION + "/commands";


    //private String QUBOLE_JOB_STATUS_AND_RESULTS_API = CREATE_QUBOLE_JOB_API + "/" + Qubole_Job_ID + "/status_with_results";




    /**
     * Qubole Account: PROD_PII_Core_Consumer_Token
     */
    public String PROD_PII_CORE_Consumer_Token = CommonUtils.getValue("PROD_PII_CORE_Consumer_Token");


    /** Qubole Job Input Files */

    public String SPARK_JOB_INPUT_FILE_PATH = System.getProperty("user.dir") + "/TestCases/testdata/DownstreamOptout/qubole_jobs/";

    public enum QuboleJobType {SPARK_COMMAND, HIVE_QUERY, PRESTO_QUERY, PIG_QUERY, HADOOP_JOB, REFRESH_TABLE}

    public enum Qubole_Accounts {PROD_PII_CORE_Consumer, PROD_TECH_CORE_CONSUMER, PROD_ANALYTICS_CORE_CONSUMER, DEV_TECH_CORE_SHARED, DEV_TECH_CORE_CONSUMER, DEV_PII_CORE_CONSUMER }

    /**
     *
     * @param QuboleJobType
     * @param QuboleJobInput
     * @return Qubole_Job_ID
     * @Purpose By using createQuboleJob method, we can able to create Spark, Hive etc jobs by providing required input data
     */
    public String createQuboleJob(QuboleJobType QuboleJobType, String QuboleJobInput, String quboleToken){

        RestAssured.baseURI = CREATE_QUBOLE_JOB_API;
        RequestSpecification request = RestAssured.given();

        JsonObject jsonBody = new JsonObject();

        Response response;

        String responseString = null;

        JSONObject jsonObj;

        switch (QuboleJobType){
            case SPARK_COMMAND:
                System.out.println("Spark_Command Job is creating.!!");
                LogfourJ.getLogger().info("Spark_Command Job is creating!!");
                /*RestAssured.baseURI = api_URI;
                RequestSpecification request = RestAssured.given(); */

                // Add required Headers
                request.header("Content-Type", "application/json");
                request.header("Accept", "application/json");
                request.header("X-AUTH-TOKEN", quboleToken);

                // JSON Body construction
                //JsonObject jsonBody = new JsonObject();
                jsonBody.addProperty("command_type", "SparkCommand");

                jsonBody.addProperty("program", QuboleJobInput);

                jsonBody.addProperty("label", "ids_spark_prod1_qa");

                jsonBody.addProperty("arguments", "--driver-memory 45G --executor-memory 20G --max-executors 100 --executor-cores 4");

                jsonBody.addProperty("language", "scala");

                jsonBody.addProperty("name", "new-data TC coel-optout test case 01");

                request.body(jsonBody.toString());

                // API Call
                response = request.post(CREATE_QUBOLE_JOB_API);

                // Response
                responseString = response.body().asString();
                System.out.println(responseString);

                jsonObj = new JSONObject(responseString);
                String Spark_Job_Id = jsonObj.get("id").toString();
                System.out.println("Spark_Job_id : "+Spark_Job_Id);
                Qubole_Job_ID = Spark_Job_Id;
                break;

            case HIVE_QUERY:
                System.out.println("Hive_Query Job is creating.!!");
                LogfourJ.getLogger().info("Hive_Query Job is creating.!!");
                // Add required Headers
                request.header("Content-Type", "application/json");
                request.header("Accept", "application/json");
                request.header("X-AUTH-TOKEN", quboleToken);

                // JSON Body construction
                //JsonObject jsonBody = new JsonObject();
                jsonBody.addProperty("command_type", "HiveCommand");
                jsonBody.addProperty("query", QuboleJobInput);

                request.body(jsonBody.toString());

                // API Call
                response = request.post(CREATE_QUBOLE_JOB_API);

                // Response
                responseString = response.body().asString();
                jsonObj = new JSONObject(responseString);
                String Hive_Query_Job_Id = jsonObj.get("id").toString();
                //System.out.println("Hive_Query_Job_id : "+Hive_Query_Job_Id);
                LogfourJ.getLogger().info("Hive_Query_Job_id : "+Hive_Query_Job_Id);
                Qubole_Job_ID = Hive_Query_Job_Id;

                break;
                

            case HADOOP_JOB:
                System.out.println("Hadoop Job is creating.!!");
                Qubole_Job_ID = null;
                break;


            case PIG_QUERY:
                System.out.println("PIG_Query Job is creating.!!");
                Qubole_Job_ID = null;
                break;


            case PRESTO_QUERY:
                System.out.println("PRESTO_Query Job is creating.!!");
                Qubole_Job_ID = null;
                break;


            case REFRESH_TABLE:
                System.out.println("Refresh_Table Job is creating.!!");
                Qubole_Job_ID = null;
                break;

            default:
                System.out.println("No Qubole job Type is matching ~: "+QuboleJobType);
        }
        return  Qubole_Job_ID;
    }

    /**
     *
     * @param commandID
     * @param qubole_token
     * @return state
     * @Description Get current status of submitted Qubole Job
     */
    public String get_Submitted_Job_State(String commandID, String qubole_token ) {

        String QUBOLE_JOB_STATUS_AND_RESULTS_API = CREATE_QUBOLE_JOB_API + "/" + commandID + "/status_with_results";

        RestAssured.baseURI = QUBOLE_JOB_STATUS_AND_RESULTS_API;
        RequestSpecification request = RestAssured.given();

        // Add required Headers
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        request.header("X-AUTH-TOKEN", qubole_token);


        // API Call
        Response response = request.get(QUBOLE_JOB_STATUS_AND_RESULTS_API);

        // Response
        String responseString = response.body().asString();

        JSONObject jsonObj = new JSONObject(responseString);
        //System.out.println("Results are :  "+jsonObj.get("results"));

        String state  = jsonObj.getString("status").toString();

        System.out.println("State of the submitted job is : "+state);

        return state;


    }


    /**
     * @param commandID
     * @param qubole_token
     * @Description Get Results for Qubole job
     */

    public String[] get_Results_For_Submitted_Job(String commandID, String qubole_token ) {

        String QUBOLE_JOB_STATUS_AND_RESULTS_API = CREATE_QUBOLE_JOB_API + "/" + commandID + "/status_with_results";

        RestAssured.baseURI = QUBOLE_JOB_STATUS_AND_RESULTS_API;
        RequestSpecification request = RestAssured.given();

        // Add required Headers
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        request.header("X-AUTH-TOKEN", qubole_token);


        // API Call
        Response response = request.get(QUBOLE_JOB_STATUS_AND_RESULTS_API);

        // Response
        String responseString = response.body().asString();

        JSONObject jsonObj = new JSONObject(responseString);
        //System.out.println("Results are :  "+jsonObj.get("results"));

        String[] result = getResults(jsonObj.getString("results").toString());

        System.out.println("results after processing..");

        for(String s : result) {
            System.out.println("result is : " +s);
        }
        return result;

    }

    /**
     *
     * @param resultStr
     * @return
     * @Description
     */
    private String[] getResults(String resultStr) {

        String [] resultArr = resultStr.split(" ");

        return resultArr;
    }

    public String getSparkJobInput(String inputFileName) throws IOException {
        String inputFilePath = System.getProperty("user.dir")+"/src/test/resources/testdata/DownStream_Optout/xas/"+inputFileName;
        FileInputStream fis = new FileInputStream(inputFilePath);
        String data = IOUtils.toString(fis, "UTF-8");
        return data;
    }

    //TO-DO : Get failure logs / errors for failed job

}
