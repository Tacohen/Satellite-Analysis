<%@ include file="/WEB-INF/views/head.jsp" %>
<%@ include file="/WEB-INF/views/jsLinks.jsp"%>
<html>

<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
<script>
	$(function() {
		$("#datepicker2").datepicker();
	});
</script>
<head>

<title>Generate New Study</title>
<table class="bar" border=1 cellpadding=2 cellspacing=2>
	<tr />
	<td class="item">About Us</a></td>
	<td class="item"><a href="viewPlans">View Plans</a></td>
	<td class="item"><a href="j_spring_security_logout">Logout</a></td>
	</tr>
</table>

</head>

<body id="thebody">

	<form:form modelAttribute="ICPlan" method="POST" action="addPlan">
		<div id="content" style="width: 350px; margin: 0 auto;">
			<br>
			<div>
				<form:label path="name">Plan Name:</form:label>
				<form:input path="name" />
				<form:errors id="error" path="name" />
			</div>
			<br>
			<div>
				<form:label path="ref">Reference Instrument:</form:label><br>
				<form:select path="ref">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${instruments.instrumentList}"
						itemValue="name" itemLabel="name" />
				</form:select>
				<form:errors id="error" path="ref"/>
			</div>
			<br>
			<div>
				<form:label path="tgt">Target Instrument:</form:label><br>
				<form:select path="tgt">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${instruments.instrumentList}"
						itemValue="name" itemLabel="name" />
				</form:select>
				<form:errors id="error" path="tgt" />
				<div id = "errorGen">
				${error}
				</div>
			</div>
			<br>
			<div>
				<form:label path="eventPredictionRanges[0].beginDate.time">Start Date:</form:label><br>
				<form:input type="text" id="datepicker"
					path="eventPredictionRanges[0].beginDate.time" />
			</div>
			<br>
			<div>
				<form:label path="eventPredictionRanges[0].endDate.time">End Date:</form:label><br>
				<form:input type="text" id="datepicker2"
					path="eventPredictionRanges[0].endDate.time" />

			</div>
			<br> <input type="submit" value="Save Plan" />

		</div>
	</form:form>

</body>
</html>