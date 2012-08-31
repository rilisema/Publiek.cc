<h2>Klantgegevens</h2>
<div class="klantBeeldPersoon">
  <table>
    <tbody>
    <tr class="kbprop">
      <td valign="top" class="name"><g:message code="klant.naam.label" default="Naam" /></td>
      <td valign="top" class="value">${klantInstance}</td>
      <td valign="top" class="name"><g:message code="klant.BSN.label" default="BSN" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "BSN")}</td>

    </tr>
    <tr  class="kbprop">
      <td valign="top" class="name"><g:message code="klant.adres.label" default="Adres" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "adres")}</td>
      <td valign="top" class="name"><g:message code="klant.geboortedatum.label" default="Geboortedatum" /></td>
      <td valign="top" class="value"><g:formatDate date="${klantInstance?.geboortedatum}" type="date" style="MEDIUM" /></td>
    </tr>

    <tr class="kbprop">
      <td valign="top" class="name"><g:message code="klant.postcode.label" default="Postcode" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "postcode")}</td>
      <td valign="top" class="name"><g:message code="klant.plaats.label" default="Plaats" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "plaats")}</td>
    </tr>
    <tr  class="kbprop">
      <td valign="top" class="name"><g:message code="klant.telefoonnummer.label" default="Telefoonnummer" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "telefoonnummer")}</td>
      <td valign="top" class="name"><g:message code="klant.email.label" default="Email" /></td>
      <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "email")}</td>
    </tr>
    </tbody>
  </table>
</div>