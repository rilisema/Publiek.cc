<irJQ:rteResources/>
					
<div class="dialog">
    <table>
        <tbody>                
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="title"><g:message code="localPdc.title.label" default="title" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localPdcInstance, field: 'title', 'errors')}">
                    <g:textField name="title" value="${localPdcInstance?.title}" />
                </td>
            </tr>        
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="description"><g:message code="localPdc.description.label" default="description" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localPdcInstance, field: 'description', 'errors')}" style="border:1px solid #CCC;">
                	<irJQ:rte name="description">${localPdcInstance?.description}</irJQ:rte>								           
                </td>
            </tr>        
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="internalInformation"><g:message code="localPdc.internalInformation.label" default="internalInformation" /></label>
                </td>                
                <td valign="top" class="value ${hasErrors(bean: localPdcInstance, field: 'internalInformation', 'errors')}" style="border:1px solid #CCC;">
                	<irJQ:rte name="internalInformation">${localPdcInstance?.internalInformation}</irJQ:rte>								           
                </td>
            </tr>        
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="contentSynonym"><g:message code="localPdc.contentSynonym.label" default="contentSynonym" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: localPdcInstance, field: 'synoniemen', 'errors')}">
                    <g:textArea name="contentSynonym" cols="80" rows="5" value="${localPdcInstance?.contentSynonym}" />
                </td>
            </tr>
        </tbody>
    </table>
</div>