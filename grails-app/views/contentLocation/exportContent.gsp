<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="layout" content="main" />
		<title>Verwerk VAC gegevens</title>	
	</head>
	
<body>
	<div class="body"><g:if test="${flash.message}">
		<div class="errors">
		${flash.message}
		</div>
	</g:if>
	
	<h1><g:message code="export.content.label" /></h1>
	
	<g:set var="contentPublisherList" value="${pcc.Publisher }" /> 
	<g:each	in="${exportList}" var="exportItem">
		<g:remoteLink action="exportContentItem" params="[exportItem:exportItem]" update="exportDiv">
			<g:message code="export.${exportItem}" />
		</g:remoteLink>
	</g:each> 
	
	<?xml version="1.0" encoding="ISO-8859-1"?>
	<div id="exportDiv">
	</div>
	
	</div>
</body>
</html>