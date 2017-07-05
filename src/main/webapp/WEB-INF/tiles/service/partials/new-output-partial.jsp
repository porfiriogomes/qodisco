<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="output-row${outputnumber}">
	<div class="form-group">
		<label for="name">Output's Name</label>
		<form:input path="service.outputs[${outputnumber}].name" cssClass="form-control"/>
	</div>
	<div class="form-group">
		<label for="name">Output't Type</label>
		<form:select path="service.outputs[${outputnumber}].type" cssClass="form-control">
			<form:options items="${units}"/>
		</form:select>
	</div>
	<div class="form-group">
		<input type="button" value="Remove" onclick="removeOutputRow(${outputnumber})" class="btn btn-danger">
	</div>
</div>