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


class ItemValue {

	ItemValueType itemValueType
    String sourceScheme
    String code
    String description
    String url
    ItemValue motherItem

    static belongsTo = [PccContent, ItemValue]
    static hasMany = [childReferences : ItemValue, valueReferences: PccContent]
    
    static constraints = {
        sourceScheme(nullable:true)
        code(unique:'sourceScheme')
        description(nullable:true)
        url(nullable:true)
        motherItem(nullable:true)
    }

        String toString() {
        "${description}"
    }
}

public enum ItemValueType implements org.springframework.context.MessageSourceResolvable {
	subject,
	TiO,
	SduVindThema,
	CustomerQuestion

	Object[] getArguments() { [] as Object[] }

	String[] getCodes() {
		["${getClass().name}.${name()}"] as String[]
	}

	String getDefaultMessage() { name() }
}