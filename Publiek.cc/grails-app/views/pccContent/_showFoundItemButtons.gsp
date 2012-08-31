
<irSysPar:getParValue name="kccKnoppen" var="kccKnoppen"/>
<g:if test="${!(kccKnoppen.equals('false'))}">

	<script type="text/javascript">
		function putThrough(msg, nr) {
		prompt(msg, nr);
		return false;
		}
	</script>
  
	<div class="search">
		<div class="buttons">
			<g:form controller="pccContent" >        
				<g:hiddenField name="id" value="${contentInstance?.id}" />        
				<sec:ifAnyGranted roles="ROLE_KCC">
        			<g:set var="phoneNr" value="${(objectController.equals('product')||objectController.equals('facility'))?contentInstance?.phoneNumber:'nog niet geimplementeerd'}"/>
					<g:if test="${fromSearch=='on'}">
						<span class="button"><g:actionSubmit class="putThrough" action="edit" value="${message(code: 'call.putthrough', default: 'put through')}" onclick="putThrough('doorverbinden met telefoonnummer', '${phoneNr}'); return false;" /></span>
						<span class="button"><g:actionSubmit class="carryOn" action="edit" value="${message(code: 'call.carryon', default: 'carry on')}" onclick="putThrough('doorzetten met telefoonnummer', '${phoneNr}'); return false;"/></span>
						<span class="button"><g:actionSubmit class="takeBack" action="edit" value="${message(code: 'call.takeback', default: 'take back')}" onclick="alert('Nog niet geimplementeerd'); return false;"/></span>
						<g:hiddenField name="wfcontroller" value="terugbelAfspraak"/>
						<span class="button"><g:actionSubmit class="makeNote" action="startProcess" value="${message(code: 'call.note', default: 'note')}"/></span>
					</g:if> <!-- if from search -->
				</sec:ifAnyGranted>
			</g:form>
		</div>
  	</div>

</g:if>
  