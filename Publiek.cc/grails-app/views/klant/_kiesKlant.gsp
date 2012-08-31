<%@ page import="demo.Klant" %>
<div class="list">
  <table>
    <thead>
      <tr>

        <th>Selecteer</th>
    <g:sortableColumn property="BSN" title="${message(code: 'klant.bsn.label', default: 'BSN')}" />
    <g:sortableColumn property="voorletters" title="${message(code: 'klant.voorletters.label', default: 'Voorletters')}" />
    <g:sortableColumn property="voornamen" title="${message(code: 'klant.voornamen.label', default: 'Voornamen')}" />
    <g:sortableColumn property="tussenvoegsels" title="${message(code: 'klant.tussenvoegsels.label', default: 'Tussenvoegsels')}" />
    <g:sortableColumn property="achternaam" title="${message(code: 'klant.achternaam.label', default: 'Achternaam')}" />
    <g:sortableColumn property="geboortedatum" title="${message(code: 'klant.geboortedatum.label', default: 'Geboortedatum')}" />
    <g:sortableColumn property="geslacht" title="${message(code: 'klant.geslacht.label', default: 'Geslacht')}" />
    <g:sortableColumn property="postcode" title="${message(code: 'klant.postcode.label', default: 'Postcode')}" />
    <g:sortableColumn property="huisnummer" title="${message(code: 'klant.huisnummer.label', default: 'Huisnummer')}" />
    <g:sortableColumn property="straat" title="${message(code: 'klant.straat.label', default: 'Straat')}" />
    <g:sortableColumn property="plaats" title="${message(code: 'klant.plaats.label', default: 'Plaats')}" />
    </tr>
    </thead>
    <tbody>
    <g:each in="${klanten}" status="i" var="klantInstance">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link action="klantBeeld" id="${klantInstance.id}">Kies</g:link></td>
      <td>${fieldValue(bean: klantInstance, field: "BSN")}</td>
      <td>${fieldValue(bean: klantInstance, field: "voorletters")}</td>
      <td>${fieldValue(bean: klantInstance, field: "voornamen")}</td>
      <td>${fieldValue(bean: klantInstance, field: "tussenvoegsels")}</td>
      <td>${fieldValue(bean: klantInstance, field: "achternaam")}</td>
      <td><g:formatDate date="${klantInstance.geboortedatum}" type="date" style="MEDIUM"/></td>
      <td>${fieldValue(bean: klantInstance, field: "geslacht")}</td>
      <td>${fieldValue(bean: klantInstance, field: "postcode")}</td>
      <td>${fieldValue(bean: klantInstance, field: "huisnummer")}</td>
      <td>${fieldValue(bean: klantInstance, field: "straat")}</td>
      <td>${fieldValue(bean: klantInstance, field: "plaats")}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
