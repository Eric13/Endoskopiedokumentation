package model;

/**
 * Beschreibt ein Material und die bei einer
 * Untersuchung verwendete Menge.
 */
public class Materialverbrauch {

    private final String bezeichnung;
    private final int menge;

    public Materialverbrauch(
            String bezeichnung,
            int menge
    ) {
        this.bezeichnung = bezeichnung;
        this.menge = menge;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getMenge() {
        return menge;
    }
}