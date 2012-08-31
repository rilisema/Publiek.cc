<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="content.ContentService" %>
<%
    def contentService = grailsApplication.classLoader.loadClass('content.ContentService').newInstance()
%>
<div class="list resultItem">
	<table>
		<g:set var="bestViewedItems" value="${contentService.getBestViewedContent(cntType) }"/>
		<g:each in="${bestViewedItems}" status="index" var="contentInstance">
			<g:set var="ctrl" value="${contentInstance.class}"/>
       		<g:set var="className" value="${ClassUtils.getShortName(contentInstance.getClass())}" />
			<g:set var="selectedItem" value="${contentInstance.id.equals(params.id?.toLong()) && ctrl.equals(params.rctrl)}"/>
			<tr class="${(index % 2) == 0 ? 'odd' : 'even'} ${selectedItem?'selected':''}">
				<td class="value">
					<g:remoteLink class="${className}" controller="contentSearch" action="searchResult" params="[contentId:contentInstance.id]" update="pccContent">${index+1} : ${contentService.getHeader(contentInstance)}</g:remoteLink>   
    			</td>
			</tr>
		</g:each>		
	</table>

	<p>
		<g:set var="navType" value="${cntType=='content.AccVac'?'TiO':cntType=='content.ImpactivePdc'?'CustomerQuestion':null}" />
		<g:if test="${navType}">
			<g:remoteLink class="navButton" controller="itemValue" action="valueList" params="[navType:navType]" update="pccContent" fragment="pccTop">
				<g:message code="preSearch.${cntType}.${navType}.label" default="navigeren" />
			</g:remoteLink>		
		</g:if>
	</p>
	
</div>
