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

<%@page language="java"
import="org.springframework.data.util.Pair, java.util.Map, java.util.HashMap,
acme.framework.components.models.Model, java.lang.String, java.util.Collections,
acme.entities.patronages.PatronageStatus"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="administrator.initial-configuration.form.label.defaultCurrency" path="defaultCurrency"/>	
	<acme:input-textbox code="administrator.initial-configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-textbox code="administrator.initial-configuration.form.label.strongSpamTerms" path="strongSpamTerms"/>	
	<acme:input-textbox code="administrator.initial-configuration.form.label.weakSpamTerms" path="weakSpamTerms"/>
	<acme:input-textbox code="administrator.initial-configuration.form.label.strongSpamTreshold" path="strongSpamTreshold"/>	
	<acme:input-textbox code="administrator.initial-configuration.label.weakSpamTreshold" path="weakSpamTreshold"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update')}">
			<acme:submit code="administrator.initial-configuration.form.button.update" action="/administrator/initial-configuration/update"/>
		</jstl:when>
	</jstl:choose>
	
	
</acme:form>

