/* Copyright 2011 Memio BV
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package ir

class IRUtilsService {

    static transactional = true
	def messageSource
	
	def createBreadcrumb(ctrl, action, pars, msg, update) {
		def breadcrumb = new Expando(ctrl:ctrl, action:action, pars:pars, msg:msg, update:update)
		return breadcrumb
	}
	
	def createFirstPccBreadcrumb(update) {
		def breadcrumbs = []
		Object[] testArgs = {}
		/* TODO: Locale dynamisch ophalen */
		def msg = messageSource.resolveCode("search.knowledgebase.label", new java.util.Locale("NL")).format(testArgs)
		breadcrumbs += createBreadcrumb ("contentSearch", "searchResult", null, msg, update)
		return breadcrumbs
	}
	
}
