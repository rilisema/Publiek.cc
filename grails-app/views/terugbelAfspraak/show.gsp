
<%@ page import="pcc.TerugbelAfspraak" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: terugbelAfspraakInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.klantnaam.label" default="Klantnaam" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: terugbelAfspraakInstance, field: "klantnaam")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.telefoonnummer.label" default="Telefoonnummer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: terugbelAfspraakInstance, field: "telefoonnummer")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.opmerkingen.label" default="Opmerkingen" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: terugbelAfspraakInstance, field: "opmerkingen")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.terugbelDatum.label" default="Terugbel Datum" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${terugbelAfspraakInstance?.terugbelDatum}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.terugbeller.label" default="Terugbeller" /></td>
                            
                            <td valign="top" class="value">${terugbelAfspraakInstance?.terugbeller}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="terugbelAfspraak.teruggebeld.label" default="Teruggebeld" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${terugbelAfspraakInstance?.teruggebeld}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:if test="${!params.complete && params.taskId}">
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${terugbelAfspraakInstance?.id}" />
                    <g:hiddenField name="taskId" value="${params.taskId}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
            </g:if>
        </div>
    </body>
</html>
