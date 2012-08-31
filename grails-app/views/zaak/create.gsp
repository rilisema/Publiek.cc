

<%@ page import="demo.Zaak" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'zaak.label', default: 'Zaak')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${zaakInstance}">
            <div class="errors">
                <g:renderErrors bean="${zaakInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="omschrijving"><g:message code="zaak.omschrijving.label" default="Omschrijving" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'omschrijving', 'errors')}">
                                    <g:textField name="omschrijving" value="${zaakInstance?.omschrijving}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="zaakType"><g:message code="zaak.zaakType.label" default="Zaak Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'zaakType', 'errors')}">
                                    <g:textField name="zaakType" value="${zaakInstance?.zaakType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ontvangstdatum"><g:message code="zaak.ontvangstdatum.label" default="Ontvangstdatum" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'ontvangstdatum', 'errors')}">
                                    <g:datePicker name="ontvangstdatum" precision="day" value="${zaakInstance?.ontvangstdatum}"  />
                                </td>
                            </tr>
                        
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="verzoeknummer"><g:message code="zaak.verzoeknummer.label" default="Verzoeknummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'verzoeknummer', 'errors')}">
                                    <g:textField name="verzoeknummer" value="${fieldValue(bean: zaakInstance, field: 'verzoeknummer')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="zaak.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${demo.ZaakStatus?.values()}" keys="${demo.ZaakStatus?.values()*.name()}" value="${zaakInstance?.status?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="klant"><g:message code="zaak.klant.label" default="Klant" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'klant', 'errors')}">
                                    <g:select name="klant.id" from="${demo.Klant.list()}" optionKey="id" value="${zaakInstance?.klant?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="behandelaar"><g:message code="zaak.behandelaar.label" default="Behandelaar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: zaakInstance, field: 'behandelaar', 'errors')}">
                                    <g:select name="behandelaar.id" from="${auth.User.list()}" optionKey="id" value="${zaakInstance?.behandelaar?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
