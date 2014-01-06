<%@ include file="/WEB-INF/views/head.jsp"%>
<style>
.enlargedImages{
	font-size:15px;text-align:center;
}
</style>
<body>
<div id="EventInfo">
<div style="margin-top:1cm;margin-left:.5cm;">
	<div style="float:left;">
	<h2>Target Graphs</h2>
	</div>
	<div style="float:left;margin-left:8.5cm">
	<h2>Reference Graphs</h2>
	</div>
	<div id="EnlargedImages" style="display:none; overflow:auto;">
		<div style="clear:left;float:left;" id="container" >
			<img class="testImages" id="TgtEnlarge" src="" width=500/>
			<input value="" id="tgtEnSet" style="display:none;"/>
			<h3 id="TgtEnlargeLa" class="enlargedImages"> </h3>
		</div>
	
		<div style="float:left;margin-top:7cm;margin-left:.5cm;">
			<input type="button" value="hide" onclick="collapseImages()" width=75/>
		</div>
	
		<div style="float:left;margin-left:.5cm;">
			<img class="testImages" id="RefEnlarge" src="" width=500/>
			<input value="" id="refEnSet" style="display:none;"/>
			<h3 id="RefEnlargeLa" class="enlargedImages"> </h3>
		</div>
		</div>
</div>
<div style="clear:left;">
<div style="float:left">
<div id="TgtImageScroller" style="clear:left;margin-top:.5cm;background-color: #F5FFFA;margin-left:.5cm;height:400px;width:500px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;float:left;">
	
	<c:forEach var="event" items="${ICPlan.events}" varStatus="eventInfo">
		<div >
		
			<h2 style="clear:left;">Event ${event.id}:</h2>
			<c:forEach var="tgtVar" varStatus="i" items="${ICPlan.targetDataVariableRefs}">
				<div style="float:left" onclick="enlarge('${eventInfo.index}','${i.index}','1')">
					<img class="testImages" id="imageTgt${eventInfo.index}${i.index}" src="images/${username}/plan${ICPlan.id}/${ICPlan.eventPredictionRanges[0].beginDate.time}
					${ICPlan.eventPredictionRanges[0].endDate.time}/${EncodedProps}/${ICPlan.tgt.name}${ICPlan.ref.name}
					/verifyTargets/${tgtVar.name}${event.id}.png"  width=120/>
					<h3 id="tgtLabel${eventInfo.index}${i.index}" style="font-size:10px;text-align: center;"> ${tgtVar.name}</h3>
				</div>
			</c:forEach>
		</div>
	</c:forEach>
</div>
</div>
<div style="float:left;">


<div id="RefImageScroller" style="margin-top:.5cm;background-color: #F5FFFA;margin-left:3cm;height:400px;width:500px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;float:left;">
	
	<c:forEach var="event" items="${ICPlan.events}" varStatus="eventInfo">
		<div >
		
			<h2 style="clear:left;">Event ${event.id}:</h2>
			<c:forEach var="refVar" varStatus="i" items="${ICPlan.referenceDataVariableRefs}">
				<div style="float:left" onclick="enlarge('${eventInfo.index}','${i.index}','')">
					<img class="testImages" id="imageRef${eventInfo.index}${i.index}" src="images/${username}/plan${ICPlan.id}/${ICPlan.eventPredictionRanges[0].beginDate.time}
					${ICPlan.eventPredictionRanges[0].endDate.time}/${EncodedProps}/${ICPlan.tgt.name}${ICPlan.ref.name}
					/verifyReferences/${refVar.name}${event.id}.png" width=120/>
					<h3 id="refLabel${eventInfo.index}${i.index}" style="font-size:10px;text-align: center;"> ${refVar.name}</h3>
				</div>
			</c:forEach>
		</div>
	</c:forEach>
</div>
</div>
</div>
<%@ include file="/WEB-INF/views/filterMaker.jsp" %>
</body>
<script>
	
	$(document).ready(function(){
		chopText("TgtImageScroller");
		chopText("RefImageScroller");
		//enlarge("0", "0", "");
		
		$(".testImages").error(function(){
				loadAgain(this);
		});
	});
	
	
	function loadAgain(element){
		
		setTimeout(function(){
			
			element.src = element.src;//.substring(28, element.src.length);
		}, 5000);
	}
	function chopText(id){
		var list=document.getElementById(id).getElementsByTagName("h3");
		for(var i=0; i < list.length; ++i){
			if(list[i].innerHTML.length > 17){
				list[i].innerHTML = list[i].innerHTML.substring(0, 18);
			}
		}
	}
	function enlarge(event, index, isTgt){
		if(isTgt){
			document.getElementById("TgtEnlarge").src= document.getElementById("imageTgt"+event +""+index).src;
			document.getElementById("TgtEnlargeLa").innerHTML= document.getElementById("tgtLabel"+event+""+index).innerHTML;
			if(!document.getElementById("refEnSet").value){
				document.getElementById("RefEnlarge").src = document.getElementById("imageRef"+event+""+0).src;
				document.getElementById("RefEnlargeLa").innerHTML = document.getElementById("refLabel"+event+""+0).innerHTML;
				document.getElementById("refEnSet").value=1;
			}
			
		}
		else{
			document.getElementById("RefEnlarge").src = document.getElementById("imageRef"+event+""+index).src;
			document.getElementById("RefEnlargeLa").innerHTML = document.getElementById("refLabel"+event+""+index).innerHTML;
			if(!document.getElementById("tgtEnSet").value){
				document.getElementById("TgtEnlarge").src= document.getElementById("imageTgt"+event +""+0).src;
				document.getElementById("TgtEnlargeLa").innerHTML= document.getElementById("tgtLabel"+event+""+0).innerHTML;
				document.getElementById("tgtEnSet").value = 1;
			}
		}
		document.getElementById("EnlargedImages").style.display="inline";
	}
	function collapseImages(){
		document.getElementById("EnlargedImages").style.display="none";
	}
</script>