<%@ include file="/WEB-INF/views/head.jsp"%>	
		
		
		<form:form  id="SelectedFilters" modelAttribute="CurrFilters" >
		
		
	
				<table id="FilterTable"  style="max-width:100px; text-align:center;" border="1">
				<tr>
					<th>Selected Filters</th>
					<th>Target Var</th>
					<th>Ref Var</th>
					<th>Target Min-Max</th>
					<th>Target CV Max</th>
					<th>Reference Min-Max</th>
					<th>Reference CV Max</th>
					<th>Max Delta</th>
					<th>Count Filter</th>
				</tr>
				<c:forEach var="formFilter" varStatus="i" items="${PossFilters.list}">
				<!--  <div style="white-space: nowrap;">-->
					<tr>	
						<td><form:checkbox item="${PossFilters.list[i.index]}" path="list" 
						checked="${PossFilters.list[i.index].selected2}" value="${PossFilters.list[i.index].id}" size="15" /></td>
						<td>${formFilter.depend.name}</td>
						<td>${formFilter.independ.name}</td>
						<td>${formFilter.dependMin}-${formFilter.dependMax}</td>
						<td>${formFilter.dependCV2}</td>
						<td>${formFilter.independMin}-${formFilter.independMax}</td>
						<td>${formFilter.independCV2}</td>
						<td>${formFilter.delta2}</td> 
						<td>${formFilter.countFilter}</td>
					</tr>
					
				<!--  </div>-->
				</c:forEach>
				</table>
			
			</form:form>