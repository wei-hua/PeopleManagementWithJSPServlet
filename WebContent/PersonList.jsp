<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> <!--常用函数标签库-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PersonList</title>
</head>
<body>
	<center>
		<h2>Person Management</h2>
		<a href="new">Add New Person</a>
		&nbsp;&nbsp;&nbsp;
		<a href="list">List All People</a>
	</center>
	<div align="center">
		<table>
			<caption><h3>List Of People</h3></caption>
			<tr>
				<th>ID</th>
				<th>NAME</th>
				<th>AGE</th>
			</tr>
			
			<c:forEach var="person" items="${listPerson}">
				<tr>
					<td><c:out value="${person.id}" /></td>
					<td><c:out value="${person.name}" /></td>
					<td><c:out value="${person.age}" /></td>
					
					<td>
					<a href="edit?id=<c:out value='${person.id}'/>">Edit</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="delete?id=<c:out value='${person.id}'/>">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>