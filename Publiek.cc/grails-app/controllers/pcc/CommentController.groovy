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
package pcc

class CommentController {

    def scaffold = true
	
	//comment form methods
	def commentNew = {
		log.debug "new form with : ${params}"
		render (template:"post/commentForm", params:params, model:[objectId:params.objectId, objectDomain:params.objectDomain, type:params.type, divid:params.divid])
	}

	def commentEdit = {
		log.debug "form met : ${params}"
		render (template:"post/commentForm", params:params, model:[objectId:params.objectId, objectDomain:params.objectDomain, divid:params.divid])
	}

	def commentSave = {
		log.debug "save comment van '${request.remoteUser?:'anoniem'}' met : ${params}"

		if (params?.comment && params?.comment.trim() != "") {
			def cmnt
			if (params?.cmntId) {
				log.debug "edited comment"
				cmnt = Comment.get(params.cmntId)
			} else {
				log.debug "new comment - Type: ${pcc.CType.values().find{it.toString() == params.type}}"
				cmnt = new Comment()
				cmnt.objectDomain = params?.objectDomain
				cmnt.objectId     = params?.objectId.toLong()
				cmnt.type         = pcc.CType.values().find{it.toString() == params.type}
				cmnt.poster       = request?.remoteUser
			}
			cmnt.comment      = params?.comment
			cmnt.lastUpdated  = new Date()
			log.debug "comment length ${cmnt.comment.length()}"
			if (!cmnt.save(flush: true)) {
				log.debug "not saved !!!!!!!"
				cmnt.errors.each {
					log.debug it
				} 
			}
		}
		render (template:"post/commentOnItem", params:params, model:[objectId:params.objectId, objectDomain:params.objectDomain, type:params.type, divid:params.divid])
	}

	def commentDelete = {
		def commentInstance = Comment.get(params?.cmntId)
		commentInstance.delete(flush: true)
		render (template:"post/commentOnItem", params:params, model:[objectId:params.objectId, objectDomain:params.objectDomain, divid:params.divid])
	}

	def commentCancel = {
		log.debug "cancel : ${params}"
		if (params?.cmntId) {//from edit
			def cmnt = Comment.get(params?.cmntId)
			render (template:"post/listItem", params:params, model:[cmnt:cmnt, divid:params.divid])
		} else { //from new (todo!!)
			render (template:"post/newComment", params:params, model:[objectId:params.objectId, objectDomain:params.objectDomain, type:params.type, divid:params.divid])
		}
	}

}
