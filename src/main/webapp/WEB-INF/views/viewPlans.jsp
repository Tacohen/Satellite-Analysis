<%@ include file="/WEB-INF/views/head.jsp" %>
<%@ include file="/WEB-INF/views/jsLinks.jsp"%>
<html>

<style>
select,input {
	font: normal 16px/22px Georgia;
	
}
body{
	font: normal 16px/22px Georgia;
	min-width:1290px;
	max-width:1500px;
}
h1 {
	font: normal 36px/36px Georgia;
	letter-spacing: -1px;
}

#plans
{
	 width: 700px; margin-left:5cm;
	 
}
#new-nav
{
	width:100%; overflow: hidden;
}
#plansLabel
{
	margin-left:5cm;
}
.analysisTb
{
	color:black;
	text-align: center;
	text-decoration: none;
}
</style>
<head>
<title>Event Predictor</title>



<table class="bar" border=1 cellpadding=2 cellspacing=2>
	<tr />
	<td class="item">About Us</a></td>
	<td class="item"><a href="createStudy">Create Plan</a></td>
	<td class="item"><a href="j_spring_security_logout">Logout</a></td>
	</tr>
</table>
</head>
<body>
	
		<div>
	<h2 id="plansLabel">Plans:</h2>
	
	<div id="planScroller"
			style="float:left; background-color: #F5FFFA; margin-left: 6.5cm;max-height: 500px; max-width: 700px; 
			margin-left:cm; overflow: auto; font-size: 12;">
	<table id="plans2" style="width:700px; text-align:center;"" border="1">
				<tr>
					<th>Name</th>
					<th>Target</th>
					<th>Reference</th>
					<th>Status</th>
				</tr>
				<c:forEach var="plan"  items="${ICPlans}">
				
					<tr id="plan${plan.id}" >	
						<td><a class="analysisTb" href="Javascript:viewPlanDesc(${plan.id})">${plan.name}</a></td>
						<td><a class="analysisTb" href="Javascript:viewPlanDesc(${plan.id})">${plan.tgt.name}</a></td>
						<td><a class="analysisTb" href="Javascript:viewPlanDesc(${plan.id})">${plan.ref.name}</a></td>
						<td><a id="planState${plan.id}" class="analysisTb" href="Javascript:viewPlanDesc(${plan.id})">${plan.state}</a></td>
					</tr>
					
				
				</c:forEach>
	</table>
	</div>
	<div id="new-nav"></div>
	
	<div id="predict-nav"></div>
	<script type='text/javascript'>
  
    function viewPlanDesc(id){
    	var element = document.getElementById("ui-datepicker-div");
    	if(element){
    		 element.parentNode.removeChild(element);
    	}
    	$( "#new-nav" ).load( "plan?planID="+id);
    }
    function updateState(id){
    	
    	document.getElementById("planState"+id).innerHTML = document.getElementById("Status").value;
    }
    
	function setState(state, id){
    	document.getElementById("planState"+id).innerHTML = state;
    }
</script>

</body>
</html>