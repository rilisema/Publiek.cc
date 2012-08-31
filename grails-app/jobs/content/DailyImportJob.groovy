package content

import content.AccImportService;
import content.SduImportService;
import content.ImpactiveImportService;

class DailyImportJob {
    def AccImportService
	def SduImportService
	def ImpactiveImportService;
	
    def IRLogInTableService
    def IRSystemParService
    
    static triggers = {
        cron name:'cronTrigger', startDelay:10000, cronExpression: '1 0 0 * * ?' //sec min hrs dayMonth month year: 1 seconde over 12
    }

    def execute() {
       IRLogInTableService.log("Import job: begin")

	   if (IRSystemParService.getParValue("importAccContent")?.equals("true")) {
		   importAccContent()
	   }

	   if (IRSystemParService.getParValue("importSduContent")?.equals("true")) {
		   importSduContent()
	   }
	   
	   if (IRSystemParService.getParValue("importImpactiveContent")?.equals("true")) {
		   importImpactiveContent()
	   }
	   	   
       IRLogInTableService.log("Import job: end")
    }
	   
	def importAccContent() {
		try {
			IRLogInTableService.log("Import job AccContent: begin")
			AccImportService.importAccVac()
			AccImportService.importAccTiO()			
			IRLogInTableService.log("Import job AccContent: end")
		} catch (Exception e) {
			IRLogInTableService.log("Import job AccContent: fout - ${e}")
		}
		
	}
	
	def importSduContent() {
		try {
			IRLogInTableService.log("Import job Sdu Content: begin")
			SduImportService.importSduPdc()
			IRLogInTableService.log("Import job Sdu Content: end")
		} catch (Exception e) {
			IRLogInTableService.log("Import job Sdu Content: fout - ${e}")
		}
	}
	
	def importImpactiveContent() {
		try {
			IRLogInTableService.log("Import job Impactive content: begin")
			ImpactiveImportService.importPDC()
			IRLogInTableService.log("Import job Impactive content: end")
		} catch (Exception e) {
			IRLogInTableService.log("Import job Impactive content: fout - ${e}")
		}
	}
}
