<div class="env">
	<div class="${GrailsUtil.environment}">
		Publiek.cc <g:meta name="app.version" /> (${GrailsUtil.environment})
	</div>
</div>
<sec:ifLoggedIn>
	<g:message code="auth.logged.in.as" default="Logged in as" />
	<span style="font-weight: bold;"> 
	<sec:loggedInUserInfo field="username" /> </span>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
	<g:message code="auth.logged.guest" default="Guest user" />
</sec:ifNotLoggedIn>


