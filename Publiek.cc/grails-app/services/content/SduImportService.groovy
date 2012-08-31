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

class SduImportService {

    static transactional = true

	def importSduPdc() {
		log.info "Opstarten ophalen producten Sdu"
		def locations = pcc.ContentLocation.findAllLocationActiveByPublisherAndContentType(pcc.Publisher.SDU, pcc.ContentType.PDC)

		log.debug "Aantal locaties: ${locations.size()}"
		def totaalAantalProducten = 0
		def fouten = 0
		def aangepast = 0
		def nieuw = 0
		def verwerkteIdentifiers = []

		//Alle locaties aflopen
		locations.each { location ->
			log.debug "Ophalen XML locatie: ${location.url}"
			def url = new URL(location.url)
			def conn = url.openConnection()
			conn.addRequestProperty("accept","application/xml")
			def xmlProducten = new XmlSlurper().parse(conn.content)
			def allProds = xmlProducten.product
			totaalAantalProducten += allProds.size()

			//Alle producten ophalen van locatie
			allProds.eachWithIndex {product, volgnr ->
				log.debug "inlezen product ${volgnr+1}/${allProds.size()} : ${product.@id}"
				verwerkteIdentifiers += product.@id.toString()
				def sduProd = SduPdc.findByIdentifier(product.@id.toInteger())
				if (!sduProd) {
					log.debug "nieuw sdu product"
					sduProd = new SduPdc()
					sduProd.identifier = product.@id
					nieuw += 1
				}
				if (sduProd.modified != new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(product.updated.toString().replace("T"," "))) {
					aangepast += 1
					sduProd.modified = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(product.updated.toString().replace("T"," "))
					sduProd.naam = product.name
					sduProd.omschrijving = product.description
					sduProd.synoniemen = ""
					product.synoniemen.each {it->
						sduProd.synoniemen += it
					}
					if (product.vind_themes) {
						verwerkSduVindThema (product.vind_themes.vind_theme, sduProd, 'VindThema')
					}
					if (!sduProd.save(flush:true)) {
						log.debug "SDU Product niet opgeslagen : ${sduProd.identifier}"
						fouten += 1
					}
				}
			}
		}
		if (verwerkteIdentifiers) {
			//verwijderen niet geraakte items
			SduPdc.executeUpdate("delete SduPdc sp where identifier not in (:ids) ",[ids:verwerkteIdentifiers])
		}

		log.info "${totaalAantalProducten} producten verwerkt; ${aangepast} records aangepast; waarvan ${nieuw} nieuw; ${fouten} fouten"
		log.info "Einde ophalen producten SDU Producten"
	}
	
	def verwerkSduVindThema (themaLijst, product, sourceScheme) {
		themaLijst.each {thema->
			def themaNaam    = thema.name.toString()
			def themaSuper   = themaNaam.substring(0,themaNaam.indexOf(":"))
//            def themaSuperId = thema.id.toString().substring(0,1)
			def themaSub     = themaNaam.substring(themaNaam.indexOf(":")+1, themaNaam.length())
			def themaSubId   = thema.id.toString()
			log.debug "thema ${thema.id} : '${themaSuper}' - '${themaSub}'"

			def themaSuperInstance = ItemValue.findByItemValueTypeAndCode(ItemValueType.SduVindThema, themaSuper)
			if (!themaSuperInstance) {
				log.debug "nieuw super"
				themaSuperInstance = new ItemValue(itemValueType: ItemValueType.SduVindThema, sourceScheme:sourceScheme, code:themaSuper, description:themaSuper)
				themaSuperInstance.save(flush:true)
			}

			def themaSubInstance = ItemValue.findByItemValueTypeAndCode(ItemValueType.SduVindThema, themaSubId)
			if (!themaSubInstance) {
				themaSubInstance = new ItemValue(itemValueType: ItemValueType.SduVindThema, sourceScheme:sourceScheme, code:themaSubId, description:themaSub, motherItem:themaSuperInstance, url:themaNaam)
				themaSubInstance.save(flush:true)
			}
			product.addToItemValues(themaSubInstance)
		}

	}

}
