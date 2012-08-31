/* Copyright 2011 Memio BV * * Licensed under the Apache License,
Version 2.0 (the "License"); * you may not use this file except in
compliance with the License. * You may obtain a copy of the License at *
* http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by
applicable law or agreed to in writing, software * distributed under the
License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. * See the License for
the specific language governing permissions and * limitations under the
License. */


<%@ page import="content.LocalContent"%>
<%@ page import="content.PccContent"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'localContent.label', default: 'LocalContent')}" />
<title><g:message code="default.koppel.label"
	args="[entityName]" /></title>
</head>
<body>
<div class="nav"><span class="menuButton"><a class="home"
	href="${createLink(uri: '/')}"><g:message code="default.home.label" /></a></span>
<span class="menuButton"><g:link class="list" action="list">
	<g:message code="default.list.label" args="[entityName]" />
</g:link></span></div>
<div class="body">
<h1><g:message code="default.koppel.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> <g:hasErrors bean="${localContentInstance}">
	<div class="errors"><g:renderErrors
		bean="${localContentInstance}" as="list" /></div>
</g:hasErrors> <g:form action="koppel">
	<g:select name="id" optionKey="id" from="${LocalContent.list()}" value="${localContentInstance?.id}" noSelection="['null': '[-Kies een te koppelen item]']"/>
	<div class="buttons"><span class="button"><g:submitButton
		name="create" class="save"
		value="${message(code: 'default.button.submit', default: 'Submit')}" /></span>
	</div>
</g:form> 
<br />
<br />
<g:if test="${localContentInstance}">
	<g:form action="createLinks">
		<div class="dialog">
		<table>
			<tbody>
				<g:hiddenField name="id" value="${localContentInstance?.id}" />
				<g:hiddenField name="version"
					value="${localContentInstance?.version}" />

				<tr class="prop">
					<td valign="top" class="name"><g:message
						code="localContent.title.label" default="Title" /></td>

					<td valign="top" class="value">
					${fieldValue(bean: localContentInstance, field: "title")}
					</td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
						code="localContent.createReferences.label"
						default="Kies koppelingen" /></td>

					<td valign="top" class="value"><g:select name="content.id"
						optionKey="id" from="${PccContent.list()}"
						value="${localContentInstance?.referencingContent?.id}"
						noSelection="['null': '[-Kies te koppelen items]']"
						multiple="true" /></td>

				</tr>
			</tbody>
		</table>
		</div>
		<div class="buttons"><span class="button"><g:submitButton
			name="create" class="save"
			value="${message(code: 'default.button.koppel.label', default: 'koppel')}" /></span>
		</div>
	</g:form>
</g:if></div>
</body>
</html>
