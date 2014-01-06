<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<table class="bar" border=1 cellpadding=2 cellspacing=2>
		<tr/>
			<td class="item">About Us</td>
			<td class="item"><a href="home" >Return Home</a></td>
		</tr>
	</table>
</head>
<body>
	<div align="Left">
		<h2 style="text-align: left;">Enter Information to Create an Account</h2>
		<form:form modelAttribute="contact" method ="POST" action="addContact">
			<table>
				<tr>
					<td><form:label path="firstName">First Name</form:label></td>
					<td><form:input path="firstName" /></td>
					<td><form:errors path="firstName" /></td>
				</tr>
				<tr>
					<td><form:label path="lastName">Last Name</form:label></td>
					<td><form:input path="lastName" /></td>
					<td><form:errors path="lastName" /></td>
				</tr>
				<tr>
					<td><form:label path="email">Email Address</form:label></td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" /></td>
				</tr>
				<tr>
					<td><form:label path="userName">User Name</form:label></td>
					<td><form:input path="userName" /></td>
					<td><form:errors path="userName" /></td>
				</tr>
				<tr>
					<td><form:label path="password">Enter a Password</form:label></td>
					<td><form:password path="password" showPassword="false"/></td>
					<td><form:errors path="password" /></td>
				</tr>
				<tr>
					<td><form:label path="confirmPassword">Re-Enter Password</form:label></td>
					<td><form:password path="confirmPassword" showPassword="false" /></td>
					<td><form:errors path="confirmPassword" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Create" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>
