<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
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
acme.entities.patronages.PatronageStatus, java.lang.String, java.util.Collections"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<table class="table table-sm" aria-describedby="">	
	<tr>
		<th scope="row">
			<acme:message code="patron.dashboard.form.label.total-number-of-proposed-patronages"/>
		</th>
		<td>
			<acme:print value="${proposedPatronages}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="patron.dashboard.form.label.total-number-of-accepted-patronages"/>
		</th>
		<td>
			<acme:print value="${acceptedPatronages}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="patron.dashboard.form.label.total-number-of-denied-patronages"/>
		</th>
		<td>
			<acme:print value="${deniedPatronages}"/>
		</td>
	</tr>
	
	</table>

<jstl:forEach items="${status}" var="status">
	<h2><acme:message code="patron.dashboard.form.label.average-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${averageBudget.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${status}" var="status">
	<h2><acme:message code="patron.dashboard.form.label.deviation-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${deviationBudget.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${status}" var="status">
	<h2><acme:message code="patron.dashboard.form.label.minimun-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${minimunBudget.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${status}" var="status">
	<h2><acme:message code="patron.dashboard.form.label.maximun-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${maximunBudget.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

