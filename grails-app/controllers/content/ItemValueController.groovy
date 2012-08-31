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
package content

class ItemValueController {

    def scaffold = true
	def IRUtilsService
	
	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}
	
	def navigatie = {
		def navPars = getNavigationParams(params)
		return [moederVraag:navPars.motherItem, navItems: navPars.themas, breadcrumbs: navPars.breadcrumbs, themeBreadcrumbs:navPars.themeBreadcrumbs, accVacId:params.accVacId, navType:params.navType]
	}
	
	def valueList = {
		params.breadcrumbs = params.breadcrumbs?.tokenize( "[, ]" ).collect { it as Integer } 
		def navPars = getNavigationParams(params)
		log.debug "navPars : ${navPars}"
		log.debug "navPars : ${navPars.breadcrumbs}"
		render (template:"showValueList", model:[moederVraag:navPars?.motherItem, navItems: navPars.themas, breadcrumbs: navPars.breadcrumbs, themeBreadcrumbs:navPars.themeBreadcrumbs, accVacId:params.accVacId, navType:params.navType])
	}
	
	def showContent = {
		render(template:"../pccContent/showContent",model:params)		
	}
	
	def getNavigationParams(def params) {
		log.debug "getNavigationParams params : ${params}" 
		def updateDiv = "pccContent"
		def navPars = new Expando(themeBreadcrumbs: IRUtilsService.createFirstPccBreadcrumb(updateDiv), breadcrumbs: [])
		
		navPars.themeBreadcrumbs += IRUtilsService.createBreadcrumb ("itemValue", "valueList", [navType:params.navType, breadrumbs:navPars.breadcrumbs], message(code: 'itemValue.navigatie.label', default: 'thema'), updateDiv)
		
		params.breadcrumbs.each{bcThemeId->
			navPars.themeBreadcrumbs += IRUtilsService.createBreadcrumb ("itemValue", "valueList", [id:bcThemeId, navType:params.navType, breadcrumbs:navPars.breadcrumbs], ItemValue.get(bcThemeId) , updateDiv)
			navPars.breadcrumbs += bcThemeId
		} 
				
		if (params.id) {
			navPars.themeBreadcrumbs += IRUtilsService.createBreadcrumb ("itemValue", "valueList", [id:params.id, navType:params.navType, breadcrumbs:navPars.breadcrumbs], ItemValue.get(params.id) , updateDiv)
			navPars.breadcrumbs += params.id
			navPars.motherItem = ItemValue.get(params.id)
			navPars.themas = ItemValue.findAllByMotherItem(navPars.motherItem)
		} else {
			navPars.themas = ItemValue.findAllByItemValueTypeAndMotherItemIsNull(params.navType)
		}
		return navPars		
	}
		
}
