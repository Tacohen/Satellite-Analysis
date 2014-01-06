<%@ include file="/WEB-INF/views/head.jsp" %>
<style>
#posProperties
{ 
	margin-left: auto; margin-left: 18cm;
}
</style>
<div id="posProperties">
<body>
	
	
	
		<h2>Properties</h2>
		
	<form:form id="propsForm" method="post" modelAttribute="Props">
			<table id="propertyForm">
				<c:forEach items="${Props.map}" var="prop">
					<tr>
						<td><label name="${prop.key}" value="${prop.key}">${prop.key}</label></td>
						<td><form:input path="map['${prop.key}']" id="${prop.key}" value="${prop.value}" type ="text" maxlength="50" class="spinner"/></td>
						<td><p id="error${prop.key}" class="error" style="font-size:14px;"></p></td>
					<tr>
				</c:forEach>
				
			</table>
		</form:form>
	
</body>
</div>
	<script>
		$(document).ready(function(){
			var spinner = $( ".spinner" ).spinner();
		});
	
		function checkPropsAndPost(){
			var isBad = false;
			var re = /^([0-9]+(\.)[0-9]+$)|[0-9]+$/;
			$("#propertyForm").find("input").each(function(){
				if (!this.value.match(re)){
					var error = document.getElementById("error"+this.id);
					error.innerHTML = "Invalid Value";
					isBad = true;
				}
				else{
					var error = document.getElementById("error"+this.id);
					error.innerHTML="";
					
				}
			});
			return isBad;
		}
	</script>