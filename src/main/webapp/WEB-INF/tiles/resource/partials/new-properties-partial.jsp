<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="form-group" id="property-row${propertiesnumber}">
	<div class="row">
		<div class="col-md-1">
			<label>Observed Property</label>
		</div>
		<div class="col-md-9">
			<form:select path="resource.properties[${propertiesnumber}]" cssClass="form-control select2">
				<form:options items="${observedproperties}"/>
			</form:select>
		</div>
		<div class="col-md-2">
			<input type="button" value="Remove" onclick="removePropertyRow(${propertiesnumber})" class="btn btn-danger">
		</div>
	</div>
</div>