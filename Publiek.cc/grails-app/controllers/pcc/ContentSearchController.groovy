package pcc
import org.compass.core.engine.SearchEngineQueryParseException
import org.springframework.util.ClassUtils

class ContentSearchController {

    def searchableService
    def IRLogInTableService
	def contentService
	def IRUtilsService
    def zoekKanaal = "internet"
    def generiekKanaal = "generiek"

    def sduPdcClassName = "content.SduPdc"
    def accVacClassName = "content.AccVac"

    def beforeInterceptor = {
        log.debug "Tracing action ${actionUri} with ${params} "
		if (params.zaaksysteem_ident) {
			session?.zaaksysteem_ident = params.zaaksysteem_ident
			log.debug("setting session var zaaksysteem_ident with '${params.zaaksysteem_ident}'")
		}
    }

	def search = {
		return [breadcrumbs:IRUtilsService.createFirstPccBreadcrumb("pccContent")]
	}
	
	def searchResult = {
		//onthouden en zetten van de vinkjes in de sessie
		if (params.searchItems) {//set session searchItems if available from params
			session?.searchItems = params.searchItems
		} else if (session?.searchItems) {//if not from params check session settings
			params.searchItems = session?.searchItems
		}		

		//Zoeken als er een argument is
		def searchResult = []
		if (params.q && !params.contentId) {
			IRLogInTableService.log("find '${params.q}'", request)
			try {
				searchResult = pccSearch(params)				
			} catch (Exception e) {
				log.debug "foutmelding!!!!!!!!"
			}

			//Als er een resultaat is, deze ene dan wel ophalen (behalve als er een gekozen is)
			if (searchResult.results?.size()==1 && (!params.id || params.rctrl) && !params.contentId) {
				params.contentId = searchResult.results[0].id
				//TODO Fragment: als er een gevonden is direct anchor naar resultaat
			}
		}
				
		//breadcrumbs bepalen
		def breadcrumbs = IRUtilsService.createFirstPccBreadcrumb("pccContent") //naar het zoekscherm
		if (params.q) { //naar de zoekresultaten
			def searchPars = params.clone()
			searchPars.remove('contentId')
			log.debug "zoek bc met ${searchPars}"
			breadcrumbs += 	IRUtilsService.createBreadcrumb ('contentSearch', 'searchResult', searchPars, message(code: 'search.result', default: 'zoekresultaat'), "pccContent")
		}
		
		render (template:'searchResult', model:[searchResult:searchResult, breadcrumbs:breadcrumbs])
	}
		
//	@deprecated
//	@TODO uitzoeken of dit idd deprecated is
//	def searchItem = {
//		render(template:"searchResult",model:params)		
////		render(template:"../pccContent/showContent",model:params)		
//	}
	
    def pccSearch(def params) {
        def pccSearchResult = searchableService.search(offset:0, max:10000) {
            must(queryString(params.q))
//            if (params.searchItems) {
//                if (!params.searchItems?.sduPdc) {
//                    mustNot(alias(sduPdcClassName))
//                }
//                if (!params.searchItems?.accVac) {
//                    mustNot(alias(accVacClassName))
//                }
//            }
            params
        }

        def suggestedQuery = searchableService.suggestQuery(params.q)
        if (!suggestedQuery.equals(params.q)) {
            pccSearchResult.suggestedQuery = suggestedQuery
        }

        //Ophalen kanaal specifieke resultaten 
        def kanaalResultaten = pccSearchResult.results.findAll {
            (it instanceof content.AccVac && (it.kanaal == zoekKanaal))
        }

        //Zoeken naar te verwijderen resultaten (generieke met specifieke kanaal en overige kanalen)
        def kanWegResults = pccSearchResult.results.findAll{
            it instanceof content.AccVac &&
                ( (it.identifier in kanaalResultaten.identifier && !it.kanaal.equals(zoekKanaal))||
                 !(it.kanaal in [generiekKanaal, zoekKanaal]))
        }
        pccSearchResult.results.removeAll(kanWegResults)

		pccSearchResult.total = pccSearchResult.results.size()
        def rOffset = params.offset?params.offset.toInteger():0
        def rMax = Math.min(rOffset+(params.max?params.max.toInteger():10),pccSearchResult.total-1)

        if (rMax>0) {
            pccSearchResult.results = pccSearchResult.results[rOffset..rMax]
        }

        return pccSearchResult
    }


//    def getAccVacTopHits(def maxResultItems) {
//        def c = content.AccVac.createCriteria()
//        def results = c.list {
//            projections {
//                groupProperty 'identifier'
//                sum 'viewCount' , "totaalViews"
//            }
//            maxResults maxResultItems
//            and{
//               order('totaalViews', 'desc')
//               //order('vraag','asc')
//            }
//
//        }
//
//        def pccFAQ = []
//        results.each {topResult ->
//            def pccFaQKanaal = content.AccVac.findByIdentifierAndKanaal(topResult[0], zoekKanaal)
//            pccFAQ += pccFaQKanaal?pccFaQKanaal:content.AccVac.findByIdentifierAndKanaal(topResult[0], generiekKanaal)
//        }
//        return pccFAQ
//    }
	
	def rebuildIndex = {
		def startingTime = System.currentTimeMillis()
		searchableService.reindex()
		log.debug "reindex is done!"
		render "<i>Index rebuild in ${(System.currentTimeMillis() - startingTime)/1000} seconds (at ${new java.util.Date()})</i>"
	}
	
}
