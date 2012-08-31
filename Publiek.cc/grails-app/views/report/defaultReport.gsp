<%@ page import="ir.ast.*"
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="report.label" default="report" /></title>
        <export:resource />

    </head>
    <body>

        <div class="body">
          <br>
          
          <br>
			<script>
			$(function() {
				$( "#tabs" ).tabs();
			});
	  		</script>
	  		
			<g:set var="repStmts" value="${StatementInBatch.findAllByBatchAndActive(Batch.findByName('KCC reports'),'true')}"/>			
			
	  		<g:if test="${repStmts}">
	  		
			<div id="resultOrItem" class="block tab-block">
				<div class="header tabbed"> 
					${message(code: 'menuitem.report', default: 'reports')}
				</div>
	  		
		  		<div id="tabs">
  					<ul>
  						<g:each var="stiba" in="${repStmts}" status="nr">
  							<li><a href="#tab-${nr}">${stiba.stmt.name}</a></li>
  						</g:each>
	  				</ul>
 					<g:each var="stiba" in="${repStmts}" status="nr">
 						<div id="tab-${nr}">
 							<h1>${stiba.stmt.description}</h1>
                	  		<ast:statement statement="${stiba.stmt.statement}"/>
                  			<export:formats formats="['csv', 'excel', 'ods', 'pdf', 'rtf', 'xml']" action="export" params="[stmtId:stiba.stmt.id]"/>
 						</div>
 					</g:each>
 				</div>
	        </div>
 					
 				
			</g:if>
			<g:else>
				<h2>${message(code: 'menuitem.noReport.list', default: 'no reports to show')}</h2>
			</g:else>

    </body>
</html>
