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


class SduPdc extends SduContent  {

	String identifier
	String naam
	String omschrijving
	String synoniemen
	
	static constraints = {
		identifier(nullable:false)
		naam(nullable:false)
		omschrijving(nullable:false, maxSize:5000)
		synoniemen(nullable:false, maxSize:5000)
	}

	static searchable = {
		naam spellCheck:'include'
		omschrijving spellCheck:'include'
		synoniemen spellCheck:'include'
	}

	String toString() {
		"${naam}"
	}
			
}
