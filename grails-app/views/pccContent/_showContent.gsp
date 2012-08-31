<a name="answer" id="answer"></a>
<script type="text/javascript">
	$(function() {
		<%--location.href="#answer";--%>
		$( "#tabs" ).tabs();
	});
</script>

<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="content.ContentService" %>
<%
    def contentService = grailsApplication.classLoader.loadClass('content.ContentService').newInstance()
%>

<g:set var="contentId" value="${contentId?contentId:params.contentId }"/>
<g:set var="contentInstance" value="${content.PccContent.get(contentId)}"/>
<g:set var="contentClass" value="${ClassUtils.getShortName(contentInstance.getClass())}"/>

<g:set var="verwijzingen" value="${contentInstance?.itemValues || contentInstance?.externalLinks || contentInstance?.referencingContent}"/>
<sec:ifLoggedIn>
	<g:set var="internalInformation" value="${contentService.getInternalInformation(contentInstance)}"/>
</sec:ifLoggedIn>


<div id="contentPart">
	<div class="dialog">
			<g:if test="${!editMode}"> 
				<g:render template="/contentSearch/rating" model="[contentInstance:contentInstance]"/>
			</g:if>
	
			<div class="block tab-block" id="tabinterface-1">
			<p class="header tabbed">
				${contentService.getHeader(contentInstance)}
			</p>
				<div id="tabs">
				
					<ul>
					    <g:each var="tabWithInfo" in="${contentService.getTabsWithInfo(contentInstance)}">
							<li><a href="#${tabWithInfo.tabName}">${tabWithInfo.tabName}</a></li>
						</g:each>
						<g:if test="${verwijzingen}">
							<li><a href="#meer">Meer</a></li>
						</g:if>
						<g:if test="${internalInformation}">
							<li><a href="#internalInfo">Intern</a></li>
						</g:if>
						<li><a href="#doorsturen">Doorsturen</a></li>
					</ul>
				    <g:each var="tabWithInfo" in="${contentService.getTabsWithInfo(contentInstance)}">
						<div class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom" id="${tabWithInfo.tabName}">${tabWithInfo.tabContent}</div>
					</g:each>
					<g:if test="${verwijzingen}">
						<div id="meer" class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
		    				<!--Verwijzing naar referenties uit ItemValue-->
				    		<g:if test="${contentInstance?.itemValues}">
				    			<div class="pccContentPart">
			      					<h2><g:message code="content.ItemValueType.label" default="verwijzingen naar groepen" /></h2>
		    	  					<g:set var="prevType" value=""/>
		        	  				<g:each var="itemValueInstance" in="${contentInstance?.itemValues.sort{q->q.itemValueType; q.toString()}}" status="index">
	    	      						<g:if test="${!prevType.equals(itemValueInstance.itemValueType)}">
	        	  							<g:set var="prevType" value="${itemValueInstance.itemValueType}"/>
		        	    					<h3><g:message code="content.ItemValueType.${itemValueInstance.itemValueType}" default="verwijzing ${itemValueInstance.itemValueType}" /></h3>
	          							</g:if>
	          							<div class="pccContentLink">
											<g:link controller="itemValue" action="navigatie" id="${itemValueInstance.id}" params="[navType:itemValueInstance.itemValueType]">
												${itemValueInstance.description}
											</g:link>
										</div>          	
			          				</g:each>
	        	  				</div>
		    				</g:if>
		    				
		    				<!--Verwijzing naar externe Links-->
		    				<g:if test="${contentInstance?.externalLinks}">
			    				<div class="pccContentPart">
		    	  					<h2><g:message code="content.ExternalLinkType.label" default="externe verwijzingen" /></h2>	      
			      					<g:set var="prevType" value=""/>
		          					<g:each var="externalLinkInstance" in="${contentInstance?.externalLinks.sort{q->q.externalLinkType; q.toString()}}" status="index">
	    	      						<g:if test="${!prevType.equals(externalLinkInstance.externalLinkType)}">
	        	  							<g:set var="prevType" value="${externalLinkInstance.externalLinkType}"/>
		        	    					<h3><g:message code="content.ExternalLinkType.${externalLinkInstance.externalLinkType}" default="verwijzing ${externalLinkInstance.externalLinkType}" /></h3>
		          						</g:if>
		          						<div class="pccContentLink">
		          							<a href="${externalLinkInstance.url}" target="new">${externalLinkInstance.description}</a>
		          						</div>
		        	  				</g:each>
								</div>
							</g:if>
													
	    					<!--Verwijzing naar andere content-->
	    					<g:if test="${contentInstance?.referencingContent}">
	    						<div class="pccContgentPart">
	    							<h2><g:message code="content.references.label" default="verwijzingen naar content" /></h2>	      
	    							<g:set var="prevType" value=""/>
	            					<g:each var="referencedContentInstance" in="${contentInstance?.referencingContent.referencedContent.sort{q->q.class; q.toString()}}" status="index">
		          						<g:if test="${!prevType.equals(referencedContentInstance.class)}">
		          							<g:set var="prevType" value="${referencedContentInstance.class}"/>
				          					<g:set var="refClassName" value="${ClassUtils.getShortName(referencedContentInstance.getClass()) }"/>
					            			<h3><g:message code="content.references.${refClassName}.label" defaultX="verwijzing ${refClassName}" /></h3>
			    	      				</g:if>
		    	    	    
							            <!-- remoteLink doesn't work with rateable & GrailsUI popup -->
						    	        <!-- 
										<g:remoteLink controller="pccContent" action="showRelatedContent" params="[contentId:referencedContentInstance.id]" update="contentPart">
			            					${contentService.getHeader(referencedContentInstance)}
										</g:remoteLink>
						 				-->	            
										<div class="pccContentLink">
											<g:link class="${refClassName}" controller="contentSearch" action="search" params="[contentId:referencedContentInstance.id, q:params.q, offset:offset]" fragment="answer">
						            			${contentService.getHeader(referencedContentInstance)}
											</g:link>
										</div>
		        					</g:each>
	        					</div> <!-- pccContgentPart -->
	    					</g:if> <!-- referencing content -->
						</div> <!-- meer -->

						<!-- interne informatie -->
						<g:if test="${internalInformation}">
							<div id="internalInfo"  class="tabbertab ui-tabs-panel ui-widget-content ui-corner-bottom">
								<div class="internalContent">
									${internalInformation}
								</div>
							</div>
						</g:if>
						
					</g:if> <!-- verwijzingen -->					
					
					<div id="doorsturen">
						<g:render template="/pccContent/mailContent" model="[contentInstance: contentInstance]"/> 
					</div>
					
				</div> <!-- Einde tabbladen -->
				<div class="block inner-block">
			  	
					<!--  things to do when a regular user finds this content -->
					<g:if test="${!editMode}">
						${contentService.incrementViewCount(contentInstance)}
						<g:render template="../pccContent/showFoundItemButtons" model="[contentInstance:contentInstance, fromSearch:fromSearch]" />
			
						<!--commenting-->
						<div id="pccComment" class="kccComment">
				  			<g:render template="../comment/post/commentOnItem" model="[objectId:contentInstance.id, objectDomain:contentInstance.getClass().getName(),divid:'pccComment']"/>
						</div>
					</g:if>
				</div> <!--  block onder tabs -->
			</div> <!--  tabblock -->
	</div>
</div>
