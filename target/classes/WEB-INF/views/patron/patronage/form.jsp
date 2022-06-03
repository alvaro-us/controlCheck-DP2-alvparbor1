<%@page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
<jstl:if test="${command=='show'}">
<acme:input-textbox code="patron.patronage.form.label.status" path="status" readonly='true'/> 
</jstl:if>
<acme:input-textbox code="patron.patronage.form.label.code" path="code"/> 
<acme:input-textbox code="patron.patronage.form.label.legalStuff" path="legalStuff"/> 
<acme:input-money code="patron.patronage.form.label.budget" path="budget"/> 

<jstl:if test="${isExchange && command=='show'}">
			<acme:input-money code="any.item.list.label.exchange" path="exchange" readonly='true'/>
	</jstl:if>
<acme:input-moment code="patron.patronage.form.label.creationDate" path="creationDate" readonly='true'/> 
<acme:input-moment code="patron.patronage.form.label.startDate" path="startDate"/> 
<acme:input-moment code="patron.patronage.form.label.endDate" path="endDate"/> 
<acme:input-textbox code="patron.patronage.form.label.moreInfo" path="moreInfo"/> 


<jstl:if test="${command == 'create'}">
	<acme:input-select code="patron.patronage.form.label.inventors" path="inventorId">
		<jstl:forEach items="${inventors}" var="inventor">
			<acme:input-option code="${ inventor.getUserAccount().getUsername()}" value="${inventor.getId()}"/>
		</jstl:forEach>
	</acme:input-select>
</jstl:if>

<jstl:choose>
	<jstl:when test="${acme:anyOf(command,'show,update,delete,publish') && published}">
		<acme:button code="patron.patronage.form.button.patron" action="/any/user-account/show?id=${ inventorId }"/>
		<acme:button code="patron.patronage.form.button.patronageReport" action="/patron/patronage-report/list?patronageId=${patronageId}"/>
	</jstl:when>
	<jstl:when test="${acme:anyOf(command,'show,update,delete,publish') && !published}">
	
	<acme:submit code="patron.patronage.form.button.update" action="/patron/patronage/update"/>
	<acme:submit code="patron.patronage.form.button.delete" action="/patron/patronage/delete"/>
	<acme:submit code="patron.patronage.form.button.publish" action="/patron/patronage/publish"/>
	<acme:button code="patron.patronage.form.button.patron" action="/any/user-account/show?id=${ inventorId }"/>
	</jstl:when>

	<jstl:when test="${command=='create'}">
	<acme:submit code="patron.patronage.form.button.create" action="/patron/patronage/create"/>
	</jstl:when>
</jstl:choose>
</acme:form>