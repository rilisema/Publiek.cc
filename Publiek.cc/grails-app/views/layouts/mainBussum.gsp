<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>  
	<g:javascript library="jquery" plugin="jquery"/>	
	<!-- jqui:resources/ -->
	<script src="/Publiek.cc/plugins/jquery-ui-1.8.10/jquery-ui/js/jquery-ui-1.8.10.custom.min.js" type="text/javascript" ></script> 
	
    <title><g:layoutTitle default="Publiek Contact Centrum" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'kcc.css')}" />

	<g:set var="zaaksysteemUrl" value="${grailsApplication.config.zaaksysteem.url}"/>
	<g:set var="pathBussum" value="${grailsApplication.config.zaaksysteem.css.url}"/>
	
	<g:set var="pathBussumCss" value="${pathBussum}tpl/zaak_v1/nl_NL/css/"/>
	<g:set var="pathBussumJs"  value="${pathBussum}tpl/zaak_v1/nl_NL/js/"/>
	<g:set var="zaakUser"      value="${session?.zaaksysteem_ident}" />	

	<link rel="stylesheet" href="${pathBussumCss}themes/smoothness/jquery-ui-1.7.2.custom.css" /> 
    <link rel="stylesheet" href="${pathBussumCss}jquery.treeTable.css" /> 
    <link rel="stylesheet" href="${pathBussumCss}stylesheet.css" type="text/css" media="screen" /> 
    <link rel="stylesheet" href="${pathBussumCss}jquery.ui.selectmenu.css" type="text/css" media="screen" /> 
    <link rel="stylesheet" href="${pathBussumCss}ztb.css" type="text/css" media="screen" /> 
    <link rel="stylesheet" href="${pathBussumCss}jquery.multiselect.css" type="text/css" media="screen" /> 
    <link rel="stylesheet" href="${pathBussumCss}bussum/jquery.uniform.default.css" type="text/css" media="screen" /> 

	<script type="text/javascript" src="${pathBussumJs}jquery.ui.selectmenu.js"></script>
	<script type="text/javascript" src="${pathBussumJs}jquery.cookie.js"></script>
	<script type="text/javascript" src="${pathBussumJs}zaaksysteem-memio.js"></script>

    <link rel="stylesheet" href="${resource(dir:'css/bussum/',file:'bussumOverrule.css')}" />

    <g:if test="${cookie(name:'customCss')}">
      <link rel="stylesheet" href="${resource(dir:'css',file:cookie(name:'customCss'))}" />
    </g:if>
    <link rel="shortcut icon" href="${resource(dir:'images/kcc',file:'logo_prt.ico')}" type="image/x-icon" />
    <g:layoutHead />
  </head>

<body class="zaaksysteem-memio">
	<g:if test="${zaakUser}">
		<g:javascript>
			$(function() {
		        <g:remoteFunction controller="pcc" onSuccess="\$('#topsection').trigger('contentChange');" action="getExternalPage" params="[url:zaaksysteemUrl+'page/retrieve_component/usermenu?username='+zaakUser]" update="topsection"/>
				<g:remoteFunction controller="pcc" onSuccess="\$('#leftcolumn').trigger('contentChange');" action="getExternalPage" params="[url:zaaksysteemUrl+'page/retrieve_component/mainmenu?username='+zaakUser]" update="leftcolumn"/>
			})		 
		</g:javascript>
		
		<div id="topsection">
		</div>
		<div id="contentwrapper" class="clearfix">
			<div id="leftcolumn">
			</div>
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
	</g:if>
	<g:else>  <!-- no zaakUser -->
		<g:include view="/error.gsp" />
	</g:else>


</body>
</html>
