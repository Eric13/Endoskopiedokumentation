package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Enthält die Daten einer vollständigen Untersuchung.
 */
public class Untersuchung {

    private final String patientenId;
    private final String vorname;
    private final String nachname;
    private final LocalDate geburtsdatum;

    private final String untersuchungsnummer;
    private final LocalDateTime datum;
    private final String untersuchungsart;

    private final List<Materialverbrauch> materialverbrauch;

    public Untersuchung(
            String patientenId,
            String vorname,
            String nachname,
            LocalDate geburtsdatum,
            String untersuchungsnummer,
            LocalDateTime datum,
            String untersuchungsart,
            List<Materialverbrauch> materialverbrauch
    ) {
        this.patientenId = patientenId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.untersuchungsnummer = untersuchungsnummer;
        this.datum = datum;
        this.untersuchungsart = untersuchungsart;
        this.materialverbrauch = new ArrayList<>(materialverbrauch);
    }

    public String getPatientenId() {
        return patientenId;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getUntersuchungsnummer() {
        return untersuchungsnummer;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public String getUntersuchungsart() {
        return untersuchungsart;
    }

    public List<Materialverbrauch> getMaterialverbrauch() {
        return new ArrayList<>(materialverbrauch);
    }
}