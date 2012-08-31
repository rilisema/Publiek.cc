<div id="cmntItem${cmnt.id}">
	<p class="comment">
		<g:message code="comment.header" args="[cmnt.createDate, cmnt.poster?cmnt.poster:'anonymus']" /><br>
		<b>${cmnt.comment.replace('\n','<br>')}</b>

		<sec:ifAnyGranted roles="ROLE_ADMIN">
			<g:set var="isAdmin" value="${true}"/>
		</sec:ifAnyGranted>
		
		<g:if test="${(request.remoteUser && request.remoteUser==cmnt.poster) || isAdmin}">
			<br>
			<g:remoteLink controller="comment" class="deleteComment" action="commentDelete" params="[cmntId:cmnt.id,objectId:objectId,objectDomain:objectDomain, divid:divid]" update="${divid}"><g:message code="comment.delete.label" default="delete" /></g:remoteLink>
			<g:remoteLink controller="comment" class="editComment" action="commentEdit"   params="[cmntId:cmnt.id,objectId:objectId,objectDomain:objectDomain, divid:divid, doForm:'true']" update="cmntItem${cmnt.id}"><g:message code="comment.edit.label" default="edit" /></g:remoteLink><br>
      	</g:if>
	</p>
</div>