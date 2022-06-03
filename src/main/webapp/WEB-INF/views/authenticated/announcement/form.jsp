<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>


<acme:form>
	<acme:input-moment code="authenticated.announcement.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="authenticated.announcement.label.title" path="title"/>
	<acme:input-textarea code="authenticated.announcement.label.body" path="body"/>
	<acme:input-textbox code="authenticated.announcement.label.critical" path="critical"/>
	<acme:input-url code="authenticated.announcement.label.moreInfo" path="moreInfo"/>
	
	<acme:submit test="${command == 'create'}" code="administrator.announcement.form.button.create" action="/administrator/announcement/create"/>
</acme:form> 

<jstl:if test="${!readonly}">
	<acme:input-checkbox code="inventor.item.form.label.confirmation" path="confirmation"/>
	<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create"/>
</jstl:if>