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

class ImpactiveImportService {

	static transactional = true
	//Importeren Impactive PDC's
	def importPDC() {
		log.info "Opstarten ophalen producten Impactive"
		def locations = ContentLocation.findAllLocationActiveByPublisherAndContentType(pcc.Publisher.Impactive, pcc.ContentType.PDC)

		log.debug "Aantal locaties: ${locations.size()}"
		def totaalAantalProducten = 0
		def fouten = 0

		locations.each { location ->

			log.debug "Ophalen XML locatie: ${location.url}"
			def url = new URL(location.url)
			def conn = url.openConnection()
			//			conn.addRequestProperty("accept","application/xml")
			def xmlProducten = new XmlSlurper().parse(conn.content)
			def allProds = xmlProducten.lijst.product
			totaalAantalProducten += allProds.size()

			allProds.eachWithIndex {product, volgnr ->
				log.debug "inlezen product ${volgnr+1}/${allProds.size()}"
				def inhoudOnderdelen = product.opusinhoud.onderdeel
				log.debug "Product Id: ${product.productID.toString()}"
				log.debug "Product: ${product.productnaam.toString()}"
				def kccprod = ImpactivePdc.findByProductId(product.productID.toString())
				//            log.debug "ImpactivePdc: $kccprod"
				if (kccprod == null) {
					//                log.debug "Nieuw product"
					kccprod = new ImpactivePdc()
				}
				kccprod.productId = product.productID.toString()
				kccprod.productNaam = product.productnaam.toString()
				kccprod.inhoud = product.inhoud.toString()
				kccprod.synoniemen = product.onderwerp2.toString()
				inhoudOnderdelen.each {onderdeel->
					//                log.debug "Onderdeel: ${onderdeel.@naam}"
					switch (onderdeel.@naam) {
						case "Voorwaarden":
							kccprod.voorwaarden = onderdeel.tekstwaarde
							break
						case "GangvanZaken":
							kccprod.gangVanZaken = onderdeel.tekstwaarde
							break
						case "Meenemen":
							kccprod.meenemen = onderdeel.tekstwaarde
							break
						case "Kosten":
							kccprod.kosten = onderdeel.tekstwaarde
							break
						case "Formulieren":					
							def externalLink = ExternalLink.findByUrlAndExternalLinkType(onderdeel.verwijzingenwaarde.verwijzing.url.toString(), ExternalLinkType.formulier)
							if (!externalLink) {
								externalLink = new ExternalLink(url: onderdeel.verwijzingenwaarde.verwijzing.url.toString(), description: onderdeel.verwijzingenwaarde.verwijzing.titel.toString(),externalLinkType: ExternalLinkType.formulier).save(flush:true)
							}
							kccprod.addToExternalLinks(externalLink)
							break
						case "Werkinstructie":
//		                    log.debug "Verwerken werkinstructie"
							kccprod.werkinstructies = ""
						//TODO verwerken werkinstructie in eigen veld
							onderdeel.werkinstructiewaarde.werkinstructiestap.each {
								//                        log.debug "Werkinstructie: ${it}"
								kccprod.werkinstructies += "<b>Stap ${it.@stapnummer.toString()}</b><br />"
								if (it.werkinstructieactie.toString() != "") {
									kccprod.werkinstructies += "${it.werkinstructieactie} <br />"
								}
								if (it.werkinstructievraagblok.toString() != "") {
									kccprod.werkinstructies += "Vraag: ${it.werkinstructievraagblok.werkinstructievraag.toString()} <br />"
									kccprod.werkinstructies += "Bij Ja: ${it.werkinstructievraagblok.werkinstructievervolgja.@vervolgactie.toString()}"
									if (it.werkinstructievraagblok.werkinstructievervolgja.@volgendestap.toString() != "") {
										kccprod.werkinstructies += "${it.werkinstructievraagblok.werkinstructievervolgja.@volgendestap.toString()} <br />"
									} else {
										kccprod.werkinstructies += "<br />"
									}
									kccprod.werkinstructies += "Bij Nee: ${it.werkinstructievraagblok.werkinstructievervolgnee.@vervolgactie.toString()}"
									if (it.werkinstructievraagblok.werkinstructievervolgnee.@volgendestap.toString() != "") {
										kccprod.werkinstructies += "${it.werkinstructievraagblok.werkinstructievervolgnee.@volgendestap.toString()} <br />"
									} else {
										kccprod.werkinstructies += "<br />"
									}
								}
							}
							kccprod.werkinstructies += "</p><br />"
						//                    log.debug "Grootte werkinstructies: ${kccprod.werkinstructies.size()}"
							break
					}
				}
				if (!kccprod.hasErrors() && kccprod.save(flush:true)) {
					//                                log.debug "ImpactivePdc ${kccprod.name} opgelagen"
				} else {
					//                log.debug "Grootte werkinstructies: ${kccprod.werkinstructies.size()}"
					log.debug "ImpactivePdc ${kccprod.name} niet opgeslagen: $kccprod.errors"
					fouten += 1
				}
			}
		}
		log.info "${totaalAantalProducten} producten verwerkt; ${fouten} fouten"
		log.info "Einde ophalen producten Impactive"
	}

	//Importeren Klantvragen uit FLAT file van Impactive
	def importKlantvragen() {
		log.info "Opstarten ophalen klantvragen Impactive"
		def location = ContentLocation.findLocationActiveByPublisherAndContentType(pcc.Publisher.Impactive, pcc.ContentType.Thema)
		log.debug "Ophalen XML locatie: ${location.url}"
		def url = new URL(location.url)
		def conn = url.openConnection()
		def xmlProducten = new XmlSlurper().parse(conn.content)
		def hoofdVragen = xmlProducten.klantvraag
		def source = "Impactive"
		def totaalAantalVragen = hoofdVragen.size()
		def fouten = 0
		def nietGevondenProducten = 0
		hoofdVragen.eachWithIndex {eenVraag, volgnr ->
			log.debug "Verwerken vraag: ${volgnr} / ${totaalAantalVragen}"
			
			//get itemValue and if not exists create one
			def itemValue = ItemValue.findByItemValueTypeAndCode(ItemValueType.CustomerQuestion,eenVraag.@referentie.toString())
			if (!itemValue) {
				itemValue = new ItemValue(itemValueType:ItemValueType.CustomerQuestion, code:eenVraag.@referentie.toString())
			}
			
			//set description
			itemValue.description=eenVraag.titel

			//get higher value if exists
			if (eenVraag.@moeder && eenVraag.@moeder != '') {
				itemValue.motherItem = ItemValue.findByItemValueTypeAndCode(ItemValueType.CustomerQuestion, eenVraag.@moeder.toString())
			}
			
			//process referencing product; add customerQuestion/itemValue to product
			def producten = eenVraag.productverwijzing
			producten.each { product ->
				def prod = ImpactivePdc.findByProductId(product.@referentie.toString())
				if (prod) {
					if (!prod.itemValues.id.contains(itemValue.id)) {
						prod.addToItemValues(itemValue)
					}
				} else {
					nietGevondenProducten += 1
					log.debug "PRODUCT NIET GEVONDEN !!! (${product.@referentie.toString()})"
				}
			}

			//save item Value/Customer question
			if (!itemValue.save(flush:true)) {
				log.debug "ItemValue ${itemValue.question} niet opgeslagen: $itemValue.errors"
				fouten += 1
			}

		}
		log.info "${totaalAantalVragen} klantvragen verwerkt; ${fouten} fouten; ${nietGevondenProducten} niet gevonden producten"
		log.info "Einde ophalen klantvragen Impactive"
	}

}
