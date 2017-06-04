<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> <!--常用函数标签库-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h2>Person Management</h2>
		<a href="new">Add New Person</a>
		&nbsp;&nbsp;&nbsp;
		<a href="list">List All People</a>
	</center>
	<div align="center">
		<c:if test="${person!=null}">
				<form action="update" method="post">
		</c:if>
		<c:if test="${person==null}">
				<form action="insert" method="post">
		</c:if>
		<table>
			 <caption>
            	<h3>
            		<c:if test="${person != null}">
            			Edit Pereson
            		</c:if>
            		<c:if test="${person == null}">
            			Add New Person
            		</c:if>
            	</h3>
            </caption>
            	<c:if test="${person != null}">
        			<input type="hidden" name="id" value="<c:out value='${person.id}' />" />
        		</c:if>   
			<tr>
				<td>ID</td>
				<td>
					<input type="text" name="id" value=<c:out value='${person.id}'/>/>
				</td>
			</tr>
			<tr>
				<td>NAME</td>
				<td>
					<input type="text" name="name" value=<c:out value='${person.name}'/>/>
				</td>
			</tr>
			<tr>
				<td>AGE</td>
				<td>
					<input type="text" name="age" value=<c:out value='${person.age}'/>/>
				</td>
			</tr>
			  <tr>
            	<td colspan="2" align="center">
            		<input type="submit" value="Save" />
            	</td>
            </tr>
		</table>
	</div>
</body>
</html>