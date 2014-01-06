
<%@ include file="/WEB-INF/views/head.jsp" %>

<html>
<head>
<title>Login</title>
</head>
<table class="bar" border=1 cellpadding=2 cellspacing=2>
	<tr />
	<td class="item">About Us</a></td>
	<td class="item"><a href="createUser">Create User</a></td>
	</tr>
</table>
<body>
<c:if test="${not empty param.error}">
  <font color="red">
  Login error. <br />
  Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
  </font>
</c:if>
<form method="POST" action="<c:url value="/j_spring_security_check" />">
<table>
  <tr>
    <td align="right">Username</td>
    <td><input type="text" name="j_username" /></td>
  </tr>
  <tr>
    <td align="right">Password</td>
    <td><input type="password" name="j_password" /></td>
  </tr>
  <tr>
    <td align="right">Remember me</td>
    <td><input type="checkbox" name="_spring_security_remember_me" /></td>
  </tr>
  <tr>
    <td colspan="2" align="right">
      <input type="submit" value="Login" />
      <input type="reset" value="Reset" />
    </td>
  </tr>
</table>
</form>
</body>
</html>
