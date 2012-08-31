import ir.sys.IRSystemPar
import pcc.ContentLocation
import pcc.ContentType
import pcc.Publisher

import auth.Role
import auth.Requestmap
import auth.User
import auth.UserRole

class BootStrap {
	def springSecurityService
	
    def init = { servletContext ->
		environments {			
			devMintlab {
				//createSystemPars()
				createUsersAndGroups()
			}
			testMintlab {
				def cssPar = IRSystemPar.findByName('main')
				if (!cssPar) {
					cssPar = new IRSystemPar(name:'main', value: '/layouts/mainBussum.gsp')
					cssPar.save(failOnError:true)
				}
				def importAccContent = IRSystemPar.findByName('importAccContent')
				if (!importAccContent) {
					importAccContent = new IRSystemPar(name:'importAccContent', value: 'true')
					importAccContent.save(failOnError:true)
				}
				def importSduContent = IRSystemPar.findByName('importSduContent')
				if (!importSduContent) {
					importSduContent = new IRSystemPar(name:'importSduContent', value: 'false')
					importSduContent.save(failOnError:true)
				}
				def importImpactiveContent = IRSystemPar.findByName('importImpactiveContent')
				if (!importImpactiveContent) {
					importImpactiveContent = new IRSystemPar(name:'importImpactiveContent', value: 'true')
					importImpactiveContent.save(failOnError:true)
				}
		
//				createSystemPars()
//				createUsersAndGroups()
//				createContentLocations()
			}
		}    
	}
    def destroy = {
    }
	
	private void createSystemPars() {
	}
	
	private void createContentLocations() {
//		def ictuVac = ContentLocation.findByUrl('http://content.antwoord.nl/service/vacs/antwoord/1_2_2')
//		if (!ictuVac) {
//			ictuVac = new ContentLocation(contentType: ContentType.VAC, publisher: Publisher.ICTU, url:'http://content.antwoord.nl/service/vacs/antwoord/1_2_2', name:'ICTU VAC')
//		}
//		(1,0,'Volledig productie','VAC',true,'ICTU','http://content.antwoord.nl/service/vacs/antwoord/1_2_2')
//		,(2,0,'SDU Producten','PDC',true,'SDU','http://xml.sduconnect.nl/product.xml?account_id=288&product_collection_id=977&view=collection_product_full&vers=3')
//		,(3,0,'Thema Indeling Overheid','TiO',true,'ICTU','http://standaarden.overheid.nl/owms/terms/ThemaindelingOverheid.xml')
//		,(4,1,'TEST omgeving ICTU','VAC',false,'ICTU','http://alfred.acc.oha.overheid.asp4all.nl/service/vacs/antwoord/1_2_2')
//		,(5,3,'Test opperwoude','PDC',false,'Impactive','http://partners.impactive.nl/content/techniek/xml/demo_opusplus.xml')
//		,(6,1,'GPDC Impactive Rotterdam','PDC',false,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=4')
//		,(7,3,'Rotterdam - Impactive theme (klantvragen)','Thema',false,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=3')
//		,(8,2,'Rotterdam - Producten Samenwerkende Catalogi','PDC',false,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=96b57f8b-7f20-423c-a97d-cc073db0ef14&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=1')
//		,(9,0,'Opperwoude - Producten Samenwerkende Catalogi','PDC',true,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=1')
//		,(10,0,'Opperwoude - GPDC Impactive','PDC',true,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=4')
//		,(11,1,'Opperwoude - Impactive theme (klantvragen)','Thema',true,'Impactive','http://www.opusservice.nl/getopusfeed/default.aspx?KID=9164b1d1-ada6-4087-ae6b-6a93014870b3&LID=ced1eb19-eee5-490c-932f-3d69fd2e6de8&CID=3');
		
	}

	private void createUsersAndGroups() {
		def userRole = Role.findByAuthority('ROLE_USER')
		if (!userRole) {
		 userRole = new Role(authority: 'ROLE_USER', name: 'User')
		 userRole.id = 'ROLE_USER'
		 log.debug "UserRole id: $userRole.id"
		 userRole.save(failOnError: true)
		}
		def adminRole = Role.findByAuthority('ROLE_ADMIN')
		if (!adminRole) {
		 adminRole = new Role(authority: 'ROLE_ADMIN', name: 'Manager')
		 adminRole.id = 'ROLE_ADMIN'
		 adminRole.save(failOnError: true)
		}
		def publicRole = Role.findByAuthority('ROLE_PUBLIC')
		if (!publicRole) {
		 publicRole = new Role(authority: 'ROLE_PUBLIC', name: 'Public')
		 publicRole.id = 'ROLE_PUBLIC'
		 publicRole.save(failOnError: true)
		}
		def kccRole = Role.findByAuthority('ROLE_KCC')
		if (!kccRole) {
		 kccRole = new Role(authority: 'ROLE_KCC', name: 'Public')
		 kccRole.id = 'ROLE_KCC'
		 kccRole.save(failOnError: true)
		}
		def afdelingRole = Role.findByAuthority('ROLE_AFDELING')
		if (!afdelingRole) {
		 afdelingRole = new Role(authority: 'ROLE_AFDELING', name: 'Public')
		 afdelingRole.id = 'ROLE_AFDELING'
		 afdelingRole.save(failOnError: true)
		}
		def bas = User.findByUsername('bas') ?: new User(
				username: 'bas',
				firstName: 'Bas',
				lastName: 'Sluijs',
				email: 'Bas@informatieraadgevers.nl',
				password: springSecurityService.encodePassword('bas'),
				enabled: true)
		bas.id=java.util.UUID.randomUUID().toString()
		bas.save(failOnError: true)
		def admin = User.findByUsername('admin') ?: new User(
				username: 'admin',
				firstName: 'admin',
				lastName: 'admin',
				email: 'admin@informatieraadgevers.nl',
				password: springSecurityService.encodePassword('admin'),
				enabled: true)
		admin.id=java.util.UUID.randomUUID().toString()
		admin.save(failOnError: true)
		def richard = User.findByUsername('richard') ?: new User(
				username: 'richard',
				firstName: 'Richard',
				lastName: 'Polderman',
				email: 'richard@informatieraadgevers.nl',
				password: springSecurityService.encodePassword('rmp001'),
				enabled: true)
		richard.id=java.util.UUID.randomUUID().toString()
		richard.save(failOnError: true)
		def marijke = User.findByUsername('marijke') ?: new User(
				username: 'marijke',
				firstName: 'Marijke',
				lastName: 'Salters',
				email: 'marijke.salters@memio.nl',
				password: springSecurityService.encodePassword('marijke'),
				enabled: true)
		marijke.id=java.util.UUID.randomUUID().toString()
		marijke.save(failOnError: true)
			
		if (!bas.authorities?.contains(userRole)) {
			UserRole.create(bas,userRole,true)
			UserRole.create(bas,adminRole,true)
			UserRole.create(bas,kccRole,true)
			UserRole.create(bas,afdelingRole,true)
		}
		
		if (!admin.authorities?.contains(adminRole)) {
			UserRole.create(admin,adminRole,true)
		}
		
		if (!richard.authorities?.contains(adminRole)) {
			UserRole.create(richard,adminRole,true)
			UserRole.create(richard,userRole,true)
			UserRole.create(bas,kccRole,true)
			UserRole.create(bas,afdelingRole,true)
		}
		if (!marijke.authorities?.contains(userRole)) {
			UserRole.create(marijke,userRole,true)
			UserRole.create(bas,kccRole,true)
			UserRole.create(bas,afdelingRole,true)
		}
	}
}
