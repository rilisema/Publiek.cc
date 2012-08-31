<irJQ:rteResources/>

<div class="dialog">
    <table>
        <tbody>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="title"><g:message code="localFacility.title.label" default="title" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localFacilityInstance, field: 'title', 'errors')}">
                    <g:textField name="title" value="${localFacilityInstance?.title}" />
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="description"><g:message code="localFacility.description.label" default="Description" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localFacilityInstance, field: 'description', 'errors')}" style="border:1px solid #CCC;">
                	<irJQ:rte name="description">${localFacilityInstance?.description}</irJQ:rte>            
             	</td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name" >
                    <label for="internalInformation"><g:message code="localFacility.internalInformation.label" default="internalInformation" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localFacilityInstance, field: 'internalInformation', 'errors')}" style="border:1px solid #CCC;">
                	<irJQ:rte name="internalInformation">${localFacilityInstance?.internalInformation}</irJQ:rte>            
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="phoneNumber"><g:message code="localFacility.phoneNumber.label" default="Name" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localFacilityInstance, field: 'phoneNumber', 'errors')}">
                    <g:textField name="phoneNumber" value="${localFacilityInstance?.phoneNumber}" />
                </td>
            </tr>
        </tbody>
    </table>
</div>