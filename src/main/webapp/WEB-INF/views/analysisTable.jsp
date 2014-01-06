<%@ include file="/WEB-INF/views/head.jsp"%>
<style>
.analysisTb
{
	color:black;
	text-align: center;
	text-decoration: none;
}
</style>
<div id="AnalysisScroller" style="background-color: #F5FFFA;margin-left:4cm;height:400px;width:1000px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
	
	<table id="AnalysisTable" style="text-align:center;" border="1">
		<tr>
			<th>Analysis Name</th>
			<th>Target Variable</th>
			<th>Reference Variable</th>
			<th>Number of Filters</th>
		</tr>
		<c:forEach var="analysis" items="${Analysises}"> 
		<tr>
			
			<td><a class="analysisTb" href="Javascript:viewAnalysis(${analysis.id})">${analysis.name}</a></td>
			<td><a class="analysisTb" href="Javascript:viewAnalysis(${analysis.id})">${analysis.depend.name}</a></td>
			<td><a class="analysisTb" href="Javascript:viewAnalysis(${analysis.id})">${analysis.independ.name}</a></td>
			<td><a id="analysisCount${analysis.id}" class="analysisTb" href="Javascript:viewAnalysis(${analysis.id})">${analysis.filterCount}</a></td>
		</tr>
		</c:forEach>
	</table>
</div>
<script>
$(document).ready(function(){
	resize("AnalysisTable","AnalysisScroller", 1000, 400);
});

function viewAnalysis(id){
	$("#AnalysisView").load("regPlotInitial?planID=${ICPlan.id}&analysisID="+id, function(){
		document.getElementById("addFilter").style.display="inline";
	});
}
</script>