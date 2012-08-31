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

class ExternalLink {

	ExternalLinkType externalLinkType
    String url
    String description

    static hasMany = [contentReferences: PccContent]

    static belongsTo = PccContent
    
    static constraints = {
        url(url:true, nullable:true, blank: false)
        description(nullable:true, blank: false, validator: {
                val,obj ->
                if ((val && !obj.url)||(!val && obj.description)) {
                    return ['error.url.withoutdescription']
                }
        })
    }
	
	String toString() {
		if(description) return description
		if (url) return url
	}
}

public enum ExternalLinkType implements org.springframework.context.MessageSourceResolvable {
	moreInfo,
	contactInfo,
	productInfo,
	formulier

	Object[] getArguments() { [] as Object[] }

	String[] getCodes() {
		["${getClass().name}.${name()}"] as String[]
	}

	String getDefaultMessage() { name() }
}