<%@ include file="/WEB-INF/views/head.jsp"%>

<style>
#planButtons
{
	margin-top:1cm;margin-left:12cm;
}
#var-nav
{
	margin-top:1cm;
}
</style>
<html>

<%@ include file="/WEB-INF/views/viewPlan.jsp"%>
<div id="planButtons">
<input type="button" value="Update Plan" style="float:left" onclick="saveChanges()"/>
<input type="button" value="Delete Plan" style="margin-left:1cm" onclick="deletePlan()"/>
</div>
<div id="var-nav">
	<%@ include file="/WEB-INF/views/planVars.jsp"%>
</div>
<input type="button" id="CollectData" value="Collect Data" style="margin-top:.5cm;margin-left:15.5cm;"onclick="collectData()"/>

<h2 id="predictStatus" style="margin-left:15cm;margin-top:.5cm;"></h2>
<div id="reponseInfo" style="display:none"></div>
<div id="EventInfo">
	<%@ include file="/WEB-INF/views/eventTable.jsp"%>
</div>


<script type="text/javascript">
	var collecting = false;
	
	function remove(id){
		var element = document.getElementById(id);
		element.parentNode.removeChild(element);
	}
	
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
	$(document).ready(function(){
		
		if("${ICPlan.state}" == "DATA_ACQUIRED"){
			
			document.getElementById("CollectData").style.display="none";
			//resize("EventsTable","EventScroller", 1000, 400);
		}
		else{
			document.getElementById("CollectData").style.display="inline";
		 	$("#EventInfo").empty();
		}
	});
	
	
	
	function saveChanges(){
		if(checkDates()){
			return;
		}
		
		if(checkPropsAndPost()){
			return;
		}
		$.post( "edit", $("#datesForm").serialize(), function(data){
			$("#reponseInfo").empty();
			$("#reponseInfo").append(data);
			var errors = document.getElementById("dataFromServer");
			if(errors && errors.value){
				
				 document.getElementById("ErrorDates").innerHTML=errors.value;
				 $("#responseInfo").empty();
				 return;
			}
			document.getElementById("ErrorDates").innerHTML="";
			$("#reponseInfo").empty();
			posting = $.post("postProps?ID=${ICPlan.id}", $("#propsForm").serialize());
		
			posting.done(function(data){
				$("#reponseInfo").empty();
				$("#reponseInfo").append(data);
				updateState("${ICPlan.id}");
				if(document.getElementById("dataFromServer").innerHTML != "DATA_ACQUIRED"){
					$("#EventInfo").empty();
					document.getElementById("CollectData").style.display="inline";
				}
				$("#reponseInfo").empty();
			});
		});
	}
	
	
	function deletePlan(){
		$.post("deletePlan?planID=${ICPlan.id}");
		$("#new-nav").empty();
		var element = document.getElementById("plan${ICPlan.id}");
		element.parentNode.removeChild(element);
	}
	
	function collectData(){
		var info = document.getElementById("predictStatus");
		info.innerHTML = "Predicting";
		$.post("predict?planID=${ICPlan.id}", function(data){
			if(document.getElementById("EventScroller")){
				return;
			}
			$("#reponseInfo").append(data);
			var count = document.getElementById("dataFromServer").value;
			if(!count ||count == 0){
				info.innerHTML = "No Events Found";
				updateState("${ICPlan.id}");
				$("#reponseInfo").empty();
				return;
			}
			updateState("${ICPlan.id}");
			$("#reponseInfo").empty();
			
			info.innerHTML = "Found " + count + " Events";
	
			
			$.post("locate?planID=${ICPlan.id}", function(data){
				if(document.getElementById("EventScroller")){
					return;
				}
				info.innerHTML = "Events Located";
				$("#reponseInfo").append(data);
				updateState("${ICPlan.id}");
				
				$("#reponseInfo").empty();
				
				$.post("collect?planID=${ICPlan.id}", function(data){
					info.innerHTML= "";
					if(document.getElementById("EventScroller")){
						return;
					}
					$("#EventInfo").append(data);
					if(!document.getElementById("EventScroller")){
						document.getElementById("CollectData").style.display="inline";
					}
					else{
						//resize("EventsTable","EventScroller", 1000, 400);
						document.getElementById("CollectData").style.display="none";
					}
					
				});
			});
		});
	}
</script>

</html>