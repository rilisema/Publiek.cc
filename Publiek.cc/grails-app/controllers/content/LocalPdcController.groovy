package content

class LocalPdcController {

    def scaffold = true
	
	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}

}
