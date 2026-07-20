package dao;

import database.Datenbank;
import model.Materialverbrauch;
import model.Untersuchung;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import model.UntersuchungsEintrag;

import java.util.ArrayList;
import java.util.List;
/**
 * Speichert Patienten-, Untersuchungs-
 * und Materialdaten in MariaDB.
 */
public class UntersuchungsDAO {

    /**
     * Speichert eine vollständige Untersuchung.
     *
     * Alle Schritte laufen in einer Transaktion.
     * Bei einem Fehler werden sämtliche Änderungen
     * rückgängig gemacht.
     *
     * @param untersuchung zu speichernde Untersuchung
     * @throws SQLException bei einem Datenbankfehler
     */
    public void speichern(Untersuchung untersuchung)
            throws SQLException {

        try (Connection verbindung =
                     Datenbank.holeVerbindung()) {

            /*
             * Automatisches Speichern vorübergehend deaktivieren.
             */
            verbindung.setAutoCommit(false);

            try {
                int patientId =
                        patientErmittelnOderAnlegen(
                                verbindung,
                                untersuchung
                        );

                int untersuchungsartId =
                        untersuchungsartErmitteln(
                                verbindung,
                                untersuchung.getUntersuchungsart()
                        );

                int untersuchungId =
                        untersuchungAnlegen(
                                verbindung,
                                untersuchung,
                                patientId,
                                untersuchungsartId
                        );

                materialverbrauchSpeichern(
                        verbindung,
                        untersuchungId,
                        untersuchung
                );

                /*
                 * Erst nach allen erfolgreichen Schritten speichern.
                 */
                verbindung.commit();

            } catch (SQLException fehler) {

                /*
                 * Bei einem Fehler alles rückgängig machen.
                 */
                verbindung.rollback();
                throw fehler;

            } finally {
                verbindung.setAutoCommit(true);
            }
        }
    }

    /**
     * Sucht einen Patienten über die fachliche Patienten-ID.
     * Ist er nicht vorhanden, wird er neu angelegt.
     */
    private int patientErmittelnOderAnlegen(
            Connection verbindung,
            Untersuchung untersuchung
    ) throws SQLException {

        String suchenSql = """
                SELECT Patient_ID
                FROM Patient
                WHERE Patienten_ID = ?
                """;

        try (PreparedStatement befehl =
                     verbindung.prepareStatement(suchenSql)) {

            befehl.setString(
                    1,
                    untersuchung.getPatientenId()
            );

            try (ResultSet ergebnis =
                         befehl.executeQuery()) {

                if (ergebnis.next()) {
                    return ergebnis.getInt("Patient_ID");
                }
            }
        }

        String einfuegenSql = """
                INSERT INTO Patient (
                    Patienten_ID,
                    Vorname,
                    Nachname,
                    Geburtsdatum
                )
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement befehl =
                     verbindung.prepareStatement(
                             einfuegenSql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            befehl.setString(
                    1,
                    untersuchung.getPatientenId()
            );

            befehl.setString(
                    2,
                    untersuchung.getVorname()
            );

            befehl.setString(
                    3,
                    untersuchung.getNachname()
            );

            befehl.setDate(
                    4,
                    Date.valueOf(
                            untersuchung.getGeburtsdatum()
                    )
            );

            befehl.executeUpdate();

            try (ResultSet schluessel =
                         befehl.getGeneratedKeys()) {

                if (schluessel.next()) {
                    return schluessel.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Die Patient-ID konnte nicht ermittelt werden."
        );
    }

    /**
     * Ermittelt die interne ID einer Untersuchungsart.
     */
    private int untersuchungsartErmitteln(
            Connection verbindung,
            String bezeichnung
    ) throws SQLException {

        String sql = """
                SELECT Untersuchungsart_ID
                FROM Untersuchungsart
                WHERE Bezeichnung = ?
                """;

        try (PreparedStatement befehl =
                     verbindung.prepareStatement(sql)) {

            befehl.setString(1, bezeichnung);

            try (ResultSet ergebnis =
                         befehl.executeQuery()) {

                if (ergebnis.next()) {
                    return ergebnis.getInt(
                            "Untersuchungsart_ID"
                    );
                }
            }
        }

        throw new SQLException(
                "Untersuchungsart nicht gefunden: "
                        + bezeichnung
        );
    }

    /**
     * Legt die Untersuchung an und liefert
     * die erzeugte Untersuchung_ID zurück.
     */
    private int untersuchungAnlegen(
            Connection verbindung,
            Untersuchung untersuchung,
            int patientId,
            int untersuchungsartId
    ) throws SQLException {

        String sql = """
                INSERT INTO Untersuchung (
                    Patient_ID,
                    Untersuchungsart_ID,
                    Untersuchungsnummer,
                    Datum
                )
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement befehl =
                     verbindung.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            befehl.setInt(1, patientId);
            befehl.setInt(2, untersuchungsartId);

            befehl.setString(
                    3,
                    untersuchung.getUntersuchungsnummer()
            );

            befehl.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            untersuchung.getDatum()
                    )
            );

            befehl.executeUpdate();

            try (ResultSet schluessel =
                         befehl.getGeneratedKeys()) {

                if (schluessel.next()) {
                    return schluessel.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Die Untersuchungs-ID konnte nicht ermittelt werden."
        );
    }

    /**
     * Speichert sämtliche verwendeten Materialien.
     *
     * Der Lagerbestand wird hier noch nicht verändert.
     * Dies erfolgt erst in Sprint 3.
     */
    private void materialverbrauchSpeichern(
            Connection verbindung,
            int untersuchungId,
            Untersuchung untersuchung
    ) throws SQLException {

        String materialIdSql = """
                SELECT Material_ID
                FROM Material
                WHERE Bezeichnung = ?
                """;

        String verbrauchSql = """
                INSERT INTO Untersuchung_Material (
                    Untersuchung_ID,
                    Material_ID,
                    Menge
                )
                VALUES (?, ?, ?)
                """;

        for (Materialverbrauch material :
                untersuchung.getMaterialverbrauch()) {

            int materialId;

            try (PreparedStatement suchBefehl =
                         verbindung.prepareStatement(
                                 materialIdSql
                         )) {

                suchBefehl.setString(
                        1,
                        material.getBezeichnung()
                );

                try (ResultSet ergebnis =
                             suchBefehl.executeQuery()) {

                    if (!ergebnis.next()) {
                        throw new SQLException(
                                "Material nicht gefunden: "
                                        + material.getBezeichnung()
                        );
                    }

                    materialId =
                            ergebnis.getInt("Material_ID");
                }
            }

            try (PreparedStatement einfuegeBefehl =
                         verbindung.prepareStatement(
                                 verbrauchSql
                         )) {

                einfuegeBefehl.setInt(
                        1,
                        untersuchungId
                );

                einfuegeBefehl.setInt(
                        2,
                        materialId
                );

                einfuegeBefehl.setInt(
                        3,
                        material.getMenge()
                );

                einfuegeBefehl.executeUpdate();
            }
        }
    }
    /**
 * Lädt alle gespeicherten Untersuchungen
 * für die Tabelle auf der Startseite.
 *
 * Die Daten aus Patient, Untersuchung,
 * Untersuchungsart und Material werden
 * in einer gemeinsamen Abfrage zusammengeführt.
 *
 * @return Liste aller Untersuchungen
 * @throws SQLException bei einem Datenbankfehler
 */
