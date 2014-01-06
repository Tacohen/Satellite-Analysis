<%@ include file="/WEB-INF/views/head.jsp"%>


<html>
<style>
#varScrollers
{
	margin-left:2cm;
}
.scrollers
{
	width: 510px;
}
</style>
<body>
<div id="varScrollers">
	<form:form id="dataVars" method="post" modelAttribute="ICPlan"
		action="addPlanVarsOnly">
		<table border="0" cellpadding="3" cellspacing="0">
			<tr>
			<td><h2>All Reference Variables</h2> </td>
			<td> </td>
			<td><h2>Selected Reference Variables</h2> </td>
			</tr>
			
			<tr>
				
				<td><select
					id="possRefRefs" size="15" class="scrollers" MULTIPLE>
						<c:forEach var="refVar" items="${refList}">
							<option label="${refVar.name}" value="${refVar.ID}">${refVar.name}</option>
						</c:forEach>
				</select></td>
				<td align="center" valign="middle" MULTIPLE><input
					type="Button" id="addRef" value="Add >>" style="width: 100px"><br>
					<br> <input type="Button" id="removeRef" value="<< Remove"
					style="width: 100px"></td>

				<td><form:select
						id="selRefRefs" size="15" class="scrollers"
						path="referenceDataVariableRefs">
						<form:options items="${ICPlan.referenceDataVariableRefs}"
							itemValue="ID" itemLabel="name" />
					</form:select></td>
			</tr>
		</table>
		<table border="0" cellpadding="3" cellspacing="0" style="margin-top:.5cm">
		<tr>
			<td><h2>All Target Variables</h2> </td>
			<td> </td>
			<td><h2>Selected Target Variables</h2> </td>
			</tr>
			<tr>
				<td> <select class="scrollers"
					id="possTgtRefs" size="15" MULTIPLE>
						<c:forEach var="refVar" items="${tgtList}">
							<option value="${refVar.ID}" label="${refVar.name}">${refVar.name}</option>
						</c:forEach>
				</select></td>
				<td align="center" valign="middle"><input type="Button"
					id="addTgt" value="Add >>" style="width: 100px"><br>
					<br> <input type="Button" id="removeTgt" value="<< Remove"
					style="width: 100px"></td>

				<td> <form:select
						class="scrollers" id="selTgtRefs" size="15" path="targetDataVariableRefs">
						<form:options items="${ICPlan.targetDataVariableRefs}"
							itemValue="ID" itemLabel="name" />
					</form:select></td>
			</tr>
		</table>
	</form:form>
</div>
</body>

<script language="Javascript">
    
    $(document).ready(function(){
    	SelectSort(document.getElementById("possRefRefs"));
    	SelectSort(document.getElementById("selRefRefs"));
    	SelectSort(document.getElementById("possTgtRefs"));
    	SelectSort(document.getElementById("selTgtRefs"));
    	$('#addTgt').click(function() {
    		updateSelectors('possTgtRefs','selTgtRefs');
        });
    	$('#removeTgt').click(function() {
    		updateSelectors('selTgtRefs','possTgtRefs');
        });
    	$('#addRef').click(function() {
    		updateSelectors('possRefRefs','selRefRefs');
        });
    	$('#removeRef').click(function() {
    		updateSelectors('selRefRefs','possRefRefs');
        });
    	jQuery( "#beginDate" ).datepicker();
    	jQuery( "#endDate" ).datepicker();
     });
    
    function updateSelectors(fromSel, toSel){
    	SelectMoveRows(fromSel,toSel);
    	$('#selRefRefs option').prop('selected', 'selected');
    	$('#selTgtRefs option').prop('selected', 'selected');
    	$.post( "addPlanVarsOnly", $("#dataVars").serialize(), function(data){
    		$("#reponseInfo").empty();
    		$("#reponseInfo").append(data);
    		updateState("${ICPlan.id}");
    		$("#reponseInfo").empty();
    	});
        $('#selRefRefs option').prop('selected', false);
        $('#selTgtRefs option').prop('selected', false);
        
        
		
        document.getElementById("CollectData").style.display="inline";
        $("#EventInfo").empty();
    }
    function SelectMoveRows(fromId, toId)
    {
    	SS1 = document.getElementById(fromId);
    	SS2 = document.getElementById(toId);
        var SelID='';
        var SelText='';
        // Move rows from SS1 to SS2 from bottom to top
        for (var i=SS1.options.length - 1; i>=0; i--)
        {
            if (SS1.options[i].selected == true)
            {
                SelID=SS1.options[i].value;
                SelText=SS1.options[i].text;
                var newRow = new Option(SelText,SelID);
                SS2.options[SS2.length]=newRow;
                SS1.options[i]=null;
            }
        }
        SelectSort(SS2);
    }
    function SelectSort(SelList)
    {
        var ID='';
        var Text='';
        
        for (var x=0; x < SelList.length - 1; x++)
        {
            for (var y=x + 1; y < SelList.length; y++)
            {
                if (SelList[x].text.toUpperCase() > SelList[y].text.toUpperCase())
                {
                    // Swap rows
                    ID=SelList[x].value;
                    Text=SelList[x].text;
                    SelList[x].value=SelList[y].value;
                    SelList[x].text=SelList[y].text;
                    SelList[y].value=ID;
                    SelList[y].text=Text;
                }
            }
        }
    }
  
    
    </script>
</html>
