<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.toolkit.list.label.code" path="code" placeholder="ABC-123-A"/>
	<acme:input-textbox code="inventor.toolkit.list.label.title" path="title"/> 
	<acme:input-textarea code="inventor.toolkit.list.label.description" path="description"/>
	<acme:input-textarea code="inventor.toolkit.list.label.assemblyNotes" path="assemblyNotes"/>
	<acme:input-textarea code="inventor.toolkit.list.label.moreInfo" path="moreInfo"/>  
	<acme:input-money code="inventor.toolkit.list.label.retailPrice" path="retailPrice" readonly="true"/>  
	
		<jstl:choose>	 
		<jstl:when test="${command == 'show' && draftMode == false}">
			<acme:button code="inventor.toolkit.form.button.items" action="/inventor/quantity/list?masterId=${id}"/>			
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && draftMode == true}"> 
			<acme:button code="inventor.toolkit.form.button.items" action="/inventor/quantity/list?masterId=${id}"/>
			<acme:button code="inventor.quantity.form.button.create-quantity" action="/inventor/quantity/create?masterId=${id}"/>
			<acme:submit code="inventor.toolkit.form.button.update" action="/inventor/toolkit/update"/>
			<acme:submit code="inventor.toolkit.form.button.delete" action="/inventor/toolkit/delete"/>
			<jstl:if test="${numQuantities > 0}">
				<acme:submit code="inventor.toolkit.form.button.publish" action="/inventor/toolkit/publish"/>
			</jstl:if>
		</jstl:when>
		
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.toolkit.form.button.create" action="/inventor/toolkit/create"/>
		</jstl:when>		
	</jstl:choose>

</acme:form>