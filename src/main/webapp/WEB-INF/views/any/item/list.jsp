<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>



<jstl:choose>
	<jstl:when test="${acme:anyOf(command, 'show, list-all')}">
		<acme:list>
			<acme:list-column code="any.item.list.label.name" path="name"
				width="10%" />
			<acme:list-column code="any.item.list.label.code" path="code"
				width="10%" />
			<acme:list-column code="any.item.list.label.technology"
				path="technology" width="10%" />



		</acme:list>
	</jstl:when>

	<jstl:when test="${command == 'list-items-by-toolkit'}">
		<acme:list>
			<acme:list-column code="any.item.list.label.name" path="name"
				width="10%" />
			<acme:list-column code="any.item.list.label.code" path="code"
				width="10%" />
			<acme:list-column code="any.item.list.label.technology"
				path="technology" width="10%" />


			<acme:list-column code="any.item.list.label.amounts" path="amounts"
				width="10%" />

		</acme:list>
	</jstl:when>
</jstl:choose>