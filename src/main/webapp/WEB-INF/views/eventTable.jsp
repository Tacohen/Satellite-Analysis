<%@ include file="/WEB-INF/views/head.jsp"%>


<style>
.tableHeader{
	margin-left:3.5cm;
}
</style>
<body>

<h2 class="tableHeader">Events:</h2 >
<div id="EventScroller" style="background-color: #F5FFFA;margin-left:4cm;height:400px;width:1000px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
	
	<table id="EventsTable" border="1">
		<tr>
			<th>Event Number</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Latitude North</th>
			<th>Latitude South</th>
			<th>Longitude East</th>
			<th>Longitude West</th>
		</tr>
		<c:forEach var="event" items="${ICPlan.events}"> 
		<tr>
			<td>${event.id}</td>
			<td>${event.begin.time}</td>
			<td>${event.end.time}</td>
			<td>${event.latNorth}</td>
			<td>${event.latSouth}</td>
			<td>${event.lonEast}</td>
			<td>${event.lonWest}</td>
		</tr>
		</c:forEach>
	</table>
</div>
<input type="button" id="StartAnalysis" value="Start Analysis" style="margin-top:.5cm;margin-left:15.5cm;" onclick="Javascript:startAnalysis()"/>
<div id="AnalysisInfo" style="margin-left:2cm">

</div>
</body>
<script>
$(document).ready(function(){
	setState("${ICPlan.state}", "${ICPlan.id}");
	if("${ICPlan.state}" != "DATA_ACQUIRED"){
		
		
		
		$("#EventInfo").empty();
	}
	else{
		resize("EventsTable","EventScroller", 1000, 400);
	}
});
function resize(id, id2, wd, ht){
	var width = document.getElementById(id).offsetWidth;
	var height = document.getElementById(id).offsetHeight;
	if (width < wd){
		document.getElementById(id2).style.width=width;
	}
	if(height < ht){
		document.getElementById(id2).style.height=height;
	}
}

function startAnalysis(){
	
	$("#AnalysisInfo").load("events?planID=${ICPlan.id}", function(){
		remove("StartAnalysis");
	});
}
</script>
