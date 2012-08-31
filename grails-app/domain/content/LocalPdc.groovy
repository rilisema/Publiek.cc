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

class LocalPdc extends LocalContent  {
	String description
	String internalInformation
	String contentSynonym

	static constraints = {
		description(nullable:false, maxSize:5000)
		internalInformation(nullable:false, maxSize:5000)
		contentSynonym(nullable:true, maxSize:5000)
	}

	static searchable = {
		description spellCheck:'include'
		contentSynonym spellCheck:'include'
	}

	String toString() {
		"${title}"
	}
	
}
