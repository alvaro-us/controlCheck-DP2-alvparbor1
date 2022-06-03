<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="any.item.list.label.name" path="name"/>
	<acme:input-textbox code="any.item.list.label.code" path="code"/>
	<acme:input-textbox code="any.item.list.label.technology" path="technology"/> 	
	<acme:input-textarea code="any.item.list.label.description" path="description"/> 
	<acme:input-textbox code="any.item.list.label.itemType" path="itemType"/>
	<acme:input-money code="any.item.list.label.retailPrice" path="retailPrice"/> 
	
	<jstl:if test="${isExchange}">
			<acme:input-money code="any.item.list.label.exchange" path="exchange"/>
	</jstl:if>	
	<acme:input-textarea code="any.item.list.label.moreInfo" path="moreInfo"/> 
</acme:form>