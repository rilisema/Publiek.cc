

<%@ page import="pcc.TerugbelAfspraak" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${terugbelAfspraakInstance}">
      <div class="errors">
        <g:renderErrors bean="${terugbelAfspraakInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <g:hiddenField name="id" value="${terugbelAfspraakInstance?.id}" />
      <g:hiddenField name="version" value="${terugbelAfspraakInstance?.version}" />
      <g:hiddenField name="taskId" value="${params.taskId}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="klantnaam"><g:message code="terugbelAfspraak.klantnaam.label" default="Klantnaam" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'klantnaam', 'errors')}">
          <g:textField name="klantnaam" value="${terugbelAfspraakInstance?.klantnaam}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="telefoonnummer"><g:message code="terugbelAfspraak.telefoonnummer.label" default="Telefoonnummer" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'telefoonnummer', 'errors')}">
          <g:textField name="telefoonnummer" value="${terugbelAfspraakInstance?.telefoonnummer}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="opmerkingen"><g:message code="terugbelAfspraak.opmerkingen.label" default="Opmerkingen" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'opmerkingen', 'errors')}">
          <g:textArea name="opmerkingen" cols="40" rows="5" value="${terugbelAfspraakInstance?.opmerkingen}" />
          </td>
          </tr>
          
          <tr class="prop">
            <td valign="top" class="name">
              <label for="collegaNotities"><g:message code="terugbelAfspraak.colleganotities.label" default="Notities voor collega" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'collegaNotities', 'errors')}">
          <g:textArea name="collegaNotities" cols="40" rows="5" value="${terugbelAfspraakInstance?.collegaNotities}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="terugbelDatum"><g:message code="terugbelAfspraak.terugbelDatum.label" default="Terugbel Datum" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'terugbelDatum', 'errors')}">
          <g:datePicker name="terugbelDatum" precision="day" value="${terugbelAfspraakInstance?.terugbelDatum}"  />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="terugbeller"><g:message code="terugbelAfspraak.terugbeller.label" default="Terugbeller" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'terugbeller', 'errors')}">
              <g:select name="terugbeller" from="${users}" optionKey="id" value="${terugBeller.id}"  />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="teruggebeld"><g:message code="terugbelAfspraak.teruggebeld.label" default="Teruggebeld" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'teruggebeld', 'errors')}">
          <g:checkBox name="teruggebeld" value="${terugbelAfspraakInstance?.teruggebeld}" />
          </td>
          </tr>
          
          <tr class="prop">
            <td valign="top" class="name">
              <label for="stuurMail"><g:message code="terugbelAfspraak.stuurMail.label" default="Stuur mail" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: terugbelAfspraakInstance, field: 'stuurMail', 'errors')}">
          <g:checkBox name="stuurMail" value="${terugbelAfspraakInstance?.stuurMail}" />
          </td>
          </tr>
          
          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.complete.label', default: 'Complete')}" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
