<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="student.enrolment.form.label.creditCard" path="creditCard"/>
		
	<jstl:choose>
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == false}">
			<acme:button code="student.enrolment.form.button.activity" action="/student/activity/list?enrolmentId=${enrolmentId}"/>
		</jstl:when>	
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.publish" action="/student/enrolment/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>


