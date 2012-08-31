/* Copyright 2011 Memio BV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package content

import pcc.ContentLocation

class AccImportService {

	static transactional = true
	def IRLogInTableService

	//Importeren Antwoorden ICTU
	def importAccVac() {
		log.info "Ophalen Antwoord Collectie VACs"
		IRLogInTableService.log("Import : ICTU Acc Vac - begin")
		
		def foutenInBestand = false
		def verwerkteIdentifiers = []
		def teVerwerkenKoppelingen = [:]
	
		log.debug "ophalen "
		def locations = ContentLocation.findAllLocationActiveByPublisherAndContentType(pcc.Publisher.ICTU, pcc.ContentType.VAC)

		log.debug "Aantal locaties: ${locations.size()}"
		locations.each { location ->
			def url = new URL(location.url)
			def conn = url.openConnection()
			conn.addRequestProperty("accept","application/xml")
			def xmlVacs = new XmlSlurper().parse(conn.content)
			def allVacs = xmlVacs.vac
			def totaalAantalVacs = allVacs.size()
			def totaalGewijzigd = 0
			def totaalNieuw = 0
			log.debug "Aantal in te lezen vacs: ${totaalAantalVacs}"
			def fouten = 0
			allVacs.eachWithIndex {anAnswer, inr ->
				def answerIsModified = false
				def answerIsNew = false
				log.debug "Verwerk ${inr+1}/${totaalAantalVacs}"
				//                log.debug "Op zoek naar VAC met identifier ${anAnswer.meta.owmskern.identifier.toString()}"
				verwerkteIdentifiers += anAnswer.meta.owmskern.identifier.toString()
				def answer = AccVac.findByIdentifierAndKanaal(anAnswer.meta.owmskern.identifier.toString(), anAnswer.body.kanaal.@type.toString())
				if (!answer) {
					totaalNieuw++
					log.debug "new vac: ${anAnswer.meta.owmskern.identifier.toString()}"
					answerIsNew = true
					answer = new AccVac()
					answer.identifier = anAnswer.meta.owmskern.identifier.toString()
					answer.kanaal = anAnswer.body.kanaal.@type
				} else {
					if (answer.modified != new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(anAnswer.meta.owmskern.modified.toString().replace("T"," "))) {
						totaalGewijzigd++
						answerIsModified = true
						//clean object: remove external Links 
						def oiVwz = answer.externalLinks.collect {it}
						log.debug "Verwijderen external links: ${oiVwz.size()}"
						oiVwz.each { oiExternalLink ->
							answer.removeFromExternalLinks(oiExternalLink)
							//oiExternalLink.delete(flush:true)
						}
						//clean object: remove external Links 
						def itemValueList = answer.itemValues.collect{it}
						log.debug "Verwijderen itemValue verwijzingen: ${itemValueList.size()}"
						itemValueList.each { itemValueInstance ->
							answer.removeFromItemValues(itemValueInstance)
						}
						//clean object: remove referencing links
						def refContentList = answer.referencingContent.collect{it}
						log.debug "Verwijderen vac referenties: ${refContentList.size()}"
						refContentList.each { refContentInstance->
							answer.removeFromReferencingContent(refContentInstance)
							refContentInstance.delete(flush:true) 
						}
					}
					//                    log.debug "verwijzingen naar VAC's, overige info en pccSubjects verwijderen"
				}
				if (answerIsModified || answerIsNew) {
					answer.title = anAnswer.meta.owmskern.title
					answer.vraag = anAnswer.body.kanaal.vraag
					answer.antwoord = anAnswer.body.kanaal.antwoord
					if (answer.antwoord.size() > 5000) {
						log.info "VAC antwoord te lang volgens specs: ${answer.identifier} - ${answer.vraag} lengte: ${answer.antwoord.size()} "
					}
					answer.kortAntwoord = anAnswer.body.kanaal.kortAntwoord
					if (answer.kortAntwoord.size() > 650) {
						log.info "VAC kortAntwoord te lang volgens specs: ${answer.identifier} - ${answer.vraag} lengte: ${answer.kortAntwoord.size()} "
					}
					answer.onderwaterAntwoord = anAnswer.body.kanaal.onderwaterantwoord
					answer.modified = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(anAnswer.meta.owmskern.modified.toString().replace("T"," "))
					answer.authority = anAnswer.meta.owmsmantel.authority
					log.debug "Subject: ${anAnswer.meta.owmsmantel.subject}"
					log.debug "Subject schema: ${anAnswer.meta.owmsmantel.subject.@scheme}"
					log.debug "Subject resource: ${anAnswer.meta.owmsmantel.subject.@resourceIdentifier}"
					
					//Verwerken itemValues - subjecten (PccSubjects)
					anAnswer.meta.owmsmantel.subject.each { subj ->
						log.debug "Subject $subj uitzoeken"
						def onderwerp
						def isSchemaEnCode = false
						def isUrl = false
						def isDescription = false
						if (subj.@scheme && subj != "") {
							def TiO = ItemValue.findByItemValueTypeAndCode(ItemValueType.TiO, subj.toString())
							if (TiO) {
								log.debug "Koppel TiO aan AccVac (${TiO.id} - ${answer.id} met ${answer.itemValues?.id})"
								answer.addToItemValues(TiO)
							} else {
								//Als geen Tio gevonden opvoeren als onderwerp
								isSchemaEnCode = true
								onderwerp = ItemValue.findBySourceSchemeAndCode(subj.@scheme.toString(), subj.toString())
							}
						} else if (subj.@resourceIdentifier) {
							log.debug "ItemValue is een verwijzing"
							isUrl = true
							onderwerp = ItemValue.findByUrl(subj.@resourceIdentifier.toString())
						} else if (subj != "") {
							log.debug "ItemValue is een onderwerp omschrijving"
							isDescription = true
							onderwerp = ItemValue.findByDescription(subj.toString)
						}
						if (!onderwerp && (isSchemaEnCode || isUrl || isDescription)) {
							log.debug "ItemValue is nieuw"
							onderwerp = new ItemValue(sourceScheme: subj.@scheme.toString(), url: subj.@resourceIdentifier.toString(), itemValueType: ItemValueType.subject)
							if (isSchemaEnCode) {
								onderwerp.code = subj.toString()
							} else if (isDescription) {
								onderwerp.description = subj.toString()
							}
							onderwerp.save(flush:true)
							answer.addToItemValues(onderwerp)
						}

					}
					
					// Verwerken verwijzingen naar andere VAC's
					anAnswer.body.verwijzingVac.each { verwijzingVac ->
						log.debug "Verwerken verwijzing VAC: ${verwijzingVac}"
						teVerwerkenKoppelingen[answer.identifier] = verwijzingVac.@resourceIdentifier.toString()
					}
					
					// Verwerken externe Links
					anAnswer.body.kanaal.verwijzingOverigeInfo.each { verwijzingOverigeInfo ->
						log.debug "Verwerken verwijzing overige Info ${verwijzingOverigeInfo}"
						if (verwijzingOverigeInfo.@resourceIdentifier.toString()) {
							def voi = ExternalLink.findByUrlAndExternalLinkType(verwijzingOverigeInfo.@resourceIdentifier.toString(), ExternalLinkType.moreInfo)
							if (!voi) {
								//                            log.debug "Nieuwe voi: ${verwijzingOverigeInfo.@resourceIdentifier.toString()}"
								voi = new ExternalLink(url: verwijzingOverigeInfo.@resourceIdentifier.toString(), description: verwijzingOverigeInfo.toString(),externalLinkType: ExternalLinkType.moreInfo).save(flush:true)
							}
							if (voi) {
								//                            log.debug "Toevoegen voi aan VAC"
								answer.addToExternalLinks(voi)
							}

						}
					}


					answer.impStatus = ImpStatus.Verwerkt
					log.debug "Vastleggen VAC: '$answer'"
					if (!answer.save(flush:true)) {
						foutenInBestand = true
						log.debug "Niet opgeslagen VAC: ${answer.identifier} -- ${answer.vraag} (lengte antwoord : ${answer.antwoord.size()}; lengte kort antwoord : ${answer.kortAntwoord.size()}) "
						fouten += 1
					}
				}
			}
			
			//Verwerken gevonden koppelingen in content references
			if (teVerwerkenKoppelingen) {
				teVerwerkenKoppelingen.each { idNaar, idVan ->
					def vacVan = AccVac.findByIdentifier(idVan)
					def vacNaar = AccVac.findByIdentifier(idNaar)
					if (vacVan && vacNaar) {
						log.debug "verwerken koppeling van ${idVan} naar ${idNaar}"
						
						def pcr = new PccContentReferences(referencingContent:vacVan, referencedContent:vacNaar).save(flush:true)
						vacVan.addToReferencingContent(pcr)
						vacVan.save(flush:true)
						vacNaar.addToReferencedContent(pcr)
						vacNaar.save(flush:true)
						
					} else {
						log.debug "Vastleggen VAC vewijzing van ${idVan} niet gelukt naar ${idNaar}"
					}
				}
			}

			log.info "${totaalAantalVacs} VACs verwerkt; ${totaalNieuw}; Nieuwe ${totaalGewijzigd}; ${fouten} fouten"				

		}
		
		//Verwijderen niet geleverde VAC's
		if (verwerkteIdentifiers) {
			def accVacList = content.AccVac.findAllByIdentifierNotInList(verwerkteIdentifiers)
			accVacList.each {accVac->
				accVac.delete(flush: true)
			}
			log.debug "${accVacList.size()} acc Vac's deleted!!"
		}

		if (!foutenInBestand) {
			//TODO : terug draaien import bij fouten
		}

		IRLogInTableService.log("Import : ICTU Acc Vac - end")		
		log.info "Einde ophalen VACs Antwoord Collectie"		
	}

	def importAccTiO() {
		// ophalen TiO uit http://standaarden.overheid.nl/owms/terms/ThemaindelingOverheid.xml
		log.info "Ophalen ICTU TiOs"
		def location = ContentLocation.findByPublisherAndContentType(pcc.Publisher.ICTU, pcc.ContentType.TiO)
		log.debug "Ophalen XML uit locatie: ${location.url}"
		def url = new URL(location.url)
		def conn = url.openConnection()
		conn.addRequestProperty("accept","application/xml")
		def xmlTiOs = new XmlSlurper().parse(conn.content)
		log.debug "Aantal te verwerken TiO's: ${xmlTiOs.size()}"
		def sourceScheme = xmlTiOs.@name.toString()
		log.debug "Bronschema : ${sourceScheme}"
		def allTiOs = xmlTiOs.value
		def totaalAantalTios = allTiOs.size()
		def fouten = 0
		importTioTree(allTiOs, sourceScheme, null, fouten)
		log.info "${totaalAantalTios} TiOs verwerkt; ${fouten} fouten"
		log.info "einde ophalen ICTU Tios"
	}

	def importTioTree(def tioTree, String sourceScheme, ItemValue hogerItem, def fouten) {
		def totaalAantal = tioTree.size()
		tioTree.eachWithIndex {tio, volgnr->
			if (!hogerItem) {log.debug "Import thema : ${volgnr+1}/${totaalAantal}"}

			def theTio = ItemValue.findByItemValueTypeAndCode(ItemValueType.TiO,tio.@id.toString())
			if (!theTio) {
				theTio = new ItemValue(sourceScheme:sourceScheme, code:tio.@id.toString(), itemValueType: ItemValueType.TiO)
			}
			theTio.description = tio.@prefLabel.toString()
			theTio.url = tio.@resourceIdentifier.toString()
			theTio.motherItem = hogerItem
			if (!theTio.save(flush:true)) {
				fouten += 1
			}
			log.debug "theTio : ${theTio.id} - ${hogerItem?.id}"
			if (tio.value) {
				importTioTree(tio.value, sourceScheme, theTio, fouten)
			}
		}
	}


}
