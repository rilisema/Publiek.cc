<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
    <title>Importeer</title>
  </head>
  <body>
    <div class="body">
    
      <g:if test="${flash.message}">
	      <div class="errors">${flash.message}</div>
      </g:if>
    
      <h1><g:message code="import.content.label" /></h1>

      <g:set var="contentPublisherList" value="${pcc.Publisher }"/>
      <g:each in="${importList}" var="publisherImports">      	
        	<h1>Uitgever : ${publisherImports.name}</h1>        	
	    	<g:each in="${publisherImports.importSet}" var="contentType">
	    	<h3>${contentType.contentType }</h3>
	    	<g:each in="${contentType.locations}" var="importLocation">
	    		${importLocation.url} (${importLocation.contentName })<br>
	    	</g:each>
    	 	<g:link action="importCotentItem" params="[publisher:publisherImports.name, contentType:contentType.contentType]">
	   	 		<g:message code="import.${contentType.contentType}"/>
    	 	</g:link>	    	    	
	    	</g:each>    	
      </g:each>
      
      <h1>Rebuild Searchable Index</h1>
      <g:remoteLink controller="contentSearch" action="rebuildIndex" update="rebuildIndexMsg">re-index</g:remoteLink>    
      
      <div id="rebuildIndexMsg">
      </div>
      
    </div>
  </body>
</html>