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

class AccVac extends AccContent {

	String  vraag
	String  antwoord
	String  kanaal
	String  kortAntwoord
	String  onderwaterAntwoord

	static searchable = {
		vraag spellCheck:'include'
		antwoord spellCheck:'include'
		kortAntwoord spellCheck:'include'
		onderwaterAntwoord spellCheck:'include'
	}

	static constraints = {
		kanaal(nullable:false)
		vraag(nullable:false,blank:false)
		// Wijziging omdat ICTU eigen content onjuistheden bevat!!!
		kortAntwoord(nullable:false, maxSize:5000)
		antwoord(nullable:true, maxSize:20000)
		//        Onderstaand grootte volgens ICTU standaard
		//        kortAntwoord(nullable:false, maxSize:650)
		//        antwoord(nullable:true, maxSize:5000)
		onderwaterAntwoord(nullable:true, maxSize:1000)
	}

	String toString() {
		"${vraag}"
	}

}
