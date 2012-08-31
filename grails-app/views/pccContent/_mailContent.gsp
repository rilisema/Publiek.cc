<g:javascript src="jquery.validate.js"/>	
<g:javascript src="messages_nl.js"/>	
<!-- validate begin -->
<g:javascript>
	$.validator.setDefaults({
		submitHandler: function() {$('#mailForm').dialog('close'); alert('Mail verstuurd'); }
	});
	$(function(){
    	$("#validateForm").validate();
	});
</g:javascript>
<!-- validate eind -->
<div class="blockcontent">
	<g:formRemote id="validateForm" name="mailDialog" url="[ controller: 'pccContent', action: 'sendMail', params:[contentId : contentInstance.id]]" update="mailMessage">
    	<table>
       		<tr><td>Naam Geadresseerde</td><td><g:textField name="nameReceiver" class="required" /></td></tr>
            <tr><td>Email adres</td><td><g:textField name="email" class="required email" /></td></tr>
            <tr><td>Afzender</td><td><g:textField name="nameSender" class="required" /></td></tr>
            <tr><td>Notitie</td><td><g:textArea name="messageSender" /></td></tr>
            <tr><td></td><td><g:actionSubmit class="button140 right" value="${message(code: 'mail.send', default: 'verstuur')}" /></td></tr>
        </table>                
        
    </g:formRemote>
</div>