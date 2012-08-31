
<html>
  <head>  
	<g:javascript library="jquery" plugin="jquery"/>	
	<!-- jqui:resources -->
	
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'kcc.css')}" />
    <g:if test="${cookie(name:'customCss')}">
      <link rel="stylesheet" href="${resource(dir:'css',file:cookie(name:'customCss'))}" />
    </g:if>
    <link rel="shortcut icon" href="${resource(dir:'images/kcc',file:'logo_prt.ico')}" type="image/x-icon" />
  </head>

	<body>
	
	<div class="logo">
 		<g:render template="/layouts/header" />	  			 		
	</div>
	</body>
</html>
