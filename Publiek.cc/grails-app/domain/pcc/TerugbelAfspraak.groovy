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
        telefoonnummer(nullable:false, blank:false)
        opmerkingen(nullable:false, blank:false, maxSize: 10000)
        terugbelDatum(nullable:false)
        terugbeller(nullabe:false)
        teruggebeld(nullable:false)
		stuurMail(nullable:false)
    }

    String toString() {
        "$klantnaam -$terugbelDatum"
    }
}
