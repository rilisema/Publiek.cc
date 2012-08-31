<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="layout" content="main" />
		<!--  TODO : overzetten zoekargument naar title -->    
		<title><g:message code="search.label" /></title>
		<script type="text/javascript">
			$(function() {
				<%--$( "#q" ).focus();--%>		
			});
			
	    </script>
	</head>
  
	<body>
		<div class="body">
			<div class="search">
				<h1><g:message code="search.header" /></h1>
				<div id="searchResult">      
					<g:render template="searchResult"/>      	
				</div>
			</div> <!--search-->
		</div> <!--body-->
		

	</body>
</html>
