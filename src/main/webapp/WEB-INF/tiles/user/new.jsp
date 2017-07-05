<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">New User</h2>
		</div>
		<form:form id="newuser-form" method="post" action="${path}/user" modelAttribute="user" role="form">
			<div class="panel-body">
				<div class="form-group">
					<label for="name">Name</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-genderless"></span>
						</div>
						<form:input path="name" type="text" id="name" cssClass="form-control" placeholder="Enter your name. For example, John or Mary"/>
					</div>
					<form:errors path="name" cssClass="text-red"/>
				</div>
				<div class="form-group">
					<label for="username">Username</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-user"></span>
						</div>
						<form:input path="username" id="username" type="text" cssClass="form-control" placeholder="Enter your username"/>
					</div>
					<form:errors path="username" cssClass="text-red"/>
				</div>
				<div class="form-group">
					<label for="name">Email</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-envelope"></span>
						</div>
						<form:input path="email" id="email" type="email" cssClass="form-control" placeholder="Enter your email"/>
					</div>
					<form:errors path="email" cssClass="text-red"/>
				</div>
				<div class="form-group">
					<label for="name">Password</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-lock"></span>
						</div>
						<form:input path="password" type="password" id="password" cssClass="form-control" placeholder="Enter your password" />
					</div>
					<form:errors path="password" cssClass="text-red"/>
				</div>
				<div class="form-group">
					<label for="name">Confirm the Password</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-repeat"></span>
						</div>
						<input id="passwordconfirmation" name="passwordconfirmation" type="password" class="form-control" placeholder="Retype your password">
					</div>
				</div>		
			</div>
			<div class="panel-footer clearfix">
				<input type="submit" value="Create Account"
					class="btn btn-default pull-right">
			</div>
		</form:form>
	</div>
</div>