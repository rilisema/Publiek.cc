package pcc


import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ReportController {

    def exportService
    def CheckService

    def index = { }

    def defaultReport = {
        []
    }

    def export = {
        log.debug "export stmt: ${params}"
        if (params?.stmtId) {
            def aStmt = ir.ast.AnyStatement.get(params.stmtId)
			log.debug "stmt : ${aStmt}"
            def stmtResult = CheckService.getStatementResult(aStmt, params)

            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=${aStmt.name.replace(' ','_')}.${params.extension}")

            ArrayList resultSet = stmtResult.resultSet
            Map labels
            Map formatters 
            Map parameters
            List fields = [] //BS: in anyStatement versie 0.3 kan dit met stmtResultSet.columnNames
            if (resultSet.size() > 0 ){
                resultSet[0].each {col ->
                    fields.add(col.key.toString())
                }
            }

            exportService.export(params.format, response.outputStream, resultSet, fields, labels, formatters, parameters)
            log.debug "export stmt: ${params?.stmtId}"
        } else {
            redirect(action:'defaultReport')
        }
    }
}
