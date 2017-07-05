<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	var inputnumber = -1;
	var outputnumber = -1;

	$("#add-input").click(function(){
		inputnumber++;
		var domainSelected = $('#domain-option').find(":selected").val();
		$.get("<%=request.getContextPath()%>/service/addinput", { fieldid: inputnumber, domainselected: domainSelected },
			function(data){
				$("#add-input").before(data);
		});
	});

	$("#add-output").click(function(){
		outputnumber++;
		var domainSelected = $('#domain-option').find(":selected").val();
		$.get("<%=request.getContextPath()%>/service/addoutput", { fieldid: outputnumber, domainselected: domainSelected },
			function(data){
				$("#add-output").before(data);
		});
	});
	
});

function removeInputRow(row){
	$('#input-row'+row).remove();
}

function removeOutputRow(row){
	$('#output-row'+row).remove();
}

function submitForm(){
	$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
	$('#newservice-form').submit();	
}
</script>

<form:form id="newservice-form" method="post" action="${pageContext.request.contextPath}/service/new" modelAttribute="service" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">
		<div class="form-group">
			<label>Name</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:input path="name" type="text" cssClass="form-control" placeholder="Enter a name for the service."/>
			</div>
		</div>
		
		<div class="form-group">
			<label>Exposed by</label>
			<form:select path="resource" cssClass="form-control">
				<form:option value="None"/>
				<form:options items="${resources}"/>
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
			<label>Service's Inputs</label>
			<div class="form-group">
				<input type="button" class="btn btn-primary" id="add-input" value="Add Service's New Input">
			</div>
		</div>
		
		<hr/>
		
		<div id="observed-properties">
			<label>Service's Output</label>
			<div class="form-group">
				<input type="button" class="btn btn-primary" id="add-output" value="Add Service's New Output">
			</div>
		</div>

	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Save"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>
