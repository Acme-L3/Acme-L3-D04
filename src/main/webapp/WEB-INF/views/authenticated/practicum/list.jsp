<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.practicum.list.label.practicum-code" path="code" width="20%"/>
	<acme:list-column code="authenticated.practicum.list.label.title" path="title" width="70%"/>
	<acme:list-column code="authenticated.practicum.list.label.course-code" path="code" width="10%"/>
</acme:list>