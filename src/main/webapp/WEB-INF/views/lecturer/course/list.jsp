<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>




<acme:list>
	<acme:list-column code="lecturer.course.list.label.code" path="code" width="10%"/>
	<acme:list-column code="lecturer.course.list.label.title" path="title" width="30%"/>
	<acme:list-column code="lecturer.course.list.label.retailPrice" path="retailPrice" width="10%"/>
	<acme:list-column code="lecturer.course.list.label.courseType" path="courseType" width="10%"/>
	
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="lecturer.course.list.button.create" action="/lecturer/course/create"/>
</jstl:if>	
