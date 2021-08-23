<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <%@ page isErrorPage="true" %> 
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<title><c:out value="${event.name}"/></title>
</head>
<body>
	<a href="/events">Back</a>
	<h1>${event.name}</h1>
	<h4>Host: <c:out value="${event.host.firstName}"/> <c:out value="${event.host.lastName}"/></h4>
	<h4>Date: <c:out value="${event.date}"/></h4>
	<h4>Location:  <c:out value="${event.city}"/>, <c:out value="${event.state}"/></h4>
	<h4>People attending <c:out value="${count}"/></h4>
	<div>
		<table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Location</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${event.attendees}" var="eve">
                <tr>
                    <td><c:out value="${eve.firstName}"/> <c:out value="${eve.lastName}"/></td>
                    <td><c:out value="${eve.city}"/></td>                   
                </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	<div>
		<h2>Message Wall</h2>
		<c:forEach items="${event.messages}" var="message">
			<p><c:out value="${message.user.firstName}"/> <c:out value="${message.user.lastName}"/>: <c:out value="${message.comment}"/></p>
		</c:forEach>
		<form:form action="/events/${event.id}/messages" method="post" modelAttribute="message">
		<p>
	        <form:label path="comment">Add Comment:</form:label>
	        <form:errors path="comment"/>
	        <form:input path="comment"/>
	    </p>
    <input type="submit" value="Submit"/>
	</form:form>
	</div>
</body>
</html>