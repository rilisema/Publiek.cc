package demo

class KlantController {

    def scaffold = Klant

    def beforeInterceptor = {
        log.debug "Tracing action ${actionUri} with ${params}"
    }

    def klantBeeld = {
        def klanten
        def klant
        if (params.id || (params.formUsed && (params.achternaam || params.postcode || params.huisnummer || params.BSN || params.geboortedatum))) {
            log.debug "Form  is gebruikt, dus klant zoeken!"
            if(params.id) {
                log.debug "klant is gekozen"
                klant = Klant.get(params.id)
                if (!klant) {
                    flash.message = "FOUT: Klant niet gevonden!"
                    return
                }
            }
            else {
                klanten = Klant.withCriteria {
                    if(params.achternaam) {
                        log.debug "Achternaam: $params.achternaam"
                        ilike('achternaam',"%"+params.achternaam+"%")
                    }
                    if (params.postcode) {
                        log.debug "postcode: $params.postcode"
                        eq('postcode',params.postcode)
                    }
                    if(params.huisnummer) {
                        log.debug "huisnummer: $params.huisnummer"
                        eq('huisnummer',params.huisnummer.toInteger())
                    }
                    if(params.BSN) {
                        log.debug "BSN: $params.BSN"
                        eq('BSN',params.BSN.toInteger())
                    }
                    if(params.geboortedatum) {
                        log.debug "geboortedatum: $params.geboortedatum"
                        eq('geboortedatum', params.geboortedatum)
                    }
                }
                if (klanten.size()==1) {
                    klant = klanten[0]
                }
            }
        }
        return [klanten: klanten, pars: params, klantInstance: klant]
    }

}
