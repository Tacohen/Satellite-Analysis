<%@ include file="/WEB-INF/views/head.jsp"%>

<h2>Create Filters:</h2>

<form:form modelAttribute="filter" method="GET" id="filters">
<div style="float:left; height:350px; width:475; background-color: #F5FFFA; font-size: 14;">
		<div style="margin-top: 1cm;margin-left:.01cm">
		Difference Between Variables Is No Greater Than <form:input
			path="delta" maxlength="5" class="spinnerF" /> Degrees<br>
		Target Variable Min <form:input path="dependMin" class="spinnerF" /> Max <form:input
			path="dependMax" maxlength="50" class="spinnerF" />
		<br>
		Reference Variable Min <form:input path="independMin"
			class="spinnerF" /> Max <form:input path="independMax" maxlength="50"
			class="spinnerF" />
		<br>
		CVFilter Target Variable Max <form:input path="dependCV"
			stlye="width:50px;" class="spinnerF" />
		<br>
		CVFilter Reference Variable Max <form:input path="independCV"
			maxlength="50" class="spinnerF" />
		<br>
		Add Count Filter To Regression<form:checkbox path="countFilter" />
		<br>
		</div>
	<div style="margin-left:1cm;margin-top:.1cm;text-align:center;clear:left;float:left;
	font-size:15px;color:red;width:450px;" class="error">${filterError}</div>
	<div style="margin-top:1cm; margin-left:2cm; clear:left">
		<input type="button" value="View With Filter" onclick="viewFilter()" />
		<input type="button" id="addFilter" value="Create Filter"
			style="display: inline;" onclick="createFilter()" />
	</div>
	
</div>
<div
	style="float: left; margin-top: .1cm; margin-left: .5cm; font-size: 14;">
	<img class="testImages" id="filterImage" src="${filterImage}"
		 width=500 />
</div>

<div 
	style="float: left; background-color: #F5FFFA; margin-top: .1cm; height: 500px; width: 190px; margin-left: .2cm; overflow: auto; font-size: 14;">
	<h2>Target Variables</h2>
	
		
			<div style="white-space: nowrap;">
				<form:radiobuttons path="depend" items="${ICPlan.targetDataVariableRefs}" itemValue="ID" itemLabel="name"
					size="15" delimiter="<br/>"/> <!--<label for="${refToReg.name}">${refToReg.name}</label><br>-->
			</div>
		
	
	<h2>Reference Variables</h2>
	
		

			<div style="white-space: nowrap;">
				<form:radiobuttons path= "independ" items="${ICPlan.referenceDataVariableRefs}" itemValue="ID" itemLabel="name"
					size="15" delimiter="<br/>"/> 
			</div>
		
	
<input id="filterCount" value="${Analysis.filterCount}" style="display:none;"/>	
</div>
<div style="clear:left;">${FilterInfo}</div>
</form:form>
<script>
$(document).ready(function(){
	var spinner = $( ".spinnerF" ).spinner();
	var all = document.getElementsByClassName('spinnerF');
	for (var i = 0; i < all.length; i++) {
	  all[i].style.width = '50';
	}
	if(!document.getElementById("AnalysisID")){
		document.getElementById("addFilter").style.display="none";
	}
});
function updateCount(id){
	
	document.getElementById("analysisCount"+id).innerHTML = document.getElementById("filterCount").value;
}
function createFilter(){
	
	var id = document.getElementById("AnalysisID").innerHTML;
	$.post("createFilter?planID=${ICPlan.id}&Save=1&analysisID="+id, $("#filters").serialize(), function(data){
		$("#CreateFilterMenu").empty().append(data);
		viewAnalysis(id);
		updateCount(id);
	});
}

function viewFilter(){
	
	$("#CreateFilterMenu").load('viewWithFilter?planID=${ICPlan.id}&Save=0', $("#filters").serialize());
}

</script>


