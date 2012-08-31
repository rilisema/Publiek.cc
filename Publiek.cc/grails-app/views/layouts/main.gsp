<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.springframework.util.ClassUtils" %>

<irSysPar:getParValue var="mainType" name="main" />

<g:if test="${mainType && !params.defaultmain}"> <!-- If main file specified use the specified one -->
	<g:include view="${mainType}" params="[zaaksysteem_ident:params.zaaksysteem_ident]"/>
</g:if>

<g:else> <!-- If main file is not specified use the default -->	 
	<g:include view="/layouts/mainDefault.gsp" />
</g:else>
