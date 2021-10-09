package rest.controllers;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin(originPatterns = "*")
@Controller
public class MembersController {
	@GetMapping("/testsession")
	@ResponseBody
	public String Abc(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("role");
		return role;
	}

	@RequestMapping(value = "/getUserRecords", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getById(@RequestParam(defaultValue = "all") String uid,
			@RequestParam(defaultValue = "monthly") String type) {
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();

		ArrayList<String> id = new ArrayList<String>();
		ArrayList<String> fname = new ArrayList<String>();
		ArrayList<String> lname = new ArrayList<String>();
		ArrayList<String> amount = new ArrayList<String>();
		ArrayList<String> date = new ArrayList<String>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			if (uid.equals("all"))
				query = "SELECT r.record_id, u.firstname, u.lastname, r.amount, r.date_of_pay FROM Records r INNER JOIN Users u ON u.user_id=r.user_id ORDER BY r.date_of_pay DESC;";
			else if (type.equalsIgnoreCase("yearly"))
				query = "select u.user_id,u.firstname,u.lastname,sum(r.amount),year(date_of_pay) "
						+ "from Records r inner join Users u on u.user_id=r.user_id " + "where u.user_id=" + uid
						+ " group by r.user_id,year(r.date_of_pay) " + "order by r.date_of_pay desc;";
			else
				query = "SELECT r.record_id, u.firstname, u.lastname, r.amount, r.date_of_pay FROM Records r "
						+ "INNER JOIN Users u ON u.user_id=r.user_id WHERE u.user_id=" + uid
						+ " ORDER BY r.date_of_pay DESC;";
			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(uid));
			ResultSet result = pstmt.executeQuery(query);
//			System.out.println(result);
			while (result.next()) {
				id.add(result.getString(1));
				fname.add(result.getString("firstname"));
				lname.add(result.getString("lastname"));
				amount.add(result.getString(4));
				date.add(result.getString(5));
			}
			con.close();
			String idJson = gson.toJson(id);
			String fnameJson = gson.toJson(fname);
			String lnameJson = gson.toJson(lname);
			String amountJson = gson.toJson(amount);
			String dateJson = gson.toJson(date);
			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success").add("fname", fnameJson)
						.add("lname", lnameJson).add("id", idJson).add("amount", amountJson).add("date", dateJson);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		String jsonString;
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;
	}

	@RequestMapping(value = "/addUserRecord")
	@ResponseBody
	public String addUserRecord(@RequestParam String uid, @RequestParam String amount, @RequestParam String date) {
//		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO `records` (`user_id`, `amount`, `date_of_pay`) VALUES (?, ?, ?);";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, uid);
			pstmt.setString(2, amount);
			pstmt.setString(3, date);
//			System.out.println(pstmt);
			int result = pstmt.executeUpdate();
			con.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "error";
	}

	@RequestMapping(value = "/updateUserRecord")
	@ResponseBody
	public String updateUserRecord(@RequestParam String rid, @RequestParam String amount, @RequestParam String date) {
//		String rid = formData.getFirst("rid");
////		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "UPDATE `records` SET `amount`=?, `date_of_pay`=? WHERE `record_id`=?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, amount);
			pstmt.setString(2, date);
			pstmt.setString(3, rid);
			System.out.println(pstmt);
			int result = pstmt.executeUpdate();
			con.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "error";
	}

	@RequestMapping(value = "/deleteUserRecord")
	@ResponseBody
	public String deleteUserRecord(@RequestParam String rid) {
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "DELETE FROM `records` WHERE `record_id`=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(rid));
			int result = pstmt.executeUpdate();
			con.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}

	@RequestMapping(value = "/getSocietyRecords", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getSocietyRecords(@RequestParam(defaultValue = "monthly") String type) {
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();

		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> expense_type = new ArrayList<String>();
		ArrayList<String> amount = new ArrayList<String>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			if (type.equalsIgnoreCase("yearly"))
				query = "select Case when ROW_NUMBER() over(Partition by year(date_of_pay) order by year(date_of_pay))=1 then year(date_of_pay) else '' end as 'year',expense_type,sum(amount)\r\n"
						+ "from Society group by expense_type,year(date_of_pay) order by year(date_of_pay) desc;"; 
			else
				query = "select Case when ROW_NUMBER() over(Partition by Concat(month(date_of_pay),\"/\",year(date_of_pay)) order by Concat(month(date_of_pay),\"/\",year(date_of_pay)))=1 then Concat(month(date_of_pay),\"/\",year(date_of_pay)) else '' end as 'month' ,expense_type,sum(amount)\r\n"
						+ "from Society group by expense_type,Concat(month(date_of_pay),\"/\",year(date_of_pay)) order by Concat(year(date_of_pay),\"/\",month(date_of_pay)) desc;";
			pstmt = con.prepareStatement(query);
			ResultSet result = pstmt.executeQuery(query);
			while (result.next()) {
				date.add(result.getString(1));
				expense_type.add(result.getString(2));
				amount.add(result.getString(3));
			}
			con.close();
			String dateJson = gson.toJson(date);
			String expenseJson = gson.toJson(expense_type);
			String amountJson = gson.toJson(amount);
			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success")
				.add("date", dateJson)
				.add("expense", expenseJson)
				.add("amount", amountJson);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		String jsonString;
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;
	}
	
	@RequestMapping(value = "/addSocietyRecord")
	@ResponseBody
	public String addSocietyRecord(@RequestParam String expense_type, @RequestParam String amount, @RequestParam String date) {
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO `society` (`expense_type`, `amount`, `date_of_pay`) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, expense_type);
			pstmt.setString(2, amount);
			pstmt.setString(3, date);
			System.out.println(pstmt);
			int result = pstmt.executeUpdate();
			con.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "error";
	}
}
