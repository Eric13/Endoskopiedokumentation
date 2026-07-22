package model;

/**
 * Repräsentiert einen Materialartikel
 * innerhalb der Lagerübersicht.
 *
 * Die Klasse enthält die Werte,
 * die aus der Tabelle Material geladen
 * und in der JavaFX-Tabelle angezeigt werden.
 */
public class Lagerartikel {

    /*
     * Interne Datenbank-ID des Materials.
     */
    private final int materialId;

    /*
     * Eindeutige Artikelnummer.
     *
     * Beispiel:
     * M100001
     */
    private final String artikelnummer;

    /*
     * Bezeichnung des Materials.
     *
     * Beispiel:
     * Biopsiezange
     */
    private final String bezeichnung;

    /*
     * Aktuell verfügbarer Lagerbestand.
     */
    private final int bestand;

    /*
     * Definierter Mindestbestand.
     */
    private final int mindestbestand;

    /**
     * Erstellt einen Lagerartikel.
     *
     * @param materialId interne Datenbank-ID
     * @param artikelnummer eindeutige Artikelnummer
     * @param bezeichnung Materialbezeichnung
     * @param bestand aktueller Bestand
     * @param mindestbestand definierter Mindestbestand
     */
    public Lagerartikel(
            int materialId,
            String artikelnummer,
            String bezeichnung,
            int bestand,
            int mindestbestand
    ) {

        this.materialId = materialId;
        this.artikelnummer = artikelnummer;
        this.bezeichnung = bezeichnung;
        this.bestand = bestand;
        this.mindestbestand = mindestbestand;
    }

    /**
     * Gibt die interne Material-ID zurück.
     *
     * @return Material-ID
     */
    public int getMaterialId() {
        return materialId;
    }

    /**
     * Gibt die Artikelnummer zurück.
     *
     * @return Artikelnummer
     */
    public String getArtikelnummer() {
        return artikelnummer;
    }

    /**
     * Gibt die Materialbezeichnung zurück.
     *
     * @return Bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Gibt den aktuellen Bestand zurück.
     *
     * @return aktueller Lagerbestand
     */
    public int getBestand() {
        return bestand;
    }

    /**
     * Gibt den Mindestbestand zurück.
     *
     * @return Mindestbestand
     */
    public int getMindestbestand() {
        return mindestbestand;
    }

    /**
     * Ermittelt den Status des Materials.
     *
     * Die spätere Warnfunktion kann diesen
     * Wert für die Tabellenanzeige verwenden.
     *
     * @return Status des Lagerartikels
     */
    public String getStatus() {

        if (bestand <= mindestbestand) {
            return "Nachbestellen";
        }

        return "OK";
    }
}