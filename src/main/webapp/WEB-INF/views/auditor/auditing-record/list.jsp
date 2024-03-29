<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditingRecord.list.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.auditingRecord.list.assessment" path="assessment" width="60%"/>
	<acme:list-column code="auditor.auditingRecord.list.mark" path="mark" width="20%"/>	
</acme:list>

	<acme:button test = "${draftMode == true && showCreate}"  code="auditor.auditingRecord.list.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
	<acme:button test = "${draftMode == false && showCreate && !isAuditingRecordsEmpty}"  code="auditor.auditingRecord.list.button.create-correction" action="/auditor/auditing-record/create?auditId=${auditId}"/>
