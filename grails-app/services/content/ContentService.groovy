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

class ContentService {
		
    static transactional = true

	String getHeader(def contentInstance) {
		contentInstance.toString()
	}
	
	def setRating(def contentInstance, def addRatingValue) {
		log.debug "Opnieuw bepalen rating voor ${contentInstance.id} met waarde ${addRatingValue}"
		def newRating = (contentInstance.rating*contentInstance.numberOfRates + addRatingValue.toDouble()) / (contentInstance.numberOfRates+1)
		contentInstance.rating = newRating
		contentInstance.numberOfRates=contentInstance.numberOfRates+1
		contentInstance.save(flush:true)
		return newRating
	}
	
	String getPublicInformation(def contentInstance) {
		def publicInformation = "unknown content type for public information (${contentInstance.class})"
		if (contentInstance instanceof content.AccVac) {
			publicInformation = getPublicInformationFromVac(contentInstance)
		} else if (contentInstance instanceof content.SduPdc) {
			publicInformation = layoutContentItem("", contentInstance.omschrijving) 
		} else if (contentInstance instanceof content.ImpactivePdc) {
			publicInformation = getPublicInformationFromImpactivePdc(contentInstance)
		} else if (contentInstance instanceof content.LocalPdc) {
			publicInformation = layoutContentItem("", contentInstance.description)
		} else if (contentInstance instanceof content.LocalFacility) {
			publicInformation = getPublicInformationFromLocalFacility(contentInstance)
		}
		return publicInformation
	}
	
	String getPublicInformationFromImpactivePdc(def contentInstance) {
		def publicInformation = ""
		
		if (contentInstance.inhoud) {
			publicInformation += layoutContentItem("Inhoud", contentInstance.inhoud, 2)
		}
		
		if (contentInstance.voorwaarden) {
			publicInformation += layoutContentItem("Voorwaarden", contentInstance.voorwaarden, 2)
		}

		if (contentInstance.gangVanZaken) {
			publicInformation += layoutContentItem("Gang van zaken", contentInstance.gangVanZaken, 2)
		}

		if (contentInstance.meenemen) {
			publicInformation += layoutContentItem("Meenemen", contentInstance.meenemen, 2)
		}
		
		if (contentInstance.kosten) {
			publicInformation += layoutContentItem("Kosten", contentInstance.kosten, 2)
		}
		
		if (contentInstance.werkinstructies) {
			publicInformation += layoutContentItem("Werkinstructie", contentInstance.werkinstructies, 2)
		}
		return publicInformation

	}
	
	String getPublicInformationFromLocalFacility(def contentInstance) {
		def publicInformation = layoutContentItem("Locatie", contentInstance.description, 2)
		if (contentInstance.phoneNumber) {
			publicInformation += layoutContentItem("Telefoonnummer", contentInstance.phoneNumber, 2)			
		}
		return publicInformation 
	}
	
	String getPublicInformationFromVac(def contentInstance) {
		def publicInformation = ""
		if (contentInstance.kortAntwoord) {
			publicInformation += layoutContentItem("Kort antwoord", contentInstance.kortAntwoord, 2) 
		}
		
		if (contentInstance.antwoord) {
			publicInformation += layoutContentItem("Antwoord", contentInstance.antwoord, 2) 
		}
		return publicInformation
	}
	
	String getInternalInformation(def contentInstance) {
		def internalInformation
		def title = "Intern antwoord"
		if (contentInstance instanceof content.AccVac && contentInstance.onderwaterAntwoord) {
			internalInformation = layoutContentItem(title, contentInstance.onderwaterAntwoord, 2)
		} else if (contentInstance instanceof content.LocalFacility && contentInstance.internalInformation) {
			internalInformation = layoutContentItem(title, contentInstance.internalInformation, 2)
		} else if (contentInstance instanceof content.LocalPdc && contentInstance.internalInformation)	{
			internalInformation = layoutContentItem(title, contentInstance.internalInformation, 2)
		}
		log.debug "intern antwoord : ${internalInformation}"
		return internalInformation
	}
	
