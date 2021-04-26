package com.qa.aws;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qa.reporting.LogfourJ;

public class SThree {

	private AmazonS3 s3;

	public String AWS_PROFILE_NAME = "prd";

	public SThree() {
		s3 = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(AWS_PROFILE_NAME)).build();
	}

	/**
	 * 
	 * @return s3 : by using s3 object we can perform operations on S3 Bucket
	 */
	public AmazonS3 getS3Object() {
		return s3;
	}

	public void setS3Object(AmazonS3 s3Object) {
		this.s3 = s3Object;
	}

	/**
	 * get list of available files in provided "S3 Bucket"
	 * 
	 * @param s3_bucket : provide the S3 Bucket Name, EX: odc-optout-published
	 * @param s3_path   : provide the complete path of the s3 object/file which we
	 *                  need to handle, EX: prod/datalogix/regbased/
	 */
	public List<String> getListOfFilesFromS3Bucket(String s3_bucket, String s3_path) {
		List<String> listOfFiles = new ArrayList<>();
		ObjectListing objects = null;
		try {
			objects = s3.listObjects(s3_bucket, s3_path);
			List<S3ObjectSummary> object_summary = objects.getObjectSummaries();
			while (objects.isTruncated()) {
				objects = s3.listNextBatchOfObjects(objects);
				object_summary.addAll(objects.getObjectSummaries());
			}
			for (S3ObjectSummary obj : object_summary) {
				if (obj.getKey() != null) {

					// System.out.println("object inside folder is ::"+obj.getKey());
					// System.out.println(getOutput(obj.getKey()));
					listOfFiles.add(getOutput(obj.getKey()));
					System.out.println(
							"FILE name is : " + obj.getKey() + " | Cretion date is : " + obj.getLastModified());

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		printS3Objects(listOfFiles);

		return listOfFiles;
	}

	/**
	 * get list of available files from "REG_BASED_OPTOUT_PARTNERS" Based on date
	 * provided
	 * 
	 * @param DateRequiredForRegBasedPartners : provide the date to pull the REG
	 *                                        BASED PARTNER OPTOUT Files
	 */

	public List<String> getListOfFilesFromS3Bucket(String DateRequiredForRegBasedPartners) {

		String REG_BASED_S3_BUCKET = "odc-optout-published";
		String REG_BASED_S3_PATH = "prod/datalogix/regbased/";
		List<String> listOfRegBasedOptouFiles = new ArrayList<>();

		ObjectListing objects = null;

		try {
			String finalS3Path = REG_BASED_S3_PATH + DateRequiredForRegBasedPartners + "/";
			objects = s3.listObjects(REG_BASED_S3_BUCKET, finalS3Path);
			List<S3ObjectSummary> object_summary = objects.getObjectSummaries();

			while (objects.isTruncated()) {
				objects = s3.listNextBatchOfObjects(objects);
				object_summary.addAll(objects.getObjectSummaries());
			}

			System.out.println("Total reg based optout files in the garden hose as on " + getTodaysDate() + " -> "
					+ object_summary.size());

			for (S3ObjectSummary obj : object_summary) {
				if (obj.getKey() != null) {

					// System.out.println("object inside folder is ::"+obj.getKey());
					// System.out.println(getOutput(obj.getKey()));
					listOfRegBasedOptouFiles.add(getOutput(obj.getKey()));
					System.out.println(
							"FILE name is : " + obj.getKey() + " | Cretion date is : " + obj.getLastModified());

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// printS3Objects(regBasedOptoutFies);

		return listOfRegBasedOptouFiles;
	}

	/**
	 * This method is created to return DATE to deal with REG BASED PARTNERS
	 * @return Date: in the "yyyy-MM-dd" format to pull list of files from REG Based Partners
	 */
	public String getTodaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}

	public String getOutput(String input) {
		String output = null;
		String[] words = input.split("/");

		output = words[words.length - 1];
		return output;
	}

	/**
	 * 
	 * @param s3ObjectSummary : to deal with s3 object details 
	 */
	public void printS3Objects(List<String> s3ObjectSummary) {
		// print Data Logix file only
		s3ObjectSummary.forEach(s -> {
			System.out.println(s);
			if (s.equalsIgnoreCase("1157.csv"))
				System.out.println("Reg Based optout File for Data Logix : " + s);
			else {
				System.out.println("File available in S3 Is : " + s);
			}
		});
	}
	
	/**
	 * By using uploadFileToS3 we can upload file/object into given S3 Bucket path
	 * @param s3_bucketName : provide complete s3 bucket path to upload file, Ex: "dlx-prod-core-shared/xas/scs_qa_team/"
	 * @param key : key as like file/object name will display under given s3 bucket, Ex: Provide file name as key name "Testcase01_Mar152021"
	 * @param local_Dir_Path_Including_File_Name : provide the File name including local directory path, Ex: "/Users/satishnaiduparimi/Desktop/s3uploadDir/Testcase01_Mar152021"
	 * @reference https://stackoverflow.com/questions/21229493/how-to-store-a-downloaded-file-object-from-s3-to-a-local-directory/41206245
	 */
	
	public void uploadFileToS3(String s3_bucketName, String key, String local_Dir_Path_Including_File_Name) {
		try {
			s3.putObject(s3_bucketName, key, new File(local_Dir_Path_Including_File_Name));
		}catch(Exception e) {
			System.out.print("Error in uploading file to Given S3 Bucket : "+s3_bucketName);
			System.out.print("Error message is : "+e.getMessage());
		}
		
	}
	
	/**
     * By using this method we can download file/object from S3 bucket and save it into local directory in required format
     * @param s3_bucketName , provide complete s3 bucket path to download file/object, Ex: "dlx-prod-core-shared/xas/scs_qa_team/"
     * @param key , key as like file/object name will display under given s3 bucket , Ex: provide file name displaying under s3 bucket
     * @param local_Dir_Path_Including_File_Name, Ex: "/Users/satishnaiduparimi/Desktop/s3download/file1.csv"
     * @reference: https://stackoverflow.com/questions/21229493/how-to-store-a-downloaded-file-object-from-s3-to-a-local-directory/41206245
     */
    public void downloadFileFromS3ToLocalDirectory(String s3_bucketName, String key, String local_Dir_Path_Including_File_Name) {
        try {
            s3.getObject(new GetObjectRequest(s3_bucketName, key), new File(local_Dir_Path_Including_File_Name));

        }catch(Exception e) {
            System.out.print("Error in downloading file to local directory..!");
            System.out.print("Error message is : "+e.getMessage());
        }
    }
    
    public void verifyCountOfObject(List<String> outputFiles, String testName) {
		
		 if(outputFiles.size() == 0 ) {
			 System.out.println("No Files / Objects are found in given S3 Bucket "+ testName);
			 LogfourJ.getLogger().info("No Files / Objects are found in given S3 Bucket :"+ testName);
		 }else {
			 System.out.print("Files or Objects found in given S3 Bucket");
			 LogfourJ.getLogger().info("Files or Objects found in given S3 Bucket");
			 	 
		 }
			
	}
	
}

