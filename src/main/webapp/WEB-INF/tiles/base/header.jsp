<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="path" value="${pageContext.request.contextPath}" scope="request"/>

<div class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle Navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="${path}" class="navbar-brand logo"> <img
				src="${path}/static/novel/img/logo-white.svg" alt="" class="img-responsive"> <span>Qo<strong>Disco</strong></span>
			</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${path}">Dashboard</a></li>
				<li><a href="http://consiste.dimap.ufrn.br/projects/qodisco">Documentation</a></li>
			</ul>
			<sec:authorize access="!isAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${path}/signin">Sign In</a></li>
					<li><a href="${path}/user">Sign Up</a></li>
				</ul>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<form role="form" id="logout_form" action="${path}/signout" method="post">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#" style="cursor: default;">Welcome, <sec:authentication property="principal.username"/></a></li>
							<li><a href="javascript:{}" onclick="document.getElementById('logout_form').submit(); return false;">Sign Out</a></li>
					</ul>
				</form>
			</sec:authorize>
		</div>
	</div>
</div>