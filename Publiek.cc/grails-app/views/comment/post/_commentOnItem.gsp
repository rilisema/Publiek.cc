
<!--list of all comments on this item-->
<g:set var="cmntList" value="${pcc.Comment.findAllByObjectDomainAndObjectId(objectDomain, objectId, [sort:'createDate', order:'desc'])}"/>

<g:if test="${cmntList}">
	<div id="commentList">
		<h3><g:message code="comment.label" default="comment" /></h3>
		<g:render template="../comment/post/listItem" var="cmnt" collection="${cmntList}" model="[objectId:objectId, objectDomain:objectDomain, divid:divid]" />
	</div>
</g:if>

<!--form for posting new comment-->
<div id="newComment">
  <g:render template="../comment/post/newComment" model="[objectId:objectId, objectDomain:objectDomain, divid:divid]" />
</div>
