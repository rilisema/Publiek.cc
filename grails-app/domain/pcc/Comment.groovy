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

class Comment {
    String comment
    CType  type
    String poster  //TODO: refactor to user_id
    Date   createDate = new Date()
    Date   lastUpdated 
    String objectDomain
    Long   objectId


    static constraints = {
        comment(nullable:false, blank:false, maxSize:1000)
        type(nullable:true)
        poster(nullable:true)
        createDate(nullable:true)
        lastUpdated(nullable:true)
        objectDomain(nullable:true)
        objectId(nullable:true)
    }
}

public enum CType implements org.springframework.context.MessageSourceResolvable {
    comment,
    complain,
    suggestion

    Object[] getArguments() { [] as Object[] }

    String[] getCodes() {
        ["${getClass().name}.${name()}"] as String[]
    }

    String getDefaultMessage() { name() }
}