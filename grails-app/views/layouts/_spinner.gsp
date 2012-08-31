<script type="text/javascript">
       var turnOffSpinner = function() {
           $("#pccSpinner").hide();
       }

	$(document).ready(function(){
		$("#pccSpinner").bind("ajaxSend", function() {
			$(this).show();
		}).bind("ajaxStop", function() {
			$(this).hide();
		}).bind("ajaxError", function() {
			$(this).hide();
		});			
	});
</script>

<div id="pccSpinner" class="spinner" style="display:none;">
  <img id="img-spinner" src="${resource(dir:'images',file:'ajax-loader.gif')}" alt="Spinner" />
</div>