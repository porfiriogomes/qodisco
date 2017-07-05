<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	var property = -1;

	$("#add-property").click(function(){
		property++;
		var selectedOption = $('#domain-option').find(":selected").val();
		$.get("<%=request.getContextPath()%>/resource/addproperty", { fieldid: property, domainselected: selectedOption },
			function(data){
				$("#add-property").before(data);
		});
	});

});

function removePropertyRow(row){
	$('#property-row'+row).remove();
}

function submitForm() {
	$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
	$('#searchresource-form').submit();
}

</script>

<form:form id="searchresource-form" method="post" action="${pageContext.request.contextPath}/resource/search" modelAttribute="resourcesearch" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">
		<div class="form-group">
			<label>Are you searching for?</label>
			<form:select path="searchFor" cssClass="form-control">
				<form:options items="${devicetypes}"/>
			</form:select>
		</div>
				
		<hr/>
		
		<div id="observed-properties">
			<label>Which are its observed properties?</label>
			<div class="form-group">
				<input type="button" class="btn btn-primary" id="add-property" value="Add Resource's Observed Property">
			</div>
		</div>
		
		<hr/>
		
		<div id="location">
			<div class="form-group">
				<label>Where your resource is located?</label>
				<form:select path="location" cssClass="form-control">
					<form:option value="None"></form:option>
					<form:options items="${locations}"/>
				</form:select>
			</div>
		</div>
	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Search"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>