<%@ page import="org.springframework.util.ClassUtils" %>

<!-- breadcrumbs -->
<g:render template="../irUtils/breadCrumbs" model="[breadcrumbs:themeBreadcrumbs]"/>	
<div id="valueList">
	
	<div class="block">
		<div class="header">
			<g:if test="${navItems}">
				<g:message code="${params.navType}.found.themes.label" default="gevonden ${params.navType}"/>
			</g:if> 
			<g:if test="${moederVraag?.valueReferences}">
				<g:message code="${params.navType}.found.content.label" default="gevonden ${params.navType}"/>
			</g:if> 			
		</div>
		<div class="blockcontent">

			<g:if test="${navItems}">
			<!-- list of themes -->
				<table>
					<tbody>
						<g:each in="${navItems}" status="i" var="aNavItem">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td class="value">
									<g:remoteLink action="valueList" id="${aNavItem.id}" params="[breadcrumbs:breadcrumbs?breadcrumbs:'',navType:params.navType]" update="pccContent">
										${fieldValue(bean: aNavItem, field: "description")}
									</g:remoteLink>                	
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</g:if>
        
			<g:if test="${moederVraag?.valueReferences}">
			<!-- list of content -->    
				<table>            
              		<g:each var="refItem" in="${moederVraag.valueReferences.sort{q->q.toString()}}" status="index">
						<g:set var="result" value="${content.PccContent.get(refItem.id)}"/>
						<g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
						<g:set var="offset"    value="${params.offset?params.offset:'0'}"/>
						<g:set var="selectedItem" value="${result.id.equals(params.contentId?.toLong())}"/>
						<tr  class="${(index % 2) == 0 ? 'odd' : 'even'} ${selectedItem?'selected':''}">
							<td class="value">
								<div class="resultItem">
									<g:remoteLink class="${className}" controller="itemValue" action="showContent" params="[contentId:result.id]" update="valueList">
										${result.toString().encodeAsHTML()}
									</g:remoteLink>
								</div>
							</td>
						</tr>
					</g:each>              
				</table>

				<!--tonen van 1 item uit resultaat lijst-->
				<div id="resultAnItem">
					<g:if test="${params.contentId}">
						<g:render template="../pccContent/showContent" id="${params.contentId}" model="[fromSearch:'on', breadcrumbs:themeBreadcrumbs]"/>
					</g:if> <!-- id -->
				</div> <!--result item-->			

			</g:if><!--productlist-->
		</div><!-- blockcontent -->
	</div> <!-- block -->
</div> <!-- valueList -->