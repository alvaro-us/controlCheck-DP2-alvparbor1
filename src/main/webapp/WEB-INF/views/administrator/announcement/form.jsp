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
	<acme:input-textbox code="authenticated.announcement.label.title" path="title"/>
	<acme:input-textarea code="authenticated.announcement.label.body" path="body"/>
	<acme:input-checkbox code="authenticated.announcement.label.critical" path="critical"/>
	<acme:input-url code="authenticated.announcement.label.moreInfo" path="moreInfo"/>
	<acme:input-checkbox code="authenticated.announcement.label.confirmation" path="confirmation"/>
	
	<acme:submit code="administrator.announcement.form.button.create" action="/administrator/announcement/create"/>
</acme:form> 