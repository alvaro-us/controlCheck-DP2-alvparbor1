<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.quantity.list.label.name" path="item.name" width="10%"/>
	<acme:list-column code="inventor.quantity.list.label.code" path="item.code" width="10%"/>
	<acme:list-column code="inventor.quantity.list.label.technology" path="item.technology" width="10%"/> 
	<acme:list-column code="inventor.quantity.list.label.amount" path="amount" width="10%" />
</acme:list>  