package pcc


class TerugbelAfspraakController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static activiti = true

    def beforeInterceptor = {
        log.debug "Tracing action ${actionUri} with ${params}"
    }

    def index = {
        redirect(action: "list", params: params)
    }

    def start = {
		log.debug "parameters: ${params}"
		log.debug "Sessie user: ${session['username']}"
        start(params)
    }
	
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [terugbelAfspraakInstanceList: TerugbelAfspraak.list(params), 
            terugbelAfspraakInstanceTotal: TerugbelAfspraak.count(),
            myTasksCount: assignedTasksCount]
    }

    def create = {
        def terugbelAfspraakInstance = new TerugbelAfspraak()
        terugbelAfspraakInstance.properties = params
        def users = activitiService.identityService.createUserQuery().memberOfGroup("ROLE_AFDELING").orderByUserId().asc().list()
        return [terugbelAfspraakInstance: terugbelAfspraakInstance,
            myTasksCount: assignedTasksCount, users: users]
    }

    def save = {
        def terugbelAfspraakInstance = new TerugbelAfspraak(params)
        if (terugbelAfspraakInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), terugbelAfspraakInstance.id])}"
            params.id = terugbelAfspraakInstance.id
//			params.stuurMail= terugbelAfspraakInstance.stuurMail
			params.taskUrl=createLink(controller:'task', action:'myTaskList', absolute: true)
            params.terugbellerEmail = auth.User.findByUsername(params.terugbeller)?.email
            if(!params.terugbellerEmail) {
                params.terugbellerEmail = "richard@informatieraadgevers.nl"
            }
			log.debug "Parameters voor proces: $params"
            if (params.complete) {
                completeTask(params)
            } else {
                params.action="show"
                saveTask(params)
            }
            redirect(action: "show", params: params)
        }
        else {
            render(view: "create", model: [terugbelAfspraakInstance: terugbelAfspraakInstance, myTasksCount: assignedTasksCount])
        }
    }

    def show = {
        def terugbelAfspraakInstance = TerugbelAfspraak.get(params.id)
        if (!terugbelAfspraakInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
            redirect(controller: "task", action: "myTaskList")
        }
        else {
            [terugbelAfspraakInstance: terugbelAfspraakInstance, myTasksCount: assignedTasksCount]
        }
    }

    def edit = {
        def terugbelAfspraakInstance = TerugbelAfspraak.get(params.id)
        if (!terugbelAfspraakInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
            redirect(controller: "task", action: "myTaskList")
        }
        else {
            def users = activitiService.identityService.createUserQuery().memberOfGroup("ROLE_AFDELING").orderByUserId().asc().list()
            [terugbelAfspraakInstance: terugbelAfspraakInstance, myTasksCount: assignedTasksCount, users: users]
        }
    }

    def update = {
        def terugbelAfspraakInstance = TerugbelAfspraak.get(params.id)
        if (terugbelAfspraakInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (terugbelAfspraakInstance.version > version) {
                    
                    terugbelAfspraakInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak')] as Object[], "Another user has updated this TerugbelAfspraak while you were editing")
                    render(view: "edit", model: [terugbelAfspraakInstance: terugbelAfspraakInstance, myTasksCount: assignedTasksCount])
                    return
                }
            }
            terugbelAfspraakInstance.properties = params
            params.terugbellerEmail = auth.User.get(params.terugbeller)?.email
            if(!params.terugbellerEmail) {
                params.terugbellerEmail = "richard@informatieraadgevers.nl"
            }
            if (!terugbelAfspraakInstance.hasErrors() && terugbelAfspraakInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), terugbelAfspraakInstance.id])}"
                Boolean isComplete = params["_action_update"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
                if (isComplete) {
                    completeTask(params)
                } else {
                    params.action="show"
                    saveTask(params)
                }
                redirect(action: "show", id: terugbelAfspraakInstance.id, params: [taskId:params.taskId, complete:isComplete?:null])
            }
            else {
                render(view: "edit", model: [terugbelAfspraakInstance: terugbelAfspraakInstance, myTasksCount: assignedTasksCount])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
            redirect(controller: "task", action: "myTaskList")
        }
    }

    def delete = {
        def terugbelAfspraakInstance = TerugbelAfspraak.get(params.id)
        if (terugbelAfspraakInstance) {
            try {
                terugbelAfspraakInstance.delete(flush: true)
                deleteTask(params.taskId)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
                redirect(controller: "task", action: "myTaskList")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
                redirect(action: "show", id: params.id, params: params)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'terugbelAfspraak.label', default: 'TerugbelAfspraak'), params.id])}"
            redirect(controller: "task", action: "myTaskList")
        }
    }
}
