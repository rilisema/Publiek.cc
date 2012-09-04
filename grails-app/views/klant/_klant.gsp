<h2>Klantgegevens</h2>
<div class="klantBeeldPersoon customer-info">
  <table>
    <tbody>
      <tr class="kbprop">
        <td valign="top" class="name"><g:message code="klant.naam.label" default="Naam" /></td>
        <td valign="top" class="value">${klantInstance}&nbsp;</td>
        <td valign="top" class="name"><g:message code="klant.BSN.label" default="BSN" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "BSN")}&nbsp;</td>
      </tr>

      <tr class="kbprop">
        <td valign="top" class="name"><g:message code="klant.adres.label" default="Adres" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "adres")}&nbsp;</td>
        <td valign="top" class="name"><g:message code="klant.geboortedatum.label" default="Geboortedatum" /></td>
        <td valign="top" class="value"><g:formatDate date="${klantInstance?.geboortedatum}" type="date" style="MEDIUM" />&nbsp;</td>
      </tr>

      <tr class="kbprop">
        <td valign="top" class="name"><g:message code="klant.postcode.label" default="Postcode" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "postcode")}&nbsp;</td>
        <td valign="top" class="name"><g:message code="klant.plaats.label" default="Plaats" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "plaats")}&nbsp;</td>
      </tr>

      <tr class="kbprop noborder">
        <td valign="top" class="name"><g:message code="klant.telefoonnummer.label" default="Telefoonnummer" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "telefoonnummer")}&nbsp;</td>
        <td valign="top" class="name"><g:message code="klant.email.label" default="Email" /></td>
        <td valign="top" class="value">${fieldValue(bean: klantInstance, field: "email")}&nbsp;</td>
      </tr>
    </tbody>
  </table>
</div>