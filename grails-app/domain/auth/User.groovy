package auth

class User implements org.activiti.engine.identity.User {
	
	String id
	String username
	String email
	String firstName
	String lastName
	String password
	String responsibilities
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	static constraints = {
		username blank: false, unique: true
		password blank: false
		email email: true, blank: false, unique: true
		firstName blank: false
		lastName blank: false
		responsibilities nullable:true
	}

	static mapping = {
		table '`user`'
		password column: '`password`'
		id generator: 'uuid'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	String toString() {
		responsibilities?"$username - ${responsibilities}":"$username"
	}
}