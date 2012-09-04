
<html>
  <head>  
	<g:javascript library="jquery" plugin="jquery"/>	
	<!-- jqui:resources/ -->
	<script src="/Publiek.cc/plugins/jquery-ui-1.8.10/jquery-ui/js/jquery-ui-1.8.10.custom.min.js" type="text/javascript" ></script> 
	
    <title><g:layoutTitle default="Publiek Contact Centrum" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'kcc.css')}" />
    
    <link rel="stylesheet" href="${resource(dir:'css/defaultMain',file:'jquery-ui-1.7.2.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css/defaultMain',file:'stylesheet.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css/defaultMain',file:'ztb.css')}" />

    <link rel="stylesheet" href="${resource(dir:'css',file:'defaultOverrule.css')}" />    
    
    <g:if test="${cookie(name:'customCss')}">
    	<link rel="stylesheet" href="${resource(dir:'css',file:cookie(name:'customCss'))}" />
    </g:if>
    <link rel="stylesheet" href="${resource(dir:'css/defaultMain',file:'override-tasks.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css/defaultMain',file:'override-klanten.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images/kcc',file:'logo_prt.ico')}" type="image/x-icon" />
    <g:layoutHead />
  </head>

<body class="${GrailsUtil.environment}" onload='turnOffSpinner();' class="zaaksysteem-memio">

	<g:render template="/layouts/spinner" />
	
	<div id="topsection">
		<div class="logo">
			<g:render template="/layouts/header" />
		</div>
	</div>
	<div id="contentwrapper" class="clearfix">

		<div id="contentcolumn">
			<!-- Publiek.cc content  -->			
			<g:render template="/layouts/spinner" />

			<a name="pccTop" id="pccTop"></a>					
			<div class="content" id="pccContent" >
				<g:layoutBody />
			</div>
			
			<div class="ft">
		  		<g:render template="/layouts/footer" />
			</div>	
			<!-- End - Publiek.cc content  -->					
		</div>
	</div>
	
</body>
</html>
