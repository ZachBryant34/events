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
<title>Events</title>
</head>
<body>
	<a href="/logout">Logout</a>
	<h1>Welcome ${user.firstName}</h1>
	<div>
		<h3>Here are some of the events in your state:</h3>
		<table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Date</th>
                    <th scope="col">Location</th>
                    <th scope="col">Host</th>
                    <th scope="col">Action / Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${eveIn}" var="event">
                <tr>
                    <td><a href="events/${event.id}">${event.name}</a></td>
                    <td><c:out value="${event.date}"/></td>
                    <td><c:out value="${event.city}"/></td>
                    <td><c:out value="${event.host.firstName}"/></td>
                    <td>
                    <c:choose>
				         <c:when test = "${event.host.id == user.id}">
				            <a href="/events/${event.id}/edit">Edit</a> 
				         </c:when>
				         
				         <c:when test = "${!event.attendees.contains(user)}">
				            <a href="/events/${event.id}/join">Join</a>
				         </c:when>
				         
				         <c:otherwise>
				            Joining 
				         </c:otherwise>
				      </c:choose>
                    <c:choose>
				         <c:when test = "${event.host.id == user.id}">
				            <form action="/events/${event.id}" method="post">
							    <input type="hidden" name="_method" value="delete">
							    <input type="submit" value="Delete">
							</form> 
				         </c:when>
				         
				         <c:when test = "${!event.attendees.contains(user)}">
				            
				         </c:when>
				         
				         <c:otherwise>
				            <a href="/events/${event.id}/cancel">Cancel</a>
				         </c:otherwise>
				      </c:choose>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	<div>
		<h3>Here are some of the events in other state:</h3>
		<table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Date</th>
                    <th scope="col">Location</th>
                    <th scope="col">State</th>
                    <th scope="col">Host</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${eveOut}" var="eve">
                <tr>
                    <td><a href="events/${eve.id}">${eve.name}</a></td>
                    <td><c:out value="${eve.date}"/></td>
                    <td><c:out value="${eve.city}"/></td>
                    <td><c:out value="${eve.state}"/></td>
                    <td><c:out value="${eve.host.firstName}"/></td>
                    <td><a href="/events/${eve.id}/join">Join</a></td>
                </tr>
                </c:forEach>
            </tbody>
        </table> 
	</div> 
	<div>
		<h2>Create an Event</h2>
		<form:form action="/events" method="post" modelAttribute="event">
		<p>
	        <form:label path="name">Name:</form:label>
	        <form:errors path="name"/>
	        <form:input path="name"/>
	    </p>
	    <p>
	        <form:label path="date">Date:</form:label>
	        <form:errors path="date"/>
	        <form:input type="date" path="date"/>
	    </p>
	    <p>
	        <form:label path="city">Location:</form:label>
	        <form:errors path="city"/>
	        <form:input path="city"/>
	    </p>
		<p>
			<form:select path="state">			
				<option value="AL">AL</option>
				<option value="AK">AK</option>
				<option value="AR">AR</option>	
				<option value="AZ">AZ</option>
				<option value="CA">CA</option>
				<option value="CO">CO</option>
				<option value="CT">CT</option>
				<option value="DC">DC</option>
				<option value="DE">DE</option>
				<option value="FL">FL</option>
				<option value="GA">GA</option>
				<option value="HI">HI</option>
				<option value="IA">IA</option>	
				<option value="ID">ID</option>
				<option value="IL">IL</option>
				<option value="IN">IN</option>
				<option value="KS">KS</option>
				<option value="KY">KY</option>
				<option value="LA">LA</option>
				<option value="MA">MA</option>
				<option value="MD">MD</option>
				<option value="ME">ME</option>
				<option value="MI">MI</option>
				<option value="MN">MN</option>
				<option value="MO">MO</option>	
				<option value="MS">MS</option>
				<option value="MT">MT</option>
				<option value="NC">NC</option>	
				<option value="NE">NE</option>
				<option value="NH">NH</option>
				<option value="NJ">NJ</option>
				<option value="NM">NM</option>			
				<option value="NV">NV</option>
				<option value="NY">NY</option>
				<option value="ND">ND</option>
				<option value="OH">OH</option>
				<option value="OK">OK</option>
				<option value="OR">OR</option>
				<option value="PA">PA</option>
				<option value="RI">RI</option>
				<option value="SC">SC</option>
				<option value="SD">SD</option>
				<option value="TN">TN</option>
				<option value="TX">TX</option>
				<option value="UT">UT</option>
				<option value="VT">VT</option>
				<option value="VA">VA</option>
				<option value="WA">WA</option>
				<option value="WI">WI</option>	
				<option value="WV">WV</option>
				<option value="WY">WY</option>				
			</form:select>
		</p>
    <input type="submit" value="Create"/>
	</form:form>
	</div>
</body>
</html>