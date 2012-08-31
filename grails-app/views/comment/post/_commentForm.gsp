
<g:javascript>
	$.validator.setDefaults({
		submitHandler: function() { }
	});
	$(function(){
    	$("#commentForm${cid}").validate();
	});
</g:javascript>

<g:if test="${params.cmntId}">
  <g:set var="cmnt" value="${pcc.Comment.get(params.cmntId)}"/>
  <g:set var="cid" value="${params.cmntId}"/>
  <g:set var="objectId" value="${cmnt?.objectId}"/>
  <g:set var="objectDomain" value="${cmnt?.objectDomain}"/>
  <g:set var="type" value="${cmnt?.type}"/>
</g:if>
<g:else>
  <g:set var="cid" value=""/>
</g:else>

<div id="commentForm${cid}">
  <g:form method="post" controller="comment" action="commentSave" >
    <g:hiddenField name="cmntId" value="${cmnt?.id}"/>
    <g:hiddenField name="divid" value="${divid}" />
    <g:hiddenField name="objectId" value="${objectId}" />
    <g:hiddenField name="objectDomain" value="${objectDomain}" />
    <label for="comment"><g:message code="comment.comment.label" default="comment" /></label>
    <g:textArea class="comment" name="comment" value="${cmnt?.comment}" cols="450" maxlength="1000"/>
    <g:select name="type" from="${pcc.CType?.values()}" value="${commentInstance?.type}"  />
    <g:submitToRemote class="postSave" action="commentSave" update="${divid}" controller="comment" value="${message(code: 'comment.save.label', default: 'post')}" />
    <g:submitToRemote class="postCancel" name="cancel" action="commentCancel" update="commentForm${cid}" controller="comment" value="${message(code: 'comment.cancel.label', default: 'cancel')}" />
  </g:form>
  <br>

</div>
