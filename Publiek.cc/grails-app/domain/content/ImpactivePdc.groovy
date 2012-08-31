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

class ImpactivePdc extends ImpactiveContent {
	String productNaam
	String productId
	String inhoud
	String synoniemen
	String voorwaarden
	String gangVanZaken
	String meenemen 
	String werkinstructies
	String kosten

    Integer viewCount = 0

    static searchable = {
	  productNaam:'include'
	  inhoud:'include'
	  synoniemen:'include'	  
	  voorwaarden:'include'
	  gangVanZaken:'include'
	  meenemen:'include'
	  kosten:'include'
	  
    }
	
    static constraints = {
		productNaam(nullable:false, blank:false)
		productId(nullable:true)
		inhoud(nullable:true, maxSize:20000)
		synoniemen(nullable:true, maxSize:20000)
		voorwaarden(nullable:true, maxSize:20000)
		gangVanZaken(nullable:true, maxSize:20000)
		meenemen(nullable:true, maxSize:20000)
		werkinstructies(nullable:true, maxSize:50000)
		kosten(nullable:true, maxSize:20000)
    }

        String toString() {
        "${productNaam}"
    }
}
