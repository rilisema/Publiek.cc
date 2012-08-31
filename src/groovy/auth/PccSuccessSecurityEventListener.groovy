package auth

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.activiti.ActivitiConstants

class PccSuccessSecurityEventListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		def sessionUsernameKey = ConfigurationHolder.config.activiti.sessionUsernameKey?:ActivitiConstants.DEFAULT_SESSION_USERNAME_KEY
		// handle the event
//		println "********************************************"
//		println "eventClass ${event.class}"
//		println "event " + event
//		println "********************************************"
//		println "${event.getAuthentication().getName()} is succesvol ingelogd!"
		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		def session = request.getSession(false)
		session[sessionUsernameKey] = event.getAuthentication().getName()
//		println "Sessie gebruikersnaam: ${session['username']}"
		ir.lit.IRLogTable.withTransaction {
			def logItem = new ir.lit.IRLogTable(logText  :"succesvol ingelogd"
					,user     :event.getAuthentication().getName()
					,ipaddress:event.getAuthentication().getDetails().getRemoteAddress()
					)

			logItem.save()

		}
	}
}
