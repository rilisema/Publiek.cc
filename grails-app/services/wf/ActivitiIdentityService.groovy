package wf

import org.activiti.engine.impl.identity.*
import auth.User
import auth.Role

class ActivitiIdentityService {
	def identityService
	static transactional = true

	//Activiti Users
//	def createUser(User aUser) {
//		def usr = identityService.newUser(aUser.username)
//		saveActivitiUser(aUser, usr)
//	}
//
//	def deleteUser(User aUser) {
//		identityService.deleteUser(aUser.username)
//	}
//
//	def updateUser(User aUser) {
//		def usr = identityService.createUserQuery().userId(aUser.username).singleResult()
//		if (!usr) {
//			createUser(aUser)
//		} else {
//			saveActivitiUser(aUser, usr)
//		}
//	}
//
//	def getUser(User aUser) {
//		return identityService.createUserQuery().userId(aUser.username).singleResult()
//	}
//
//	private void saveActivitiUser(User aUser, UserEntity usr) {
//		usr.firstName = aUser.username
//		usr.lastName = aUser.username
//		usr.email= aUser.email
//		usr.password= aUser.username
//		identityService.saveUser(usr)
//	}
//
//	//Activiti Groups
//	def createGroup(Role aRole) {
//		def role = identityService.newGroup(aRole.authority)
//		saveActivitiGroup(aRole, role)
//	}
//
//	def deleteGroup(Role aRole) {
//		identityService.deleteGroup(aRole.authority)
//	}
//
//	def updateGroup(Role aRole) {
//		def role = identityService.createGroupQuery().groupId(aRole.authority).singleResult()
//		if (!role) {
//			createGroup(aRole)
//		} else {
//			saveActivitiGroup(aRole, role)
//		}
//	}
//
//	def getGroup(Role aRole) {
//		return identityService.createGroupQuery().groupId(aRole.authority).singleResult()
//	}
//
//	private void saveActivitiGroup(Role aRole, GroupEntity grp) {
//		grp.id = aRole.authority
//		grp.name = aRole.authority
//		grp.type = "assignment"
//		identityService.saveGroup(grp)
//	}
//	// Memberships
//
//	def createMembership(User aUser, Role aRole) {
//		log.debug "Membership maken voor ${aUser.username} bij groep ${aRole.authority}"
//		def isMember = identityService.createUserQuery().userId(aUser.username).memberOfGroup(aRole.authority).singleResult()
//		if (!isMember) identityService.createMembership(aUser.username, aRole.authority)
//	}
//
//	def deleteMembership(User aUser, Role aRole) {
//		identityService.deleteMembership(aUser.username, aRole.authority)
//	}
//
//	def AddOrUpdateAllUsers() {
//		User.list().each { usr ->
//			log.debug "User: ${usr}"
//			updateUser(usr)
//			usr.authorities.each { role ->
//				log.debug "Rol: ${role}"
//				updateGroup(role)
//				createMembership(usr, role)
//			}
//		}
//	}
}
