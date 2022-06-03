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

<h2>
	<acme:message code="administrator.dashboard.form.title.components"/>
</h2>


<table class="table table-sm" aria-describedby="">	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.ratio"/>
		</th>
		<td>
			<acme:print value="${ratioOfItemsWithChimpum}"/>
		</td>
	</tr>
</table>


<h2><acme:message code="administrator.dashboard.form.label.chimpum.average-budget"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${averageBudgetOfChimpumGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>
 

 
	<h2><acme:message code="administrator.dashboard.form.label.chimpum.deviation-budget"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${deviationBudgetOfChimpumGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
 

 
<h2><acme:message code="administrator.dashboard.form.label.chimpum.minimun-budget"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${minimunBudgetOfChimpumGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>
 

 
<h2><acme:message code="administrator.dashboard.form.label.chimpum.maximun-budget"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${maximunBudgetOfChimpumGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>




























<table class="table table-sm" aria-describedby="">	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.total-number-of-components"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfComponents}"/>
		</td>
	</tr>
</table>

<br>

<h2><acme:message code="administrator.dashboard.form.label.average-retailPrice.component"/></h2>
<jstl:forEach items="${technologies}" var="technology">
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${technology}"/>
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${averageRetailPriceOfComponentsGroupedByTechnologyAndCurrency.getOrDefault(Pair.of(technology,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<h2><acme:message code="administrator.dashboard.form.label.deviation-retailPrice.component"/></h2>
	
<jstl:forEach items="${technologies}" var="technology">
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${technology}"/>
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${deviationRetailPriceOfComponentsGroupedByTechnologyAndCurrency.getOrDefault(Pair.of(technology,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<h2><acme:message code="administrator.dashboard.form.label.minimun-retailPrice.component"/></h2>
	
<jstl:forEach items="${technologies}" var="technology">
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${technology}"/>
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${minimunRetailPriceOfComponentsGroupedByTechnologyAndCurrency.getOrDefault(Pair.of(technology,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<h2><acme:message code="administrator.dashboard.form.label.maximun-retailPrice.component"/></h2>
	
<jstl:forEach items="${technologies}" var="technology">
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${technology}"/>
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${maximunRetailPriceOfComponentsGroupedByTechnologyAndCurrency.getOrDefault(Pair.of(technology,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<br>
<hr>

<h2>
	<acme:message code="administrator.dashboard.form.title.tools"/>
</h2>

<table class="table table-sm" aria-describedby="">	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.total-number-of-tools"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTools}"/>
		</td>
	</tr>
</table>

 
<h2><acme:message code="administrator.dashboard.form.label.average-retailPrice"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${averageBudgetOfToolGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>
 

 
	<h2><acme:message code="administrator.dashboard.form.label.deviation-retailPrice"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${deviationBudgetOfToolGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
 

 
<h2><acme:message code="administrator.dashboard.form.label.minimun-retailPrice"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${minimunBudgetOfToolGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>
 

 
<h2><acme:message code="administrator.dashboard.form.label.maximun-retailPrice"/></h2>
<br>
<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${maximunBudgetOfToolGroupedByCurrency.getOrDefault(currency,0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
</table>
 


<br>
<hr>

<h2>
	<acme:message code="administrator.dashboard.form.title.patronages"/>
</h2>


<table class="table table-sm" aria-describedby="">	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.total-number-of-proposed-patronages"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProposedPatronages}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.total-number-of-accepted-patronages"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfAcceptedPatronages}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.total-number-of-denied-patronages"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfDeniedPatronages}"/>
		</td>
	</tr>
	
	</table>

<br>


<jstl:forEach items="${statuses}" var="status">
	<h2><acme:message code="administrator.dashboard.form.label.average-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${averageBudgetOfPatronagesGroupedByStatusAndCurrency.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${statuses}" var="status">
	<h2><acme:message code="administrator.dashboard.form.label.deviation-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${deviationBudgetOfPatronagesGroupedByStatusAndCurrency.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${statuses}" var="status">
	<h2><acme:message code="administrator.dashboard.form.label.minimun-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${minimunBudgetOfPatronagesGroupedByStatusAndCurrency.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<jstl:forEach items="${statuses}" var="status">
	<h2><acme:message code="administrator.dashboard.form.label.maximun-budget-${status}"/></h2>
	<br>
	<table class="table table-sm" aria-describedby="">
		<jstl:forEach items="${currencies}" var="currency">
			<tr>
				<th scope="row">
					<acme:print value="${currency}"/>
				</th>
				<td>
					<acme:print value="${maximunBudgetOfPatronagesGroupedByStatusAndCurrency.getOrDefault(Pair.of(status,currency),0.0)}"/>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</jstl:forEach>

<br>
<br>
<br>
<acme:return/>

