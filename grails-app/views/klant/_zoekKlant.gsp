<h2>Zoek klant</h2>
<div class="klantBeeldPersoon search">
  <div class="dialog" >
    <g:form action="klantBeeld">
      <g:hiddenField name="formUsed" value="${true}" />
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="BSN"><g:message code="klant.BSN.label" default="BSN" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: klantInstance, field: 'BSN', 'errors')}">
              <g:textField name="BSN" value="${pars?.BSN}" />
            </td>
            <td valign="top" class="name">
              <label for="achternaam"><g:message code="klant.achternaam.label" default="Achternaam" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: klantInstance, field: 'achternaam', 'errors')}">
              <g:textField name="achternaam" value="${pars?.achternaam}" />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="postcode"><g:message code="klant.postcode.label" default="Postcode" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: klantInstance, field: 'postcode', 'errors')}">
              <g:textField name="postcode" value="${pars?.postcode}" />
            </td>
            <td valign="top" class="name">
              <label for="huisnummer"><g:message code="klant.huisnummer.label" default="Huisnummer" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: klantInstance, field: 'huisnummer', 'errors')}">
              <g:textField name="huisnummer" value="${pars?.huisnummer}" />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="geboortedatum"><g:message code="klant.geboortedatum.label" default="Geboortedatum (dd-mm-yyyy)" /></label>
            </td>
            <td colspan="3" valign="top" class="value ${hasErrors(bean: klantInstance, field: 'geboortedatum', 'errors')}">
              <g:datePicker name="geboortedatum" precision="day" value="${pars?.geboortedatum}" default="none" noSelection="['': '']" />
            </td>
          </tr>

          <tr class="prop noborder">
            <td valign="top" class="name">
              <div class="buttons">
                <span class="button"><g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label', default: 'zoek')}" /></span>
              </div>
            </td>
            <td colspan="3"></td>
          </tr>
        </tbody>
      </table>
    </g:form>
  </div>
</div>
