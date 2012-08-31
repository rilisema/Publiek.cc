class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

//		"/"(view:"/index")
		"500"(view:'/error')
		
		//added for Publiek.cc
		"404"(view:'/notfound')
		"/" {
				controller = "contentSearch"
				action = "search"
			}
	
	}
}
