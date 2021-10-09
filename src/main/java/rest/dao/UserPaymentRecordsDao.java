/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.dao;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import rest.Beans.UserPaymentRecords;
import rest.connection.MyConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserPaymentRecordsDao {
   public String getRecords(String flatNumber,String type) {
		MyConnection myConnection = new MyConnection();
		Connection connection = myConnection.getConnection();
		ArrayList<UserPaymentRecords> userRecords=new ArrayList<UserPaymentRecords>();
		String jsonString = "";
		JsonObjectBuilder res = Json.createObjectBuilder();
                GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
               // System.out.println(flatNumber+" "+type+" inside"+connection);
		if (connection != null && type.equals("yearly")) {
                    //System.out.println(flatNumber+" "+type+" inside");
			try {
				PreparedStatement pstmt = null;
				String query = null;
				query = "Select r.recordId,r.flatNumber,u.firstName,u.lastName,sum(r.amount),year(r.dateOfPay) from Records r inner join Users u on u.flatNumber=r.flatNumber where r.flatNumber="+flatNumber+" group by year(r.dateOfPay);";
				pstmt = connection.prepareStatement(query);
//				pstmt.setString(1, username);
//				pstmt.setString(2, password);
				ResultSet result = pstmt.executeQuery(query);
				while (result.next()) {
					UserPaymentRecords userPaymentRecords = new UserPaymentRecords();
                                        userPaymentRecords.setRecordNumber(result.getString(1));
                                        userPaymentRecords.setFlatNumber(result.getString(2));
                                        userPaymentRecords.setFirstName(result.getString(3));
                                        userPaymentRecords.setLastName(result.getString(4));
                                        userPaymentRecords.setAmount(result.getString(5));
                                        userPaymentRecords.setDate(result.getString(6));
                                        userRecords.add(userPaymentRecords);
					
				  
				} 
                                connection.close();

			}

			catch (SQLException e) {
				e.printStackTrace();

			}
			
			
//			Login login=loginDao.setLoginUser(username, password);
			if (userRecords.size()>0) {
				//request.getSession().setAttribute("role",login.getUserRole() );
				String recordsJson = gson.toJson(userRecords);
                                res.add("records",recordsJson);
//				return "success";
			} else {
				res.add("records", "no records found");
//				return "failed";
			}
			
			JsonObject jsonObject = res.build();
			
			StringWriter writer = new StringWriter();
			Json.createWriter(writer).write(jsonObject);
			jsonString = writer.toString();
			

		}
                if (connection != null && type.equals("monthly")) {
			try {
				PreparedStatement pstmt = null;
				String query = null;
				query = "Select r.recordId,r.flatNumber,u.firstName,u.lastName,r.amount,r.dateOfPay,r.modeOfPayment,r.paymentReference from Records r inner join Users u on u.flatNumber=r.flatNumber where r.flatNumber= "+flatNumber;
				pstmt = connection.prepareStatement(query);
//				pstmt.setString(1, username);
//				pstmt.setString(2, password);
				ResultSet result = pstmt.executeQuery(query);
				while (result.next()) {
					UserPaymentRecords userPaymentRecords = new UserPaymentRecords();
                                        
                                        userPaymentRecords.setRecordNumber(result.getString(1));
                                        userPaymentRecords.setFlatNumber(result.getString(2));
                                        userPaymentRecords.setFirstName(result.getString(3));
                                        userPaymentRecords.setLastName(result.getString(4));
                                        userPaymentRecords.setAmount(result.getString(5));
                                        userPaymentRecords.setDate(result.getString(6));
                                        userPaymentRecords.setModeOfPayment(result.getString(7));
                                        userPaymentRecords.setPaymentReference(result.getString(8));
                                        userRecords.add(userPaymentRecords);
					
				  
				} 
                                connection.close();

			}

			catch (SQLException e) {
				e.printStackTrace();

			}
			
			
//			Login login=loginDao.setLoginUser(username, password);
			if (userRecords.size()>0) {
				//request.getSession().setAttribute("role",login.getUserRole() );
				String recordsJson = gson.toJson(userRecords);
                                res.add("records",recordsJson);
//				return "success";
			} else {
				res.add("records", "no records found");
//				return "failed";
			}
			
			JsonObject jsonObject = res.build();
			
			StringWriter writer = new StringWriter();
			Json.createWriter(writer).write(jsonObject);
			jsonString = writer.toString();
			

		}
		return jsonString;

	}
}
