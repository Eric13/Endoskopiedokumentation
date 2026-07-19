package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Stellt die Verbindung zur MariaDB-Datenbank her.
 */
public class Datenbank {

    private static final String URL =
        "jdbc:mariadb://localhost:3306/endoskopie_material";

    private static final String BENUTZER =
            "root";

    private static final String PASSWORT =
            "endoskopie";

    /**
     * Baut eine Verbindung zur Datenbank auf.
     *
     * @return aktive Datenbankverbindung
     * @throws SQLException wenn keine Verbindung hergestellt werden kann
     */
    public static Connection holeVerbindung()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                BENUTZER,
                PASSWORT
        );
    }
}
