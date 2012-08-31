
<%@ page import="pcc.TerugbelAfspraak" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'terugbelAfspraak.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="klantnaam" title="${message(code: 'terugbelAfspraak.klantnaam.label', default: 'Klantnaam')}" />
                        
                            <g:sortableColumn property="telefoonnummer" title="${message(code: 'terugbelAfspraak.telefoonnummer.label', default: 'Telefoonnummer')}" />
                        
                            <g:sortableColumn property="opmerkingen" title="${message(code: 'terugbelAfspraak.opmerkingen.label', default: 'Opmerkingen')}" />
                        
                            <g:sortableColumn property="terugbelDatum" title="${message(code: 'terugbelAfspraak.terugbelDatum.label', default: 'Terugbel Datum')}" />
                        
                            <g:sortableColumn property="terugbeller" title="${message(code: 'terugbelAfspraak.terugbeller.label', default: 'Terugbeller')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${terugbelAfspraakInstanceList}" status="i" var="terugbelAfspraakInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${terugbelAfspraakInstance.id}">${fieldValue(bean: terugbelAfspraakInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: terugbelAfspraakInstance, field: "klantnaam")}</td>
                        
                            <td>${fieldValue(bean: terugbelAfspraakInstance, field: "telefoonnummer")}</td>
                        
                            <td>${fieldValue(bean: terugbelAfspraakInstance, field: "opmerkingen")}</td>
                        
                            <td><g:formatDate date="${terugbelAfspraakInstance.terugbelDatum}" /></td>
                        
                            <td>${fieldValue(bean: terugbelAfspraakInstance, field: "terugbeller")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${terugbelAfspraakInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
