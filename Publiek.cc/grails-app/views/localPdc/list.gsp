
<%@ page import="content.LocalPdc" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'localPdc.label', default: 'LocalPdc')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>                        
                            <g:sortableColumn property="id" title="${message(code: 'localPdc.id.label', default: 'Id')}" />
                            <g:sortableColumn property="title" title="${message(code: 'localPdc.title.label', default: 'Title')}" />
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'localPdc.lastUpdated.label', default: 'Last Updated')}" />                        
                            
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${localPdcInstanceList}" status="i" var="localPdcInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
                            <td><g:link action="show" id="${localPdcInstance.id}">${fieldValue(bean: localPdcInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: localPdcInstance, field: "title")}</td>
                            <td><g:formatDate date="${localPdcInstance.lastUpdated}" /></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${localPdcInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
