package rest.connection;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MyConnection {
	public Connection getConnection() {
		DataSource dataSource;
		Connection dataConnection = null;

		try {
			Context ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			dataConnection = dataSource.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dataConnection;
	}

}
