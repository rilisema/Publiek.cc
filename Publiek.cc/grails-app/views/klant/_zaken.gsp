<div class="list">
  <h2>Zaakgegevens</h2>
  <table>
    <thead>
      <tr>
    <th><g:message code="zaak.klant.label" default="Klant" /></th>
    <g:sortableColumn property="omschrijving" title="${message(code: 'zaak.omschrijving.label', default: 'Omschrijving')}" />
    <g:sortableColumn property="status" title="${message(code: 'zaak.status.label', default: 'status')}" />
    <g:sortableColumn property="ontvangstdatum" title="${message(code: 'zaak.ontvangstdatum.label', default: 'Ontvangstdatum')}" />
    <g:sortableColumn property="verzoeknummer" title="${message(code: 'zaak.verzoeknummer.label', default: 'verzoeknummer')}" />
    <g:sortableColumn property="datumAfgehandeld" title="${message(code: 'zaak.datumAfgehandeld.label', default: 'Datum Afgehandeld')}" />
        <th><g:message code="zaak.behandelaar.label" default="Behandelaar" /></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${klantInstance?.zaken}" status="i" var="zaakInstance">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
      <td>${fieldValue(bean: zaakInstance, field: "klant")}</td>
      <td>${fieldValue(bean: zaakInstance, field: "omschrijving")}</td>
      <td>${fieldValue(bean: zaakInstance, field: "status")}</td>
      <td><g:formatDate date="${zaakInstance.ontvangstdatum}" type="date" style="MEDIUM" /></td>
      <td>${fieldValue(bean: zaakInstance, field: "verzoeknummer")}</td>
        <td><g:formatDate date="${zaakInstance.datumAfgehandeld}" type="date" style="MEDIUM" /></td>
        <td>${fieldValue(bean: zaakInstance, field: "behandelaar")}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
