<%@ page contentType="text/html;charset=ISO-8859-1" %>
<g:render template="../irUtils/breadCrumbs" model="[breadcrumbs:breadcrumbs]"/>

<script type="text/javascript">
	$(function() {
		$( "#searchTabs" ).tabs();
	});
</script>

<script type="text/javascript">
	$(function() {
		$( "#XsearchTabs" ).tabs();
	});
</script>

<g:if test="${!params.contentId}">
	<div id="resultOrItem" class="block tab-block">
		<div class="header tabbed"> 
			<g:message code="search.header" default="zoek"/>
		</div>
		<div class="pccTabs">
			<div id="searchTabs">
				<ul class="">
					<li><a href="#bestViewedContent">
						<g:message code="preSearch.BestViewed.label" default="kennisbank" />
					</a></li>
					<li><a href="#bestViewedAnswers">
						<g:message code="preSearch.content.AccVacBestViewed.label" default="vragen" />
					</a></li>
					<li><a href="#bestViewedProducts">
						<g:message code="preSearch.content.ImpactivePdcBestViewed.label" default="producten" />
					</a></li>
					<li><a href="#bestProducts">
						<g:message code="preSearch.ZaakSysteemProduct.mostApplied.label" default="aanvragen" />
					</a></li>
				</ul>		
				<div id="bestViewedContent" class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
					<g:render template="searchForm"/>
					<g:render template="searchResultDetails" model="[searchResult:searchResult]"/> 							
				</div>
				<div id="bestViewedAnswers" class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
					<g:render template="bestViewedContent" model="[cntType:'content.AccVac']"/>
				</div>
				<div id="bestViewedProducts" class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
					<g:render template="bestViewedContent" model="[cntType:'content.ImpactivePdc']"/>
				</div>
				<div id="bestProducts" class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
				</div>
			</div> <!--  id searchTab -->
		</div> <!-- pccTabas -->
	</div> <!-- block -->         
</g:if>
        
<!--tonen van 1 item uit resultaat lijst-->
<g:if test="${params.contentId}">
	<g:render template="../pccContent/showContent" model="[fromSearch:'on']"/>
</g:if> <!-- id -->

