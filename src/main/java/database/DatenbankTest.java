package database;

import java.sql.Connection;

/**
 * Testet die Verbindung zur MariaDB-Datenbank.
 */
public class DatenbankTest {

    public static void main(String[] args) {

        try (Connection verbindung =
                     Datenbank.holeVerbindung()) {

            System.out.println(
                    "Verbindung zur MariaDB erfolgreich hergestellt."
            );

        } catch (Exception e) {

            System.out.println(
                    "Fehler bei der Datenbankverbindung."
            );

            e.printStackTrace();
        }
    }
}