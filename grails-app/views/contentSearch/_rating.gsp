
<g:javascript src="jquery.raty.min.js" />
<g:javascript>
$(function() {
	$('#pcRating').raty({
	  half  :  true,
	  start :    ${contentInstance.rating.round(1)},
	  number:    10,
  	  click: function(score, evt) {        
		$.post("pccContent/doRating", {'ratingValue':score, 'contentId': ${contentInstance.id}}, function(data){
		                  $("#avgRating").text('Gemiddelde waardering:'+data.ratingValue+' ('+data.numberOfRates+' waarderingen)');
		                });        
      }	  
	});
});
</g:javascript>
<div id="pcRating"></div> 


<p id="defaultConnect">
  <br />
  <g:if test="${contentInstance.numberOfRates>0 }">
	  <i id="avgRating">Gemiddelde waardering: ${contentInstance.rating.round(1)} (${contentInstance.numberOfRates} waarderingen)</i>
  </g:if>
  <g:else>
	  <i id="avgRating">Dit onderdeel is nog niet gewaardeerd.</i>  
  </g:else>
</p>


