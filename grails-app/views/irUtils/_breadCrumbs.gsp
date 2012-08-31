<g:set var="maxCrumbs" value="${4}"/>
<div id="breadcrumb-wrap" class="ui-corner-all">
	<div id="breadcrumb">
		<g:each var="breadcrumb" in="${breadcrumbs}" status="index">
						
			<g:if test="${index==0}">
				<g:remoteLink class="prev home" controller="${breadcrumb.ctrl}" action="${breadcrumb.action}" params="${breadcrumb.pars}" update="${breadcrumb.update}">${breadcrumb.msg}</g:remoteLink>
			</g:if>
			<g:elseif test="${index+1<breadcrumbs.size()}" >
				<g:set var="crumbMsg" value="${breadcrumb.msg}"/>
				<g:if test="${breadcrumbs.size()>maxCrumbs && (breadcrumbs.size() - index+1) > maxCrumbs}">
					<g:set var="crumbMsg" value="..."/>
				</g:if>
				<g:remoteLink class="prev" controller="${breadcrumb.ctrl}" action="${breadcrumb.action}" params="${breadcrumb.pars}" update="${breadcrumb.update}">${crumbMsg}</g:remoteLink>
			</g:elseif>
			<g:else>
				<span class="prev">
					<g:remoteLink controller="${breadcrumb.ctrl}" action="${breadcrumb.action}" params="${breadcrumb.pars}" update="${breadcrumb.update}">${breadcrumb.msg}</g:remoteLink>
				</span>
			</g:else>
			
			<g:if test="${breadcrumbs.size()==1 || index+2 == breadcrumbs.size()}">		
				<span class="next pijl"></span>	 <!--  pijl grijs-wit -->
			</g:if>
			<g:elseif test="${index+1<breadcrumbs.size()}">
				<span class="grey pijl"></span>	<!--  pijl grijs-grijs -->
			</g:elseif>
									
		</g:each>
	</div>
</div>