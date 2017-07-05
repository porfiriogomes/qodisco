<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="path" value="${pageContext.request.contextPath}" scope="request"/>

<title><tiles:insertAttribute name="title" /></title>

<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
<link rel="icon" href="${path}/static/novel/img/favicon.ico">
<link rel="stylesheet" href="${path}/static/vendors/css/bootstrap-cosmo.min.css" rel="stylesheet">
<link rel="stylesheet" href="${path}/static/vendors/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="${path}/static/novel/css/custom.css" rel="stylesheet">
<link href='http://cdn.jsdelivr.net/yasqe/2.2/yasqe.min.css' rel='stylesheet' type='text/css'/>
<link rel="stylesheet" href="${path}/static/vendors/css/github.css">


<script src="${path}/static/vendors/js/jquery.min.js"></script>	

</head>
<body>

	<tiles:insertAttribute name="header"/>

	<div id="main">
		<div id="main-container" class="container">		
			<c:if test="${success_message != null}">
				<div class="alert alert-success" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    	<span aria-hidden="true">&times;</span>
				 	</button>
					${success_message}	
				</div>
			</c:if>

			<div class="row">
				<tiles:insertAttribute name="sidebar"/>
				<tiles:insertAttribute name="content" />
			</div>
		</div>
	</div>
	<tiles:insertAttribute name="footer"/>
	
	
	<tiles:importAttribute name="scripts" toName="scripts"/>
	<c:forEach var="script" items="${scripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>
</body>
</html>