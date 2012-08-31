<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.SearchableUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.lucene.LuceneUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.util.StringQueryUtils" %>
<%@ page import="grails.util.*" %>

<g:set var="haveQuery" value="${params.q?.trim()}" />
<g:set var="haveResults" value="${searchResult?.results}" />

<!--error messages--> <!--TODO : tidy up exception handling below... -->
<g:if test="${parseException}">
	<g:render template="searchError"/>
</g:if> <!--errors-->

<!--No result info-->
<g:if test="${haveQuery && !haveResults && !parseException}">
	<p><g:message code="search.result.noresult" args="[params.q]"  default="no results found"/></p>
</g:if>

<!--suggestion info-->
<g:if test="${searchResult?.suggestedQuery}">
	<p><g:message code="search.suggestion" default="did you mean :"/><g:remoteLink controller="contentSearch" action="searchResult" params="[q: searchResult.suggestedQuery]" update="pccContent">${StringQueryUtils.highlightTermDiffs(params.q.trim(), searchResult.suggestedQuery)}</g:remoteLink>?</p>
</g:if>

<!--show resultset-->
<g:if test="${haveResults}">
	<h2><g:message code="search.result.info" args="[searchResult.total, params.q]"/></h2>
	<div class="list">
		<table>
			<g:each var="result" in="${searchResult.results}" status="index">
				<g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
				<g:set var="offset"    value="${params.offset?params.offset:'0'}"/>
				<g:set var="selectedItem" value="${result.id.equals(params.id?.toLong())}"/>
				<tr  class="${(index % 2) == 0 ? 'odd' : 'even'} ${selectedItem?'selected':''}">
					<td class="value">
						<div class="resultItem">
							<g:remoteLink class="${className}" controller="contentSearch" action="searchResult" params="[contentId:result.id,q:params.q]" update="pccContent">${result.toString().encodeAsHTML()}</g:remoteLink>
						</div>
					</td>
				</tr>
			</g:each>
		</table>
	</div>

	<!--paginate buttons-->
	<div class="paginateButtons">
		<util:remotePaginate controller="contentSearch" action="searchResult" total="${searchResult.total}" update="pccContent" max="10" params="[q: params.q, searchItemsX:params.searchItems]"/>        
	</div>
      
</g:if>

<g:if test="${!haveResults}"> <!--show best viewed items-->
	<g:render template="bestViewedContent"/>
</g:if>
