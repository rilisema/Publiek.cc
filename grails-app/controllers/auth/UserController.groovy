package auth

import java.util.List;
import java.util.Map;

import grails.converters.JSON

import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.dao.DataIntegrityViolationException

class UserController {
	
	def scaffold = true
	def springSecurityService
	
	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}	
	
	def saltSource
	def userCache
	
	def create = {
		[user: new User(params), authorityList: Role.list()]
	}
	
	def edit = {

		def person = User.get(params.id)
		if (!person) {
			flash.message = "User not found with id $params.id"
			redirect action: list
			return
		}

		return buildUserModel(person)
	}
	
	def show = {
		
				def person = User.get(params.id)
				if (!person) {
					flash.message = "User not found with id $params.id"
					redirect action: list
					return
				}
		
				return buildUserModel(person)
			}
	
	def save = {
		def user = new User(params)
		if (params.password) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user.password = springSecurityService.encodePassword(params.password, salt)
		}
		if (!user.save(flush: true)) {
			render view: 'create', model: [user: user, authorityList: sortedRoles()]
			return
		}

		addRoles(user)
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		redirect action: edit, id: user.id
	}
	
	def update = {
		def user = findById()
		if (!user) return
			long version = params.version.toLong()
			
		if (user.version > version) {
			user.errors.rejectValue 'version', "user.optimistic.locking.failure",
					"Another user has updated this User while you were editing."
			render view: 'edit', model: buildUserModel(user)
			return
		}

		def oldPassword = user.password
		user.properties = params
		if (params.password && !params.password.equals(oldPassword)) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user.password = springSecurityService.encodePassword(params.password, salt)
		}

		if (!user.save()) {
			render view: 'edit', model: buildUserModel(user)
			return
		}

		UserRole.removeAll user
		addRoles user
		userCache.removeUserFromCache user.username
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		redirect action: show, id: user.id
	}
	
	def delete = {

		def person = User.get(params.id)
		if (person) {
			def authPrincipal = springSecurityService.currentUser
			//avoid self-delete if the logged-in user is an admin
			if (!(authPrincipal instanceof String) && authPrincipal.username == person.username) {
				flash.message = "You can not delete yourself, please login as another admin and try again"
			}
			else {
				//first, delete this person from People_Authorities table.
				UserRole.removeAll person
				person.delete()
				userCache.removeUserFromCache person.username
				flash.message = "User $params.id deleted."
			}
		}
		else {
			flash.message = "User not found with id $params.id"
		}

		redirect action: list
	}

	protected void addRoles(user) {
		for (String key in params.keySet()) {
			if (key.contains('ROLE') && 'on' == params.get(key)) {
				UserRole.create user, Role.findByAuthority(key), true
			}
		}
	}

	protected Map buildUserModel(user) {
		log.debug "Opbouwen user model voor ${user}"
		List roles = sortedRoles()
		Set userRoleNames = user.authorities*.authority
		def granted = [:]
		def notGranted = [:]
		for (role in roles) {
			if (userRoleNames.contains(role.authority)) {
				granted[(role)] = userRoleNames.contains(role.authority)
			}
			else {
				notGranted[(role)] = userRoleNames.contains(role.authority)
			}
		}

		return [userInstance: user, roleMap: granted + notGranted]
	}

	protected findById() {
		def user = User.get(params.id)
		if (!user) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect action: list
		}

		user
	}

	protected List sortedRoles() {
		Role.list().sort { it.authority }
	}

}
