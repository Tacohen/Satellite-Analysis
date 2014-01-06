<%@ include file="/WEB-INF/views/head.jsp" %>
<h2>Create/View A New Analysis:</h2>

<div style="margin-left:1cm; float:left">
<%@ include file="/WEB-INF/views/analysisTable.jsp" %>


<form:form modelAttribute="ICAnalysis" id="AnalysisForm">
	<div style="margin-top:.5cm;clear:left">
	<form:label path="name">Analysis Name:</form:label>
	<form:input path="name" />
	</div>
	<div style="float:left;">
	<form:label path="depend" id="tgtScroller">Target Variable:</form:label>
	<form:select  path="depend" onchange="" style="margin-top:.5cm; width:250;">
		<form:options items="${ICPlan.targetDataVariableRefs}"
						itemValue="ID" itemLabel="name" />
		
	</form:select>
	<td><form:errors path="name"/></td>
	<form:label path="independ" id="tgtScroller">Reference Variable:</form:label>
	<form:select  path="independ" onchange="" style="margin-top:.5cm; width:250">
		<form:options items="${ICPlan.referenceDataVariableRefs}"
						itemValue="ID" itemLabel="name" />	
	</form:select>
	</div>
	<div style="margin-left:6cm;margin-top:.1cm;text-align:center;clear:left;float:left;font-size:15px;color:red;" class="error">${Errors}</div>
</form:form>
<div style="margin-top:1.8cm;margin-left:10cm;clear:left;">
	<input type="button" value="Create New Analysis" onclick="createAnalysis()"/>
</div>
</div>

<script type="text/javascript">
function createAnalysis(){
	
	var posting = $.post("saveAnalysis?planID=${ICPlan.id}", $("#AnalysisForm").serialize());
	
	posting.done(function(data){
		$("#AnalysisMenu").empty().append(data);
	});
}

</script>
