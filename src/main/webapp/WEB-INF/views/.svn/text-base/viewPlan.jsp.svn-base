<%@ include file="/WEB-INF/views/head.jsp" %>
<html>
<style>

#editStuff
{
	 margin-top: 0; white-space:nowrap; clear:left; float:left;
}
</style>

<script>
 
 $(document).ready(function(){
	var element = document.getElementById("ui-datepicker-div");
	if(element){
		 element.parentNode.removeChild(element);
	}
 });
 
 
  function changeTopBox(id){
	  
	 
	  var posting = $.post( "edit", $("#PlanForm").serialize());
	  
	  posting.done(function(data) {
		    $( "#new-nav" ).empty();
		    $("#new-nav").append(data);
		    //document.getElementById("planStatus"+id).innerHTML = document.getElementById("PlanStatus").value;
	  });
	  
  }
  function checkDates(){
		
		var re = /^([0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4})$/;
		var begin = document.getElementById("beginDate");
		var end = document.getElementById("endDate");
		if(!begin.value.match(re)){
			document.getElementById("ErrorDates").innerHTML="Begin Date Is Empty";
			return true;
		}
		if(!end.value.match(re)){
			document.getElementById("ErrorDates").innerHTML="End Date Is Empty";
			return true;
		}
		return false;
	}
  function deletePlan(){
	  $.post("deletePlan?planID=${ICPlan.id}");
  }
  </script>
	<input id="PlanStatus" style="display:none" value="${ICPlan.state}"/>
	<form:form modelAttribute="ICPlan" id="PlanForm" method="POST"
		action="edit">
		<div id="editStuff">
			<h2>Instrument And Dates</h2>
			<br>
			<div>
				<form:label path="ref">Reference Instrument:</form:label>
				<form:select path="ref" id="refScroller"
					onchange='changeTopBox(${ICPlan.id})'>

					<form:options items="${instruments.instrumentList}"
						itemValue="name" itemLabel="name" />
					<form:errors path="ref" />

				</form:select>
			
			<br>
				<form:label path="tgt" id="tgtScroller">Target Instrument:</form:label>
				<form:select  path="tgt"
					onchange="changeTopBox(${ICPlan.id})" style="margin-top:.5cm">

					<form:options items="${instruments.instrumentList}"
						itemValue="name" itemLabel="name" />
					<td><form:errors path="tgt" /></td>
					
				</form:select>
			</div>
			<p id="error">${errorTgtRef}</p>
			<br>
		</form:form>
		<form:form modelAttribute="ICPlan" id="datesForm">
			<div style="margin-top:.25cm;">
			<div>
				<form:label path="eventPredictionRanges[0].beginDate.time">Start Date:</form:label>
				<form:input type="text" id="beginDate" class="datepicking"
					path="eventPredictionRanges[0].beginDate.time" />
					<form:errors path="eventPredictionRanges[0].beginDate.time" />
			</div>
			<br>
			<div>

				<form:label path="eventPredictionRanges[0].endDate.time">End Date:</form:label>
				<form:input type="text" id="endDate" class="datepicking"
					path="eventPredictionRanges[0].endDate.time" />
					<form:errors path="eventPredictionRanges[0].endDate.time"></form:errors>
			</div>
			</div>
			
			<p class="error" id="ErrorDates">${Errors}</p>
		</form:form>
			<!--  <input type="submit" value="Save Plan" /> <input type="button" value="Delete Plan" onclick ="deletePlan()"/>-->
		
		</div>
		<div id="props-nav" >
			<%@ include file="/WEB-INF/views/propsPage.jsp"%>
		</div>
	

</html>