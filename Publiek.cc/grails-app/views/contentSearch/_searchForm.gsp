<g:javascript>
	$(function(){
		$('[placeholder]').focus(function() {
		  var input = $(this);
		  if (input.val() == input.attr('placeholder')) {
		    input.val('');
		    input.removeClass('placeholder');
		  }
		}).blur(function() {
		  var input = $(this);
		  if (input.val() == '' || input.val() == input.attr('placeholder')) {
		    input.addClass('placeholder');
		    input.val(input.attr('placeholder'));
		  }
		}).blur();
	});
</g:javascript>

<!--Search form-->
<g:formRemote name="searchForm" url="[ controller: 'contentSearch', action:'searchResult' ]" update="pccContent">
<input type="hidden" name="suggestQuery" value="true"/>
   <g:textField placeholder="${message(code: 'menuitem.search', default: 'zoek')}" name="q" value="${params.q}" size="50"/> 
   <input class="button140 right" type="submit" value="${message(code: 'search.button', default: 'search')}" />
  
   <irSysPar:getPar name="toonVinkjes" var="toonVinkjes"/>

   <g:if test="${toonVinkjes?.value.equals('truex')}">
		<br><g:checkBox name="searchItems.sduPdc" value="${params.searchItems?params.searchItems?.sduPdc:true}" /> ${message(code: 'search.within.label', default: 'within')} "${message(code: 'search.result.sduProduct', default: 'sdu products')}"
		<br><g:checkBox name="searchItems.accVac" value="${params.searchItems?params.searchItems?.accVac:true}" /> ${message(code: 'search.within.label', default: 'within')} "${message(code: 'search.result.accVac', default: 'answer')}"
		<br><g:checkBox name="searchItems.product" value="${params.searchItems?params.searchItems?.product:true}" /> ${message(code: 'search.within.label', default: 'within')} "${message(code: 'search.result.product', default: 'products')}"
		<br><g:checkBox name="searchItems.localAnswer" value="${params.searchItems?params.searchItems?.localAnswer:true}" /> ${message(code: 'search.within.label', default: 'within')} "${message(code: 'search.result.localAnswer', default: 'local answer')}"
		<br><g:checkBox name="searchItems.facility" value="${params.searchItems?params.searchItems?.facility:true}" /> ${message(code: 'search.within.label', default: 'within')} "${message(code: 'search.result.facility', default: 'facility')}"
  	</g:if>
</g:formRemote><br>
