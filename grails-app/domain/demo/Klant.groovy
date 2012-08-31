package demo

class Klant {
    Integer BSN
    String voorletters
    String tussenvoegsels
    String voornamen
    String achternaam
    Date   geboortedatum
    Geslacht geslacht
    String straat
    Integer huisnummer
    String huisnummerToevoeging
    String postcode
    String plaats
    String telefoonnummer
    String email

    String getAdres() {
        "$straat $huisnummer ${huisnummerToevoeging?huisnummerToevoeging:''}"
    }

    static transients = ["adres"]
    static hasMany = [zaken: Zaak]
    static constraints = {
        voorletters(nullable:true)
        achternaam()
        geboortedatum()
        geslacht(nullable:true)
        postcode()
        huisnummer()
        BSN()
        tussenvoegsels(nullable:true)
        voornamen(nullable:true)
        straat()
        huisnummerToevoeging(nullable:true)
        plaats()
        telefoonnummer(nullable:true)
        email(nullable:true, email:true)
    }
    
    String toString() {
        "$achternaam, $voorletters ${tussenvoegsels?tussenvoegsels:''}"
    }

}

enum Geslacht {Man, Vrouw}