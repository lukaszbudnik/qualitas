<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE htmlPUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Qualitas Web App</title>
</head>
<body>
	<h1>Welcome to Qualitas!</h1>

	<p>Your OpenID username is: ${username}</p>

	<form method="post" enctype="multipart/form-data">
		<input type="file" name="file" />
		<input type="submit" />
	</form>
</html>