package content

import grails.converters.JSON

class PccContentController {

	def IRMailService
	def IRLogInTableService
	def ContentService

	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}

	def scaffold = true

	def sendMail = {
		//TODO: Message voor vaste teksten.
		log.debug "send Mail : ${params}"
		List ccList = []
		def pccContentInstance = PccContent.get(params.contentId)
		
		def msgBody = "Beste ${params.nameReceiver},<br/><br/>"
		if (params.messageSender) {
			msgBody += "Notitie van ${params.nameSender}: <br/>"
			msgBody += params.messageSender
			msgBody += "<br/><br/>"
		}
		def msgHeader = "Bericht doorgestuurd door ${params.nameSender}"
		msgBody += """\
			${ContentService.getHeader(pccContentInstance)}
			<br><br>
			${ContentService.getPublicInformation(pccContentInstance)}
			"""

		if (1==1) {
			IRMailService.sendIRMailasHTML(
					params.email.split(',').toList(),
					ccList,
					msgHeader,
					msgBody)
		}

		IRLogInTableService.log("mail verstuurd (id ${params.objectId}) aan ${params.email}", request)
		
		log.debug "msg body: ${msgBody}"
		
		render "<br>Mail verstuurd"
	}

	def showRelatedContent = {
		render(template:"showContent",model:[contentId:params.contentId])
	}
	
	def startProcess = {
		redirect(controller:params.wfcontroller, action: "start")
	}
	
	def doRating = {
		log.debug "Bijwerken rating ${params.ratingValue} voor ${params.contentId}"
		def pccContentInstance = PccContent.get(params.contentId)
		def ratingValue
		if (params?.ratingValue.toDouble()>0) {
			ratingValue = ContentService.setRating(pccContentInstance, params.ratingValue).toDouble().round(1)
		} else {
			ratingValue = pccContentInstance.ratingValue.toDouble().round(1)
		}
		log.debug "Nieuwe waarde : ${ratingValue} ${pccContentInstance.numberOfRates}"
		def returnValue = ['ratingValue':ratingValue, 'numberOfRates': pccContentInstance.numberOfRates]
		render returnValue as JSON
	}
	
}
