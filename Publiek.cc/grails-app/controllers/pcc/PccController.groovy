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
package pcc

import javax.servlet.http.Cookie

class PccController {
	static {
		//for localhost testing only
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
		new javax.net.ssl.HostnameVerifier(){
 
			public boolean verify(String hostname,
					javax.net.ssl.SSLSession sslSession) {
				if (   hostname.equals("bussum-test.zaaksysteem.nl")
					|| hostname.equals("bussum.zaaksysteem.nl")) {
//				if (1==1) {
					return true
				} 
				return false
			}
		})
	}
	
	def getExternalPage = {
		def urlResponse = "no external url defined"
		if (params.url) {
			def url = new URL(params.url)
			def conn = url.openConnection()
			urlResponse = conn.content.text
		}
		render urlResponse
	}
	
	def changeCss = {
		log.debug "css : ${params}"
		def cookieName = 'customCss'
		if (params.css) {
			log.debug "set cookie"
			def cookie = new Cookie(cookieName, params.css)
			cookie.setMaxAge(60*60*24*365*5)
			cookie.setPath(request.getContextPath())
			response.addCookie(cookie)
		} else {
			def cookie = request.cookies.find { it.name == cookieName }
			if (cookie) {
				cookie.setMaxAge(0)
				cookie.setPath(request.getContextPath())
				response.addCookie(cookie)
			}
		}
		request.cookies.each {ckie ->
			log.debug "cookie : ${ckie.name} : ${ckie.value}"
		}
		redirect(controller: "contentSearch", action: "search")
	}
	
	def toggleMain = {
		def mainPar = ir.sys.IRSystemPar.findByName("main")
		if (mainPar) {
			mainPar.setName("mainToggleOff")
			mainPar.save(flush:true)
		} else {
			mainPar = ir.sys.IRSystemPar.findByName("mainToggleOff")
			if (mainPar) {
				mainPar.setName("main")
				mainPar.save(flush:true)
			}
		}		
		redirect(controller: "contentSearch", action: "search")
	}

	def beforeInterceptor = {
		log.debug "Tracing action ${actionUri} "
		log.debug "with ${params}"
	}
	
}
