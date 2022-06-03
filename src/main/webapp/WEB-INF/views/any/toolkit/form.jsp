<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="any.toolkit.list.label.code" path="code"/>
	<acme:input-textbox code="any.toolkit.list.label.title" path="title"/> 
	<acme:input-textbox code="any.toolkit.list.label.description" path="description"/>
	<acme:input-textbox code="any.toolkit.list.label.assemblyNotes" path="assemblyNotes"/>
	<acme:input-textbox code="any.toolkit.list.label.moreInfo" path="moreInfo"/>  
	<acme:input-money code="any.toolkit.list.label.retailPrice" path="retailPrice"/>  
	<acme:button code="any.toolkit.form.button.items" action="/any/item/list-items-by-toolkit?masterId=${id}"/>
</acme:form>