<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

function submitForm() {
	$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
	$('#newqoc-form').submit();
}

</script>

<form:form id="newqoc-form" method="post" action="${pageContext.request.contextPath}/qoc/new" modelAttribute="qoc" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">
		<div class="form-group">
			<label>Name</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:input path="name" type="text" cssClass="form-control" placeholder="Enter a name for the QoC Criterion."/>
			</div>
		</div>
		
		<div class="form-group">
			<label>Description</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:textarea path="description" cssClass="form-control" placeholder="Enter a brief description for your QoC Criterion."/>
			</div>
		</div>
		
		<div class="form-group">
			<label>Direction</label>
			<form:select path="direction" cssClass="form-control">
				<form:option value="INF" selected="selected"/>
				<form:option value="SUP"/>
				<form:option value="UNDEF"/>
			</form:select>
		</div>
		
		<div class="form-group">
			<label>Is Invariant?</label>
			<form:checkbox path="invariant" cssClass="form-control"/>
		</div>
		
		<div class="form-group">
			<label>Unit</label>
			<form:select path="unit" cssClass="form-control">
				<form:options items="${units}"/>
			</form:select>
		</div>
		
		<div class="form-group">
			<label>Minimum Value</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:input path="minimumValue" type="text" cssClass="form-control"/>
			</div>
		</div>

		<div class="form-group">
			<label>Maximum Value</label>
			<div class="input-group">
				<div class="input-group-addon">
					<span class="fa fa-genderless"></span>
				</div>
				<form:input path="maximumValue" type="text" cssClass="form-control"/>
			</div>
		</div>
						
		<div class="form-group">
			<label>Composed by?</label>
			<form:select path="composedBy" cssClass="form-control" multiple="true">
				<form:options items="${qoccriteria}"/>
			</form:select>
		</div>

	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Save"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>
