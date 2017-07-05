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
	$('#newresource-form').submit();
}

</script>

<form:form id="newresource-form" method="post" action="${pageContext.request.contextPath}/resource/new" modelAttribute="resource" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">
		<div class="form-group">
			<label>Name</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:input path="name" type="text" cssClass="form-control" placeholder="Enter a name for the resource."/>
			</div>
		</div>
		
		<div class="form-group">
			<label>Type</label>
			<form:select path="type" cssClass="form-control">
				<form:options items="${devicetypes}"/>
			</form:select>
		</div>
		
		<div class="form-group">
			<label>Description</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:textarea path="description" cssClass="form-control"/>
			</div>
		</div>
		
		<hr/>
		
		<div id="observed-properties">
			<label>Resource's Observed Properties</label>
			<div class="form-group">
				<input type="button" class="btn btn-primary" id="add-property" value="Add Resource's Observed Property">
			</div>
		</div>
		
		<hr/>
		
		<div id="location">
			<div class="form-group">
				<label>Resource's Location</label>
				<form:select path="location" cssClass="form-control">
					<form:option value="None"></form:option>
					<form:options items="${locations}"/>
				</form:select>
			</div>
		</div>
	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Save"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>
