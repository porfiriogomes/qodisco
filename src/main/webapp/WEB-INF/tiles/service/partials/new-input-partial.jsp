<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="input-row${inputnumber}">
	<div class="form-group">
		<label for="name">Input's Name</label>
		<form:input path="service.inputs[${inputnumber}].name" cssClass="form-control"/>
	</div>
	<div class="form-group">
		<label for="name">Input's Type</label>
		<form:select path="service.inputs[${inputnumber}].type" cssClass="form-control">
			<form:options items="${units}"/>
		</form:select>
	</div>
	<div class="form-group">
		<input type="button" value="Remove" onclick="removeInputRow(${inputnumber})" class="btn btn-danger">
	</div>
</div>