public List<UntersuchungsEintrag> alleLaden()
        throws SQLException {

    /*
     * Ergebnisliste für die Startseite.
     */
    List<UntersuchungsEintrag> untersuchungen =
            new ArrayList<>();

    /*
     * Die Abfrage verbindet alle benötigten Tabellen.
     *
     * Mehrere verwendete Materialien werden durch
     * GROUP_CONCAT in einer Textzeile zusammengefasst.
     */
    String sql = """
            SELECT
                DATE_FORMAT(
                    u.Datum,
                    '%d.%m.%Y %H:%i'
                ) AS Formatiertes_Datum,

                u.Untersuchungsnummer,

                CONCAT(
                    p.Vorname,
                    ' ',
                    p.Nachname
                ) AS Patientenname,

                DATE_FORMAT(
                    p.Geburtsdatum,
                    '%d.%m.%Y'
                ) AS Formatiertes_Geburtsdatum,

                ua.Bezeichnung AS Untersuchungsart,

                COALESCE(
                    GROUP_CONCAT(
                        CONCAT(
                            m.Bezeichnung,
                            ' (',
                            um.Menge,
                            ')'
                        )
                        ORDER BY m.Bezeichnung
                        SEPARATOR ', '
                    ),
                    'Kein Material'
                ) AS Materialverbrauch

            FROM Untersuchung u

            INNER JOIN Patient p
                ON p.Patient_ID = u.Patient_ID

            INNER JOIN Untersuchungsart ua
                ON ua.Untersuchungsart_ID =
                   u.Untersuchungsart_ID

            LEFT JOIN Untersuchung_Material um
                ON um.Untersuchung_ID =
                   u.Untersuchung_ID

            LEFT JOIN Material m
                ON m.Material_ID =
                   um.Material_ID

            GROUP BY
                u.Untersuchung_ID,
                u.Datum,
                u.Untersuchungsnummer,
                p.Vorname,
                p.Nachname,
                p.Geburtsdatum,
                ua.Bezeichnung

            ORDER BY u.Datum DESC
            """;

    /*
     * Verbindung, SQL-Befehl und Ergebnis werden
     * automatisch geschlossen.
     */
    try (Connection verbindung =
                 Datenbank.holeVerbindung();

         PreparedStatement befehl =
                 verbindung.prepareStatement(sql);

         ResultSet ergebnis =
                 befehl.executeQuery()) {

        /*
         * Jede gefundene Untersuchung wird in
         * einen UntersuchungsEintrag umgewandelt.
         */
        while (ergebnis.next()) {

            UntersuchungsEintrag eintrag =
                    new UntersuchungsEintrag(
                            ergebnis.getString(
                                    "Formatiertes_Datum"
                            ),

                            ergebnis.getString(
                                    "Untersuchungsnummer"
                            ),

                            ergebnis.getString(
                                    "Patientenname"
                            ),

                            ergebnis.getString(
                                    "Formatiertes_Geburtsdatum"
                            ),

                            ergebnis.getString(
                                    "Untersuchungsart"
                            ),

                            ergebnis.getString(
                                    "Materialverbrauch"
                            )
                    );

            untersuchungen.add(eintrag);
        }
    }

    return untersuchungen;
}
}