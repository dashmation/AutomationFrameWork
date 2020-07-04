package com.qa.test.mySQL;

import java.sql.*;

public class AccessDB {
	
	public void connectToDB() throws Exception {
		/*
		 * Loading the Required JDBC Driver Class
		 */
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		/*
		 * Creating Connection To Database
		 */
		Connection connection = DriverManager.getConnection("DataBaseURL", "username", "password");
		
		/*
		 * Executing Query And Fetching The Result
		 */
		Statement statement = connection.createStatement();
		String sqlQuery="";
		ResultSet result = statement.executeQuery(sqlQuery);
		result.getString("columnLableName");
	}
}
