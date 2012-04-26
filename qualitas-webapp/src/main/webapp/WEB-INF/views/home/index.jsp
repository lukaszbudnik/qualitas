<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
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
	
	<c:if test="${fn:length(processes) > 0}">
		<p>List of your currently installed processes:</p>
		<table>
			<tr>
				<th>id</th>
				<th>installation date</th>
				<th>status</th>
				<th>process name</th>
				<th>process epr</th>
			</tr>
		<c:forEach items="${processes}" var="p">
			<tr>
					<td>${p.processId}</td>
					<td>${p.installationDate}</td>
					<td>${p.processStatus}</td>
					<td>${p.processName}</td>
					<td>${p.processEPR}</td>
			</tr>
		</c:forEach>
		</table>
	</c:if>

	<p>Install new  process:	</p>
	<form method="post" enctype="multipart/form-data">
		<input type="file" name="file" />
		<input type="submit" />
	</form>
</html>