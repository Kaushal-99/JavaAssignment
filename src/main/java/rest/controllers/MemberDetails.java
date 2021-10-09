//http://localhost:9090/sqlartifact/mem/add?username=sanjay&firstname=sanjay&lastname=prabhu&password=chinna&email=psanjay@gmail.com&role=member

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.Beans.Login;
import rest.connection.MyConnection;
import rest.dao.LoginDao;

@CrossOrigin(origins = "*")
@RestController
public class MemberDetails {
	
	@Autowired
	LoginDao loginDao;

	@GetMapping("/test")
	@ResponseBody
	public String Abc() {
		return "success";
	}

	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addMember(@RequestParam String uname, @RequestParam String fname,@RequestParam String lname, @RequestParam String password, @RequestParam String email) {
//		String uname = formData.getFirst("username");
//		String fname = formData.getFirst("firstname");
//		String lname = formData.getFirst("lastname");
//		String pwd = formData.getFirst("password");
//		String email = formData.getFirst("email");

		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO Users(`username`, `firstname`, `lastname`, `password`,`email`,`role`) VALUES(?, ?, ?, ?,?, 'member')";
			pstmt = con.prepareStatement(query);
//	         pstmt.setInt(1, Integer.parseInt(userId));
			pstmt.setString(1, uname);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, password);
			pstmt.setString(5, email);
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				con.close();
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
//	        	 return "{status: true, message: success, batchId: "+batchMapped+"}";
			} else {
				con.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "error");
//	        	 return "{status: true, message: batch_id_not_mapped, batchId: not mapped}";
			}

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

	@RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAll() {
		// System.out.println(userId);
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		ArrayList<String> user_id = new ArrayList<String>();
		ArrayList<String> username = new ArrayList<String>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> password = new ArrayList<String>();
		ArrayList<String> email = new ArrayList<String>();
		ArrayList<String> role = new ArrayList<String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "SELECT `user_id`,`username`,`firstname`,`lastname`, `password`, `email`, `role` FROM `Users` WHERE `role`='member'";
			pstmt = con.prepareStatement(query);
			ResultSet result = pstmt.executeQuery(query);
			System.out.println(result);
			while (result.next()) {
				user_id.add(result.getString("user_id"));
				username.add(result.getString("username"));
				first_name.add(result.getString("firstname"));
				last_name.add(result.getString("lastname"));
				password.add(result.getString("password"));
				email.add(result.getString("email"));
			}
			con.close();
			String fnameJson = gson.toJson(first_name);
			String lnameJson = gson.toJson(last_name);
			String idJson = gson.toJson(user_id);
			String usernameJson = gson.toJson(username);
			String pwdJson = gson.toJson(password);
			String emailJson = gson.toJson(email);
//			String roleJson = gson.toJson(role);

			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success")
						.add("firstname", fnameJson).add("lastname", lnameJson).add("userid", idJson)
						.add("password", pwdJson).add("email", emailJson).add("username", usernameJson);
//						.add("role", roleJson);

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

	@RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		// System.out.println(userId);
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "DELETE FROM `Users` where `user_id`=" + id;
			pstmt = con.prepareStatement(query);
			// pstmt.setInt(1,Integer.parseInt(id));
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				con.close();
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
			} else {
				con.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "failure");

			}
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

	@RequestMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String update(@PathVariable("id") String id, @RequestParam("username") String uname,
			@RequestParam("firstname") String fname, @RequestParam("lastname") String lname,
			@RequestParam("password") String pwd, @RequestParam("email") String email,
			@RequestParam("role") String role) {
		// System.out.println(userId);
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "UPDATE `Users` SET  `username`=?, "
					+ "`firstname`=?, `lastname`=?, `password`=?,`email`=?,`role`=?  where `user_id`=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, uname);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, pwd);
			pstmt.setString(5, email);
			pstmt.setString(6, role);
			pstmt.setString(7, id);
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
				con.close();
			} else {
				con.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "erro");

			}
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

	@RequestMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "/login")
	@ResponseBody
	public String loginUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {

		
		
		String result=loginDao.loginUser(username, password);
		return result;
		
	}

	@RequestMapping(value = "/logout")
	@ResponseBody
	public String logoutUser(HttpServletRequest request) {
		request.getSession().setAttribute("role", "");
		return "logout successful";
	}

}