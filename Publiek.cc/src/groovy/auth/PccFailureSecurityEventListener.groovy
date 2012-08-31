package auth

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent


class PccFailureSecurityEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

	void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
		// handle the event
//		println "********************************************"
//		println "eventClass ${event.class}"
//		println "event " + event
//		println "********************************************"
//		println "Failed to login with username ${event.getAuthentication().getName()}"
		ir.lit.IRLogTable.withTransaction {
			def logItem = new ir.lit.IRLogTable(logText  :"inlog gefaald voor : '${event.getAuthentication().getName()}'"
					,user     :null
					,ipaddress:event.getAuthentication().getDetails().getRemoteAddress()
					)

			logItem.save()
		}
	}
}
