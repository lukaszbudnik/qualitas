<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:url var="rootUrl" value="/resources/" />

<title>JQuery Simple OpenID Selector Demo</title>
<!-- Simple OpenID Selector -->
<link type="text/css" rel="stylesheet" href="${rootUrl}css/openid.css" />
<script type="text/javascript" src="${rootUrl}js/jquery-1.2.6.min.js"></script>
<script type="text/javascript" src="${rootUrl}js/openid-jquery.js"></script>
<script type="text/javascript" src="${rootUrl}js/openid-en.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		openid.init('openid_identifier');
		// openid.setDemoMode(true); //Stops form submission for client javascript-only test purposes
	});
</script>
<!-- /Simple OpenID Selector -->
<style type="text/css">
/* Basic page formatting */
body {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
</style>
</head>
<body>

	<h1>Spring Security 3</h1>
	<c:if test="${not empty param.error}">
		<p style="color: red">
			Your login attempt was not successful, try again.<br />
			<br /> Reason:
			<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
		</p>
	</c:if>

	<c:url var="openIDLoginUrl" value="/j_spring_openid_security_check" />
	<br />
	<!-- Simple OpenID Selector -->
	<form action="${openIDLoginUrl}" method="post" id="openid_form">
		<input type="hidden" name="action" value="verify" />
		<fieldset>
			<legend>Sign-in or Create New Account</legend>
			<div id="openid_choice">
				<p>Please click your account provider:</p>
				<div id="openid_btns"></div>
			</div>
			<div id="openid_input_area">
				<input id="openid_identifier" name="openid_identifier" type="text"
					value="http://" /> <input id="openid_submit" type="submit"
					value="Sign-In" />
			</div>
			<noscript>
				<p>
					OpenID is service that allows you to log-on to many different
					websites using a single indentity. Find out <a
						href="http://openid.net/what/">more about OpenID</a> and <a
						href="http://openid.net/get/">how to get an OpenID enabled
						account</a>.
				</p>
			</noscript>
		</fieldset>
	</form>
	<!-- /Simple OpenID Selector -->

</body>
</html>