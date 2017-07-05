<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Sign In</h2>
		</div>
		<form role="form" name="f" action="${path}/authenticate" method="post">
			<div class="panel-body">
				<div class="form-group">
					<label for="name">Username</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-user"></span>
						</div>
						<input name="username" type="text" class="form-control" required="required">
					</div>
				</div>
				<div class="form-group">
					<label for="username">Password</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-lock"></span>
						</div>
						<input name="password" type="password" class="form-control" required="required">
					</div>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<input type="submit" value="Sign In"
					class="btn btn-default pull-right">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
</div>