package pcc

import grails.converters.*

class ContentLocationController {

    def scaffold = true
	
	def sduImportService
	def accImportService
	def impactiveImportService
	
	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} with ${params}"
	}
	
	def importContent = {
		def importList = []
		
		pcc.Publisher.each{publisher->
			def publisherImports = new Expando(name: publisher.toString(), importSet:[])
			pcc.ContentType.each{contentType->
				def importSetItem = new Expando(contentType : contentType.toString(),
				                                locations   : pcc.ContentLocation.findAllLocationActiveByPublisherAndContentType(publisher, contentType))
				if (importSetItem.locations) {
					publisherImports.importSet += importSetItem
				}
			}
			if (publisherImports.importSet.locations) {
				importList += publisherImports
			}
		}

		[importList:importList]
	}
	
	def importCotentItem = {
		
		//ICTU VAC's
		if (params.publisher.equals (pcc.Publisher.ICTU.toString()) && params.contentType.equals (pcc.ContentType.VAC.toString())) {
			accImportService.importAccVac()
			redirect(controller:"accVac", action:"list")			
		}
		//ICTU TiO's 
		else if (params.publisher.equals (pcc.Publisher.ICTU.toString()) && params.contentType.equals (pcc.ContentType.TiO.toString())) {
			accImportService.importAccTiO()
			redirect(controller:"itemValue", action: "list")
		} 
		//SDU PDC 
		else if (params.publisher.equals (pcc.Publisher.SDU.toString()) && params.contentType.equals (pcc.ContentType.PDC.toString())) {
			sduImportService.importSduPdc()
			redirect(controller:"sduPdc", action: "list")
		} 
		//Impactive PDC 
		else if (params.publisher.equals (pcc.Publisher.Impactive.toString()) && params.contentType.equals (pcc.ContentType.PDC.toString())) {
			impactiveImportService.importPDC()
			redirect(controller:"impactivePdc", action:"list")			
		} 
		//Impactive Thema
		else if (params.publisher.equals (pcc.Publisher.Impactive.toString()) && params.contentType.equals (pcc.ContentType.Thema.toString())) {
			impactiveImportService.importKlantvragen()
			redirect(controller:"itemValue", action:"list")			
		} 
		//Else go back to page with message...
		else {
			flash.message = "Import functie nog niet ontsloten voor uitgever '${params.publisher}' en content type '${params.contentType}' "
			redirect(action:"importContent", params:params)

		}
		
	}
	
	def importeerAlles = {
		DailyImportJob.triggerNow()
		//        exit
		redirect(controller: "contentSearch", action: "search")
	}
	
	def exportContent = {
		def exportList = []
		exportList += content.LocalFacility.getName()		

		[exportList:exportList]
	}
	
	def exportContentItem = {
		if (params.exportItem.equals(content.LocalFacility.getName())	) {
			log.debug "export ${params.exportItem}"
			def exportContent = content.LocalFacility.findAll()
			log.debug "export ${params.exportItem} all ${exportContent.size()}"

			StringWriter writer = new StringWriter()
			def builder = new groovy.xml.MarkupBuilder(writer)
			
			builder.localFacilties() {				
				localFacility (id:1,"Tess")
				exportContent.each {lFi->
					localFacility (id:lFi.id) {
						title(lFi.title)
						description(lFi.description)
						internalInformation(lFi.internalInformation)
						phoneNumber(lFi.phoneNumber)
					}
				}
			}
			
			writer.flush()

			log.debug "${writer}"
			
			render writer
		}
		
	}
	
}
