<%@ page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.patronage-report.form.label.sequenceNumber" path="sequenceNumber" readonly="true"/>
	<acme:input-moment code="inventor.patronage-report.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textbox code="inventor.patronage-report.form.label.memorandum" path="memorandum"/>
	<acme:input-url code="inventor.patronage-report.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:if test="${command== 'create' }">
	<acme:input-checkbox code="inventor.patronageReport.form.label.confirm" path="confirm"/>
	<acme:submit code="inventor.patronageReport.list.button.create" action="/inventor/patronage-report/create?patronageId=${patronageId}"/>
	</jstl:if>
</acme:form>  