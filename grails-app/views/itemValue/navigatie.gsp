<%@ page import="org.springframework.util.ClassUtils" %>

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'klantvragen.css')}" />
        <title><g:message code="tio.search" default="Zoeken op Thema"/></title>
  </head>
  <body>
    <div class="body">
      <div class="navigatie" id="navigatie">
		<g:render template="showValueList" model=params />
        
      </div><!--body-->
  </body>
</html>