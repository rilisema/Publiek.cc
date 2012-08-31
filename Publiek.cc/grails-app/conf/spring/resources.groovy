// Place your Spring DSL code here
beans = {
	//default language NL
	localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
		defaultLocale = new Locale("nl","NL")
		java.util.Locale.setDefault(defaultLocale)
	}
	
   pccSuccessSecurityEventListener(auth.PccSuccessSecurityEventListener)
   pccFailureSecurityEventListener(auth.PccFailureSecurityEventListener)
}
