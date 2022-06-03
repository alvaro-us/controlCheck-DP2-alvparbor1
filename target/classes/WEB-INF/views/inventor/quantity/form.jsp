<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
<jstl:choose>	 
		<jstl:when test="${command == 'show' && showItem}">
			<acme:input-textbox code="inventor.item.list.label.name" path="item.name"/>
			<acme:input-textbox code="inventor.item.list.label.code" path="item.code" placeholder="ABC-123-A"/>
			<acme:input-textbox code="inventor.item.list.label.technology" path="item.technology"/> 	
			<acme:input-textarea code="inventor.item.list.label.description" path="item.description"/>
			<acme:input-textbox code="any.item.list.label.itemType" path="item.itemType"/>
			<acme:input-money code="inventor.item.list.label.retailPrice" path="item.retailPrice"/>
			<jstl:if test="${isExchange}">
				<acme:input-money code="any.item.list.label.exchange" path="exchange"/>
			</jstl:if>	 
			<acme:input-textarea code="inventor.item.list.label.moreInfo" path="item.moreInfo"/> 
			<acme:input-integer code="inventor.quantity.list.label.amount" path="amount"/>
		</jstl:when>
		
		<jstl:when test="${command == 'create'}">
		<jstl:if test="${numItems > 0}">
			<acme:input-select path="item" code="inventor.item.list.label.item">
				<jstl:forEach items="${items}" var="item">
					<jstl:if test="${itemSelected != null}">
						<acme:input-option code="${itemSelected.name} ${itemSelected.code}" value="${itemSelected.id}" selected="true"/>
					</jstl:if>
					<acme:input-option code="${item.name} ${item.code}" value="${item.id}"/>
				</jstl:forEach>
			</acme:input-select>
			<acme:input-integer code="inventor.quantity.form.label.amount" path="amount" />

			<acme:submit code="inventor.quantity.form.button.create" action="/inventor/quantity/create?masterId=${masterId}" />
		</jstl:if>
		
		<jstl:if test="${numItems == 0}">
			<acme:message code="inventor.quantity.no-available-items"/>
		</jstl:if>
		
		</jstl:when>	
		
		<jstl:when test="${(command == 'show' && !showItem) || command == 'update'}">
			
			<acme:input-textbox code="inventor.item.list.label.item" path="itemSelect" readonly="true"/>
	
			
			<acme:input-integer code="inventor.quantity.form.label.amount" path="amount" />

			<acme:submit code="inventor.quantity.form.button.delete" action="/inventor/quantity/delete?masterId=${masterId}" />
			<acme:submit code="inventor.quantity.form.button.update" action="/inventor/quantity/update?masterId=${masterId}" />
		</jstl:when>	
</jstl:choose>

</acme:form> 