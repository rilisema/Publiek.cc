package demo

class Zaak {
    String omschrijving
    String zaakType
    Date ontvangstdatum
    Date datumAfgehandeld
    Integer verzoeknummer
    ZaakStatus status
    Klant klant
    auth.User behandelaar
 
    static constraints = {
        omschrijving()
        zaakType(nullable:true)
        ontvangstdatum()
        datumAfgehandeld(nullable:true)
        verzoeknummer()
        status()
        klant()
        behandelaar(nullable:true)
    }

    String toString() {
        "$omschrijving"
    }
}

enum ZaakStatus { Ontvangen, InBehandeling, Afgehandeld}
