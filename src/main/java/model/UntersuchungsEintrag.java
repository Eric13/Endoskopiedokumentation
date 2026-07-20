package model;

/**
 * Stellt eine gespeicherte Untersuchung
 * als einzelne Zeile in der Tabelle
 * auf der Startseite dar.
 *
 * Diese Klasse enthält ausschließlich
 * die Daten, die in der Untersuchungsübersicht
 * angezeigt werden.
 *
 * Sie dient damit als Anzeigemodell
 * zwischen Datenbank und Benutzeroberfläche.
 */
public class UntersuchungsEintrag {

    /*
     * Datum und Uhrzeit der Untersuchung.
     *
     * Beispiel:
     * 19.07.2026 20:15
     */
    private final String datum;

    /*
     * Eindeutige Untersuchungsnummer.
     *
     * Beispiel:
     * U20260719201530123
     */
    private final String untersuchungsnummer;

    /*
     * Vollständiger Name des Patienten.
     *
     * Beispiel:
     * Max Mustermann
     */
    private final String patientenname;

    /*
     * Formatiertes Geburtsdatum.
     *
     * Beispiel:
     * 12.07.1980
     */
    private final String geburtsdatum;

    /*
     * Bezeichnung der Untersuchungsart.
     *
     * Beispiel:
     * Gastroskopie
     */
    private final String untersuchungsart;

    /*
     * Zusammengefasste Darstellung
     * der verwendeten Materialien.
     *
     * Beispiel:
     * Biopsiezange (1), Clip (2)
     */
    private final String materialverbrauch;

    /**
     * Erstellt einen neuen Eintrag
     * für die Untersuchungsübersicht.
     *
     * @param datum formatiertes Untersuchungsdatum
     * @param untersuchungsnummer eindeutige Untersuchungsnummer
     * @param patientenname vollständiger Patientenname
     * @param geburtsdatum formatiertes Geburtsdatum
     * @param untersuchungsart Bezeichnung der Untersuchungsart
     * @param materialverbrauch verwendete Materialien mit Menge
     */
    public UntersuchungsEintrag(
            String datum,
            String untersuchungsnummer,
            String patientenname,
            String geburtsdatum,
            String untersuchungsart,
            String materialverbrauch
    ) {

        this.datum = datum;
        this.untersuchungsnummer = untersuchungsnummer;
        this.patientenname = patientenname;
        this.geburtsdatum = geburtsdatum;
        this.untersuchungsart = untersuchungsart;
        this.materialverbrauch = materialverbrauch;
    }

    /**
     * Gibt das Untersuchungsdatum zurück.
     *
     * @return formatiertes Datum mit Uhrzeit
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Gibt die Untersuchungsnummer zurück.
     *
     * @return eindeutige Untersuchungsnummer
     */
    public String getUntersuchungsnummer() {
        return untersuchungsnummer;
    }

    /**
     * Gibt den vollständigen Patientennamen zurück.
     *
     * @return Vorname und Nachname
     */
    public String getPatientenname() {
        return patientenname;
    }

    /**
     * Gibt das Geburtsdatum zurück.
     *
     * @return formatiertes Geburtsdatum
     */
    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    /**
     * Gibt die Untersuchungsart zurück.
     *
     * @return Bezeichnung der Untersuchungsart
     */
    public String getUntersuchungsart() {
        return untersuchungsart;
    }

    /**
     * Gibt die verwendeten Materialien zurück.
     *
     * @return zusammengefasster Materialverbrauch
     */
    public String getMaterialverbrauch() {
        return materialverbrauch;
    }
}