package dao;

import database.Datenbank;
import model.Lagerartikel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Übernimmt die Datenbankzugriffe
 * für die Material- und Lagerverwaltung.
 */
public class MaterialDAO {

    /**
     * Lädt alle Materialien aus MariaDB.
     *
     * @return Liste aller Lagerartikel
     * @throws SQLException bei einem Datenbankfehler
     */
    public List<Lagerartikel> alleLaden()
            throws SQLException {

        /*
         * Ergebnisliste für die Lagerübersicht.
         */
        List<Lagerartikel> lagerartikel =
                new ArrayList<>();

        String sql = """
                SELECT
                    Material_ID,
                    Artikelnummer,
                    Bezeichnung,
                    Bestand,
                    Mindestbestand
                FROM Material
                ORDER BY Bezeichnung
                """;

        /*
         * Verbindung, Befehl und Ergebnis werden
         * automatisch geschlossen.
         */
        try (Connection verbindung =
                     Datenbank.holeVerbindung();

             PreparedStatement befehl =
                     verbindung.prepareStatement(sql);

             ResultSet ergebnis =
                     befehl.executeQuery()) {

            while (ergebnis.next()) {

                Lagerartikel artikel =
                        new Lagerartikel(
                                ergebnis.getInt(
                                        "Material_ID"
                                ),

                                ergebnis.getString(
                                        "Artikelnummer"
                                ),

                                ergebnis.getString(
                                        "Bezeichnung"
                                ),

                                ergebnis.getInt(
                                        "Bestand"
                                ),

                                ergebnis.getInt(
                                        "Mindestbestand"
                                )
                        );

                lagerartikel.add(artikel);
            }
        }

        System.out.println(
        "MaterialDAO: Geladene Materialien = "
                + lagerartikel.size()
);

        return lagerartikel;
    }

    /**
     * Erhöht den Bestand eines Materials.
     *
     * @param materialId interne Material-ID
     * @param menge hinzuzufügende Menge
     * @throws SQLException bei einem Datenbankfehler
     */
    public void bestandErhoehen(
            int materialId,
            int menge
    ) throws SQLException {

        String sql = """
                UPDATE Material
                SET Bestand = Bestand + ?
                WHERE Material_ID = ?
                """;

        try (Connection verbindung =
                     Datenbank.holeVerbindung();

             PreparedStatement befehl =
                     verbindung.prepareStatement(sql)) {

            befehl.setInt(1, menge);
            befehl.setInt(2, materialId);

            int geaenderteZeilen =
                    befehl.executeUpdate();

            if (geaenderteZeilen != 1) {

                throw new SQLException(
                        "Der Lagerbestand konnte nicht erhöht werden."
                );
            }
        }
    }

    /**
     * Verringert den Bestand eines Materials.
     *
     * Der Bestand darf dabei nicht negativ werden.
     *
     * @param materialId interne Material-ID
     * @param menge abzuziehende Menge
     * @throws SQLException bei einem Datenbankfehler
     */
    public void bestandVerringern(
            int materialId,
            int menge
    ) throws SQLException {

        String sql = """
                UPDATE Material
                SET Bestand = Bestand - ?
                WHERE Material_ID = ?
                  AND Bestand >= ?
                """;

        try (Connection verbindung =
                     Datenbank.holeVerbindung();

             PreparedStatement befehl =
                     verbindung.prepareStatement(sql)) {

            befehl.setInt(1, menge);
            befehl.setInt(2, materialId);
            befehl.setInt(3, menge);

            int geaenderteZeilen =
                    befehl.executeUpdate();

            if (geaenderteZeilen != 1) {

                throw new SQLException(
                        "Der Bestand ist für diese Verringerung nicht ausreichend."
                );
            }
        }
    }
}