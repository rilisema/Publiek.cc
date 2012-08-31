package ir
import org.codehaus.groovy.grails.commons.*

class IRMailService {

    static transactional = true
    def mailService
    
    def sendIRMail(List mailTo, List mailCc, String mailSubject, String tekst) {
        if (mailTo?.size() + mailCc?.size() > 0) {
            log.debug "Versturen mail naar : ${mailTo} en ${mailCc} (${mailTo.size() + mailCc.size()})"
            def config = ConfigurationHolder.config
            mailService.sendMail {
                to mailTo.toArray()
                from config.grails.mail.default.from
                if (mailCc) cc mailCc.toArray()
                subject "$mailSubject"
                body "${tekst}"
            }
        }
    }

    def sendIRMailasHTML(List mailTo, List mailCc, String mailSubject, String tekst) {
        if (mailTo?.size() + mailCc?.size() > 0) {
            log.debug "Versturen mail naar : ${mailTo} en ${mailCc} (${mailTo.size() + mailCc.size()})"
            def config = ConfigurationHolder.config
            mailService.sendMail {
                to mailTo.toArray()
                from config.grails.mail.default.from
                if (mailCc) cc mailCc.toArray()
                subject "$mailSubject"
                html "${tekst}"
            }
        }
    }
}
