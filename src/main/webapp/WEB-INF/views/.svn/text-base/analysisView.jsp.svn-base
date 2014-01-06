<%@ include file="/WEB-INF/views/head.jsp"%>

<div style="float:left; margin-top:0cm;margin-left:0cm;">
		<div id="AnalysisID" style="display:none">${Analysis.id}</div>
		<h2>Viewing Analysis Name: ${Analysis.name}</h2>
		<div style="height: 500px; width: 100%;">
		<div id="FilterScroller"
			style="float:left; background-color: #F5FFFA; margin-top: 0cm; min-height:300px; max-height: 500px; max-width: 700px; 
			margin-left:cm; overflow: auto; font-size: 12;">
			<%@ include file="/WEB-INF/views/filterTable.jsp"%>
		</div>
		<div style="float:left;margin-left:.2cm" id="AnalysisDiv">
			<img class="testImages" id="AnalysisImage" src="${AnalysisImage}" width=500/>
		</div>
		</div>
		<div style="margin-top:1cm;margin-left:5cm;clear:left">
			<input type="button" value="Delete Selected Filters" />
			<input type="button" value="Replot With Filters" onclick="plotWithFilters()" />
			<input type="button" value="Delete Analysis" onclick="deleteAnalysis()" />
		</div>
</div>
<div style="clear:left;">${PlotInfo}</div>
<script>
function deleteAnalysis(){
	$.post("deleteAnalysis?analysisID=${Analysis.id}", function(data){
		$("#AnalysisInfo").load("events?planID=${ICPlan.id}");
	});
}
function plotWithFilters(){
	
	
	$("#AnalysisView").load("plotWithFilters?analysisID=${Analysis.id}", $("#SelectedFilters").serialize());
}
</script>