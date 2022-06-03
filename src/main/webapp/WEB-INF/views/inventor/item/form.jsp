<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="inventor.item.list.label.name" path="name"/>
	<acme:input-textbox code="inventor.item.list.label.code" path="code" placeholder="ABC-123-A"/>
	<acme:input-textbox code="inventor.item.list.label.technology" path="technology"/> 	
	<acme:input-textarea code="inventor.item.list.label.description" path="description"/>
	
	<jstl:if test="${!draftMode}">
		<acme:input-textbox code="any.item.list.label.itemType" path="itemType"/>
	</jstl:if>
	
	<jstl:if test="${(itemType == 'TOOL' && draftMode) || itemType == null}">
		<acme:input-select path="itemType" code="inventor.item.list.label.itemType"> 
			<acme:input-option code="TOOL" value="TOOL" selected="true"/>
			<acme:input-option code="COMPONENT" value="COMPONENT"/>
		</acme:input-select>
	</jstl:if>
	
	<jstl:if test="${itemType == 'COMPONENT' && draftMode}">
		<acme:input-select path="itemType" code="inventor.item.list.label.itemType"> 
			<acme:input-option code="TOOL" value="TOOL"/>
			<acme:input-option code="COMPONENT" value="COMPONENT" selected="true"/>
		</acme:input-select>
	</jstl:if>
	
	
	 
	<acme:input-money code="inventor.item.list.label.retailPrice" path="retailPrice"/> 
	<acme:input-textarea code="inventor.item.list.label.moreInfo" path="moreInfo"/> 
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish')}">
		<jstl:choose>
		<jstl:when test="${draftMode == true}">	
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update"/>
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish"/>
			</jstl:when>
			<jstl:when test="${draftMode == false}">	
			<jstl:choose>
				<jstl:when test="${chimpum==null }">
					<acme:button code="inventor.item.form.button.create-chimpum" action="/inventor/chimpum/create?itemId=${itemId}"/>
				</jstl:when>
					<jstl:when test="${chimpum!=null }">
					<acme:button code="inventor.item.form.button.show-chimpum" action="/inventor/chimpum/show?id=${chimpum}"/>
				</jstl:when>
			</jstl:choose>
			</jstl:when>
			</jstl:choose>	
		</jstl:when>
		
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form> 