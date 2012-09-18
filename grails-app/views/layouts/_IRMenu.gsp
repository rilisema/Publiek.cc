<%@ page import="org.grails.activiti.ActivitiUtils"%>
<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder"%>
<%@ page import="org.grails.activiti.ActivitiConstants"%>

<%
def disabledActiviti = ConfigurationHolder.config.activiti.disabled?:false
def myTaskCount = null
if (!disabledActiviti) {
	def sessionUsernameKey = ConfigurationHolder.config.activiti.sessionUsernameKey?:ActivitiConstants.DEFAULT_SESSION_USERNAME_KEY
	def activitiService = ActivitiUtils.activitiService
	def user = session[sessionUsernameKey]
	myTasksCount = activitiService?.getAssignedTasksCount(user.toString())?:0
}
%>
<irSysPar:getPar name="gemeente" var="gemeente" />

<irMenu:menubar name="pccMenu"
	css="${resource(dir:'css/menu_1',file:'style.css')}"
	position="[menuXPos,menuYPos]">

	<irMenu:menuitem
		href="${createLink(controller:'contentSearch',action:'search')}"
		name="${message(code:'menuitem.search', default:'search')}" />
	<irMenu:menuitem
		href="${createLink(controller:'itemValue',action:'navigatie',params:[navType:'TiO'])}"
		name="${message(code:'menuitem.tioNav', default:'TiO')}" />

	<sec:ifLoggedIn>
		<irMenu:menuitem
			href="${createLink(controller:'klant',action:'klantBeeld')}"
			name="${message(code:'menuitem.klantBeeld', default:'customer care')}" />
			<g:if test="${!disabledActiviti}">
 			  <irMenu:menuitem
				href="${createLink(controller:'task',action:'myTaskList')}"
				name="${message(code:'menuitem.taken', default:'taken', args:[myTasksCount?:0])}" />
              <irMenu:menuitem
                href="${createLink(controller:'terugbelAfspraak',action:'start')}"
                name="${message(code:'menuitem.maakterugbelafspraak', default:'Maak terugbelafspraak')}" />
			</g:if>
	</sec:ifLoggedIn>

	<sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_EDITOR">
	 <irMenu:menuitem
			href="${createLink(controller:'report',action:'defaultReport')}"
			name="${message(code:'menuitem.report', default:'report')}" />
	</sec:ifAnyGranted>

	<sec:ifAnyGranted roles="ROLE_ADMIN">

		<irMenu:menuitem name="${message(code:'menuitem.menu.maintenance', default:'maintenance')}">
			<irMenu:menuitem
				href="${createLink(controller:'task',action:'allTaskList')}"
				name="${message(code:'menuitem.menu.taskmaintenance', default:'task maintenance')}" />
			<irMenu:menuitem
				name="${message(code:'menuitem.menu.content', default:'content')}">
				<irMenu:menuitem
					href="${createLink(controller:'localVac',action:'list')}"
					name="${message(code:'menuitem.local.vac', default:'lokale vac')}" />
				<irMenu:menuitem
					href="${createLink(controller:'localPdc',action:'list')}"
					name="${message(code:'menuitem.local.pdc', default:'lokale pdc')}" />
				<irMenu:menuitem
					href="${createLink(controller:'localFacility',action:'list')}"
					name="${message(code:'menuitem.facility', default:'voorziening')}" />
				<irMenu:menuitem
					href="${createLink(controller:'contentLocation',action:'importContent')}"
					name="${message(code:'import.content.label', default:'import content')}" />
				<irMenu:menuitem
					name="${message(code:'menuitem.menu.itemValues', default:'itemValue navigation')}">
					<g:each var="iv" in="${content.ItemValueType}">
						<irMenu:menuitem name="${iv}" href="${createLink(controller:'itemValue',action:'navigatie',params:[navType:iv])}" />
					</g:each>					
				</irMenu:menuitem>
			</irMenu:menuitem>

			<irMenu:menuitem name="${message(code:'menuitem.menu.auth', default:'authorization')}">
				<irMenu:menuitem
					href="${createLink(controller:'user',action:'list')}"
					name="${message(code:'menuitem.user', default:'users')}" />
				<irMenu:menuitem
					href="${createLink(controller:'role',action:'list')}"
					name="${message(code:'menuitem.role', default:'roles')}" />
				<irMenu:menuitem
					href="${createLink(controller:'requestmap',action:'list')}"
					name="${message(code:'menuitem.requestmap', default:'requestmap')}" />
			</irMenu:menuitem>

			<irMenu:menuitem name="${message(code:'menuitem.menu.report', default:'report')}">
				<irMenu:menuitem
					href="${createLink(controller:'batch',action:'list')}"
					name="${message(code:'menuitem.anybatch', default:'batch')}" />
				<irMenu:menuitem
					href="${createLink(controller:'anyStatement',action:'list')}"
					name="${message(code:'menuitem.anystatement', default:'statement')}" />
				<irMenu:menuitem
					href="${createLink(controller:'statementInBatch',action:'list')}"
					name="${message(code:'menuitem.batchinstatement', default:'batch in statement')}" />
				<irMenu:menuitem
					href="${createLink(controller:'anySource',action:'list')}"
					name="${message(code:'menuitem.anysource', default:'any source')}" />
			</irMenu:menuitem>

			<g:if test="${gemeente?.value.equals('BER') || gemeente?.value.equals('Etten-Leur')}">
				<irMenu:menuitem
					name="${message(code:'menuitem.menu.gemeenten', default:'gemeenten')}">
					<irMenu:menuitem
						href="${createLink(controller:'pcc',action:'changeCss', params:[css:'opmaak_boz.css'])}"
						name="Opmaak Bergen op Zoom" />
					<irMenu:menuitem
						href="${createLink(controller:'pcc',action:'changeCss', params:[css:'opmaak_etl.css'])}"
						name="Opmaak Etten-Leur" />
					<irMenu:menuitem
						href="${createLink(controller:'pcc',action:'changeCss', params:[css:'opmaak_rdl.css'])}"
						name="Opmaak Roosendaal" />
					<irMenu:menuitem
						href="${createLink(controller:'pcc',action:'changeCss')}"
						name="Standaard opmaak" />
				</irMenu:menuitem>
			</g:if>

			<irMenu:menuitem
				name="${message(code:'menuitem.menu.settings', default:'settings')}">
				<irMenu:menuitem
					href="${createLink(controller:'IRSystemPar',action:'list')}"
					name="${message(code:'menuitem.parameters', default:'parameters')}" />
				<irMenu:menuitem
					href="${createLink(controller:'IRLogTable',action:'list')}"
					name="${message(code:'menuitem.logInTable', default:'IRLogTable')}" />
				<irMenu:menuitem
					href="${createLink(controller:'contentLocation',action:'list')}"
					name="${message(code:'import.location', default:'import lacation')}" />
			</irMenu:menuitem>

			<irMenu:menuitem
				name="${message(code:'menuitem.menu.controller', default:'controllers')}">
				<g:each var="c"
					in="${grailsApplication.domainClasses.toList().sort{q->q.name} }">
					<irMenu:menuitem name="${c.name}"
						href="${createLink(controller:c.propertyName)}" />
				</g:each>
			</irMenu:menuitem>

		</irMenu:menuitem>

	</sec:ifAnyGranted>

	<irMenu:menuitem
		name="${message(code:'menuitem.links', default:'links')}">
		<g:if test="${gemeente?.value.equals('Rotterdam')}">
			<irMenu:menuitem href="http://www.rotterdam.nl/"
				name="Website gemeente Rotterdam" />
		</g:if>
		<g:elseif test="${par?.value.equals('BER')}">
			<irMenu:menuitem href="http://www.etten-leur.nl/"
				name="Website gemeente Etten-Leur" />
			<irMenu:menuitem href="http://www.bergenopzoom.nl/"
				name="Website gemeente Bergen op Zoom" />
			<irMenu:menuitem href="http://www.roosendaal.nl/"
				name="Website gemeente Roosendaal" />
		</g:elseif>

		<irMenu:menuitem
			href="${createLink(controller:'contentSearch',action:'search',params:[lang:'nl'])}"
			name="Nederlands (zoeken)" />
		<irMenu:menuitem
			href="${createLink(controller:'contentSearch',action:'search',params:[lang:'en'])}"
			name="English (search)" />
	</irMenu:menuitem>

	<sec:ifLoggedIn>
		<irMenu:menuitem href="${createLink(controller:'logout')}"
			name="${message(code:'menuitem.logout', default:'logout')}" />
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
		<irMenu:menuitem
			href="${createLink(controller:'login',action:'auth')}"
			name="${message(code:'menuitem.login', default:'log in')}" />
	</sec:ifNotLoggedIn>


</irMenu:menubar>