	String layoutContentItem (title, content, level) {
		def retValue
		if (content) {
			def levelTag = "h"+level?:2
			def sTitle = title?"<${levelTag}>${title}</${levelTag}>":""
			retValue = """
				<div class="pccContentPart">
					${sTitle}
					<div class="text-block">
						${content}
					</div class="text-block">
				</div>
			"""
		}
		return retValue
	}
	
	Integer incrementViewCount(def contentInstance) {
		contentInstance.viewCount += 1
		log.debug "save : ${contentInstance.save(flush:true)}"
		
		if (contentInstance.save(flush:true)) {
			log.debug "incremented viewcount : ${contentInstance.viewCount}"			
		} else {
			log.debug "incremented viewcount failed!!"
		}
	}	
	
	def getTabsWithInfo(def contentInstance) {
		def tabsWithInfo = []
		def tabContent
		log.debug "Tabs met content ophalen voor : ${contentInstance}"
		if (contentInstance instanceof content.AccVac || contentInstance instanceof content.LocalVac) {
			["Antwoord"].each { tabName ->
				tabsWithInfo += new Expando(tabName:tabName, tabContent: getTabContentFromVac(contentInstance, tabName))
			}
		} else if (contentInstance instanceof content.SduPdc) {
			["Samenvatting"].each { tabName ->
				tabsWithInfo += new Expando(tabName:tabName, tabContent: getTabContentFromSduPdc(contentInstance, tabName))
			}
		} else if (contentInstance instanceof content.ImpactivePdc) {
			["Samenvatting","Voorwaarden","Aanpak","Meenemen","Kosten"].each { tabName ->
				tabsWithInfo += new Expando(tabName:tabName, tabContent: getTabContentFromImpactivePdc(contentInstance, tabName))
			}
		} else if (contentInstance instanceof content.LocalPdc) {
			tabsWithInfo += new Expando(tabName:"Samenvatting", tabContent: layoutContentItem("", contentInstance.description,2))
		} else if (contentInstance instanceof content.LocalFacility) {
			tabsWithInfo += new Expando(tabName: "Samenvatting", tabContent: getPublicInformationFromLocalFacility(contentInstance))
		}
		
		return tabsWithInfo.findAll{it.tabContent}
	}
	
	
	
	String getTabContentFromVac(def contentInstance, String tabName) {
		def tabContent = ""
		if (tabName=="Antwoord") {
			tabContent = getPublicInformationFromVac(contentInstance)
		}
		log.debug "Antwoord content: ${tabContent}"
		return tabContent
	}


	String getTabContentFromImpactivePdc(def contentInstance, String tabName) {
		def tabContent
		switch (tabName) {
			case "Samenvatting":
				tabContent = layoutContentItem("", contentInstance.inhoud, 2)
				log.debug "Inhoud toegevoegd aan tab: ${contentInstance.inhoud}"
				break
			case "Voorwaarden":
				tabContent = layoutContentItem("", contentInstance.voorwaarden, 2)
				break
			case "Aanpak":
				tabContent = layoutContentItem("", contentInstance.gangVanZaken, 2)
				break
			case "Meenemen":
				tabContent = layoutContentItem("", contentInstance.meenemen, 2)
				break
			case "Kosten":
				tabContent = layoutContentItem("", contentInstance.kosten, 2)
				break
			case "Werkinstructies":
				tabContent = layoutContentItem("", contentInstance.werkinstructies, 2)
				break
		}
		log.debug "ImpactivePdc content: ${tabContent}"
		return tabContent
	}

	String getTabContentFromSduPdc(def contentInstance, String tabName) {
		def tabContent = ""
		switch (tabName) {
			case "Samenvatting":
				tabContent = layoutContentItem("", contentInstance.omschrijving, 2)
				break
		}
		log.debug "SduPdc content: ${tabContent}"
		return tabContent
	}
	
	def getBestViewedContent(def cntType) {
		def bestViewedItems = content.PccContent.withCriteria {
			if (cntType) {
				eq ('class', cntType)
			}
			maxResults 10
			order('viewCount','desc')
		}
		return bestViewedItems
	}
	
}
