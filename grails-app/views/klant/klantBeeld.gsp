
<%@ page import="demo.Klant" %>
<%@ page import="demo.Zaak" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Klantbeeld</title>
  </head>
  <body>
    <div class="body">
      <div class="klantBeeld-content">
        <h1>Klantbeeld</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <g:render template="zoekKlant"/>
        <br/>
        <br/>
        <g:if test="${klanten?.size()>1}">
          <g:render template="kiesKlant"/>
          <br/>
          <br/>
        </g:if>
        <g:else>
          <g:render template="zaken"/>
          <br/>
          <br/>
          <g:render template="klant"/>
        </g:else>
      </div>
    </div>
  </body>
</html>
