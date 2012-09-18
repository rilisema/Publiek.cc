package pcc


class TerugbelAfspraak {
    String klantnaam
    String telefoonnummer
    String opmerkingen
    Boolean teruggebeld = false
	Boolean stuurMail = true
    Date terugbelDatum
    String terugbeller

    static constraints = {
        klantnaam(nullable:false, blank:false)
        telefoonnummer(nullable:false, blank:false, matches: /\d{2,4}[\s-]?\d{6,8}/)
        opmerkingen(nullable:false, blank:false, maxSize: 10000)
        terugbelDatum(nullable:false)
        terugbeller(nullable:false)
        teruggebeld(nullable:false)
		stuurMail(nullable:false)
    }

    String toString() {
        "$klantnaam -$terugbelDatum"
    }
}
