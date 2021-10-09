<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="updateUserRecord">
	uid:
	<input type="number" name="uid">
	amount:
	<input type="number" step="0.01" name="amount">
	Date:
	<input type="date" name="date">
	<input type="number" name="rid">
	<button type="submit">Add Record</button>
	</form>
</body>
</html>