<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-select code="inventor.patronage.form.label.status" path="status">
		<acme:input-option code="inventor.patronage.form.label.status.proposed" value="PROPOSED" selected="${status == 'PROPOSED'}"/>
		<acme:input-option code="inventor.patronage.form.label.status.accepted" value="ACCEPTED" selected="${status == 'ACCEPTED'}"/>
		<acme:input-option code="inventor.patronage.form.label.status.denied" value="DENIED" selected="${status == 'DENIED'}"/>
	</acme:input-select>
<acme:input-textbox code="inventor.patronage.form.label.code" path="code" readonly="true"/> 
<acme:input-textbox code="inventor.patronage.form.label.legalStuff" path="legalStuff" readonly="true"/> 
<acme:input-money code="inventor.patronage.form.label.budget" path="budget" readonly="true"/> 
<jstl:if test="${isExchange}">
			<acme:input-money code="any.item.list.label.exchange" path="exchange" readonly="true"/>
	</jstl:if>
<acme:input-moment code="inventor.patronage.form.label.creationDate" path="creationDate" readonly="true"/> 
<acme:input-moment code="inventor.patronage.form.label.startDate" path="startDate" readonly="true"/> 
<acme:input-moment code="inventor.patronage.form.label.endDate" path="endDate" readonly="true"/> 
<acme:input-textbox code="inventor.patronage.form.label.moreInfo" path="moreInfo" readonly="true"/> 

<acme:button code="inventor.patronage.form.button.patron" action="/any/user-account/show?id=${ patronId }"/>
<acme:button code="inventor.patronage.form.button.patronageReport" action="/inventor/patronage-report/list?patronageId=${patronageId}"/>

<jstl:if test="${status == 'PROPOSED'}">
	<acme:submit code="inventor.patronage.form.button.update" action="/inventor/patronage/update"/>
</jstl:if>
<acme:button code="inventor.patronageReport.list.button.create" action="/inventor/patronage-report/create?patronageId=${patronageId}"/>
</acme:form>
