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

class LocalContentController {

    def scaffold = true
	
	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}
	
	def koppel = {
		def localContentInstance
		if (params.id) {
			if (params.id.equals("null")) {
				flash.message = "${message(code: 'localContent.reference.choosesource')}"
				return
			}
			localContentInstance = LocalContent.get(params.id)
			if(!localContentInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localContent.label', default: 'LocalContent'), params.id])}"
				return
			}			
		} 
		return [localContentInstance: localContentInstance]
	}
	
	def createLinks = {
		if(!params.id) {
			flash.message = "${message(code: 'localContent.reference.choosesource')}"
			redirect(action: koppel)
			return
		} else {		
			def localContentInstance = LocalContent.get(params.id)
			if(!localContentInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'localContent.label', default: 'LocalContent'), params.id])}"
				redirect(action: koppel)
				return
			} else {
				if(params.content.id) {
					params.content.id.each { targetId ->
						log.debug "id ${targetId} koppelen aan ${params.id}"
						def referencedLocalContentInstance = PccContent.get(targetId)
						if(!referencedLocalContentInstance) {
							flash.message = "${message(code: 'localContent.reference.notfound', args: [targetId])}"
							redirect(action: koppel)
							return
						}
						log.debug "koppeling vastleggen tussen ${localContentInstance} en ${referencedLocalContentInstance}"
						def pcr = new PccContentReferences(referencingContent:localContentInstance, referencedContent:referencedLocalContentInstance).save(flush:true)
						localContentInstance.addToReferencingContent(pcr)
						referencedLocalContentInstance.addToReferencedContent(pcr)
						localContentInstance.save(flush:true)
						if (localContentInstance.hasErrors()) {
							log.debug "Fouten bij vastleggen content refentie: ${localContentInstance.errors}"
						}
					}
				}
				redirect (action: list)
			}
		}
	}
}
