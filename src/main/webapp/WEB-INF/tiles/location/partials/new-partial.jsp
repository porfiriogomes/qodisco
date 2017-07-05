<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="form-group">
	<label>Location is a?</label>
	<form:select path="type" cssClass="form-control">
		<form:options items="${fixedstructures}"/>
	</form:select>
</div>

<div class="form-group">
	<label>Located in?</label>
	<form:select path="parent" cssClass="form-control">
		<form:option value="None"/>
		<form:options items="${locations}"/>
	</form:select>
</div>

<div class="form-group">
	<label for="name">Name</label>
	<div class="input-group">
		<div class="input-group-addon">
			<span class="fa fa-genderless"></span>
		</div>
		<form:input path="name" type="text" cssClass="form-control" placeholder="Enter a name for the location."/>
	</div>
</div>

<div class="form-group row">
	<div class="col-md-4">
		<label for="name">Latitude</label>
		<div class="input-group">
			<div class="input-group-addon">
				<span class="fa fa-genderless"></span>
			</div>
			<form:input path="latitude" type="text" cssClass="form-control"/>
		</div>
	</div>
	<div class="col-md-4">
		<label for="name">Longitude</label>
		<div class="input-group">
			<div class="input-group-addon">
				<span class="fa fa-genderless"></span>
			</div>
			<form:input path="longitude" type="text" cssClass="form-control"/>
		</div>
	</div>
	<div class="col-md-4">
		<label for="name">Altitude</label>
		<div class="input-group">
			<div class="input-group-addon">
				<span class="fa fa-genderless"></span>
			</div>
			<form:input path="altitude" type="text" cssClass="form-control"/>
		</div>
	</div>
</div>