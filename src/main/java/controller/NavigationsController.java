package controller;

import dao.UntersuchungsDAO;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Materialverbrauch;
import model.Untersuchung;
import view.Lager;
import view.NeueUntersuchung;
import view.Startseite;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import dao.MaterialDAO;
import model.Lagerartikel;

/**
 * Steuert die Navigation zwischen den Ansichten
 * und verarbeitet die Benutzereingaben.
 *
 * Während der gesamten Laufzeit werden nur eine Stage
 * und eine Scene verwendet. Beim Seitenwechsel wird
 * lediglich das Root-Element der Scene ausgetauscht.
 */
public class NavigationsController {

    /*
     * Gemeinsames Hauptfenster.
     */
    private final Stage hauptFenster;

    /*
     * Gemeinsame Scene für alle Ansichten.
     */
    private final Scene hauptScene;

    /*
     * Datenbankzugriff für Untersuchungen.
     */
    private final UntersuchungsDAO untersuchungsDAO;

    /*
 * Datenbankzugriff für Materialien.
 */
     private final MaterialDAO materialDAO;

    /**
     * Konstruktor.
     *
     * @param hauptFenster gemeinsames JavaFX-Fenster
     */
    public NavigationsController(Stage hauptFenster) {

        this.hauptFenster = hauptFenster;
        this.untersuchungsDAO = new UntersuchungsDAO();
        this.materialDAO = new MaterialDAO();

        /*
         * Für die einmalige Erzeugung der Scene
         * wird zunächst eine Startseite verwendet.
         */
        Startseite vorlaeufigeStartseite =
                new Startseite();

        /*
         * Die Scene wird nur einmal erzeugt.
         */
        hauptScene = new Scene(
                vorlaeufigeStartseite.getAnsicht()
        );

        ladeDesign();

        hauptFenster.setTitle(
                "Endoskopie-Dokumentation"
        );

        /*
         * Mindestgröße des Fensters.
         */
        hauptFenster.setMinWidth(900);
        hauptFenster.setMinHeight(600);

        /*
         * Die gemeinsame Scene wird einmalig gesetzt.
         */
        hauptFenster.setScene(hauptScene);
    }

    /**
     * Lädt die zentrale CSS-Datei.
     */
    private void ladeDesign() {

        var cssDatei =
                getClass().getResource("/design.css");

        if (cssDatei != null) {

            hauptScene
                    .getStylesheets()
                    .add(cssDatei.toExternalForm());

        } else {

            System.out.println(
                    "Die Datei design.css wurde nicht gefunden."
            );
        }
    }

    /**
     * Ersetzt den Inhalt der bestehenden Scene.
     *
     * @param ansicht neue Benutzeroberfläche
     */
    private void zeigeAnsicht(Parent ansicht) {

        hauptScene.setRoot(ansicht);

        /*
         * Das bisher funktionierende Verhalten
         * zur Maximierung wird beibehalten.
         */
        Platform.runLater(
                () -> hauptFenster.setMaximized(true)
        );
    }

    /**
     * Zeigt die Startseite.
     */
   public void zeigeStartseite() {

    Startseite startseite =
            new Startseite();

    /*
     * Gespeicherte Untersuchungen
     * aus MariaDB laden.
     */
    ladeUntersuchungen(startseite);

    /*
     * Navigation zur Lagerseite.
     */
    startseite
            .getLagerButton()
            .setOnAction(
                    ereignis -> zeigeLager()
            );

        startseite
                .getNeueUntersuchungButton()
                .setOnAction(
                        ereignis ->
                                zeigeNeueUntersuchung()
                );

        zeigeAnsicht(
                startseite.getAnsicht()
        );

        /*
         * Das Fenster wird nur beim ersten Aufruf angezeigt.
         */
        if (!hauptFenster.isShowing()) {

            hauptFenster.show();

            Platform.runLater(
                    () -> hauptFenster.setMaximized(true)
            );
        }
    }

    /**
     * Zeigt die Eingabeseite für eine neue Untersuchung.
     */
    public void zeigeNeueUntersuchung() {

        NeueUntersuchung neueUntersuchung =
                new NeueUntersuchung();

        /*
         * Zurück zur Startseite.
         */
        neueUntersuchung
                .getZurueckButton()
                .setOnAction(
                        ereignis ->
                                zeigeStartseite()
                );

        /*
         * Ausgewähltes Material mit Menge
         * in die Materialliste übernehmen.
         */
        neueUntersuchung
                .getMaterialHinzufuegenButton()
                .setOnAction(
                        ereignis ->
                                materialHinzufuegen(
                                        neueUntersuchung
                                )
                );

        /*
         * Markierten Materialeintrag entfernen.
         */
        neueUntersuchung
                .getMaterialEntfernenButton()
                .setOnAction(
                        ereignis ->
                                materialEntfernen(
                                        neueUntersuchung
                                )
                );

        /*
         * Untersuchung in MariaDB speichern.
         */
        neueUntersuchung
                .getSpeichernButton()
                .setOnAction(
                        ereignis ->
                                untersuchungSpeichern(
                                        neueUntersuchung
                                )
                );

        zeigeAnsicht(
                neueUntersuchung.getAnsicht()
        );
    }

    /**
     * Fügt ein ausgewähltes Material zur Liste hinzu.
     */
    private void materialHinzufuegen(
            NeueUntersuchung ansicht
    ) {

        String material =
                ansicht
                        .getMaterialAuswahl()
                        .getValue();

        Integer menge =
                ansicht
                        .getMengenAuswahl()
                        .getValue();

        if (material == null) {

            zeigeWarnung(
                    "Keine Materialauswahl",
                    "Bitte wählen Sie ein Material aus."
            );

            return;
        }

        if (menge == null || menge < 1) {

            zeigeWarnung(
                    "Ungültige Menge",
                    "Die Materialmenge muss mindestens 1 betragen."
            );

            return;
        }

        ansicht
                .getMaterialListe()
                .getItems()
                .add(
                        material
                                + " – Menge: "
                                + menge
                );

        /*
         * Eingaben für die nächste Auswahl zurücksetzen.
         */
        ansicht
                .getMaterialAuswahl()
                .setValue(null);

        ansicht
                .getMengenAuswahl()
                .getValueFactory()
                .setValue(1);
    }

    /**
     * Entfernt den ausgewählten Materialeintrag.
     */
    private void materialEntfernen(
            NeueUntersuchung ansicht
    ) {

        String ausgewaehlt =
                ansicht
                        .getMaterialListe()
                        .getSelectionModel()
                        .getSelectedItem();

        if (ausgewaehlt == null) {

            zeigeWarnung(
                    "Keine Auswahl",
                    "Bitte wählen Sie einen Materialeintrag aus."
            );

            return;
        }

        ansicht
                .getMaterialListe()
                .getItems()
                .remove(ausgewaehlt);
    }

    /**
     * Prüft die Eingaben und speichert die Untersuchung.
     */
    private void untersuchungSpeichern(
            NeueUntersuchung ansicht
    ) {

        if (!eingabenGueltig(ansicht)) {
            return;
        }

        try {
            /*
             * Materialeinträge aus der ListView
             * in Modellobjekte umwandeln.
             */
            List<Materialverbrauch> materialien =
                    materialverbrauchErstellen(ansicht);

            /*
             * Eindeutige Untersuchungsnummer erzeugen.
             *
             * Beispiel:
             * U20260719201530123
             */
            String untersuchungsnummer =
                    erstelleUntersuchungsnummer();

            LocalDate untersuchungsdatum =
                    ansicht
                            .getDatumFeld()
                            .getValue();

            /*
             * DatePicker enthält nur ein Datum.
             * Als Uhrzeit wird die aktuelle Uhrzeit verwendet.
             */
            LocalDateTime datumMitUhrzeit =
                    LocalDateTime.of(
                            untersuchungsdatum,
                            LocalTime.now()
                    );

            Untersuchung untersuchung =
                    new Untersuchung(
                            ansicht
                                    .getPatientenIdFeld()
                                    .getText()
                                    .trim(),

                            ansicht
                                    .getVornameFeld()
                                    .getText()
                                    .trim(),

                            ansicht
                                    .getNachnameFeld()
                                    .getText()
                                    .trim(),

                            ansicht
                                    .getGeburtsdatumFeld()
                                    .getValue(),

                            untersuchungsnummer,

                            datumMitUhrzeit,

                            ansicht
                                    .getUntersuchungsartAuswahl()
                                    .getValue(),

                            materialien
                    );

            /*
             * Speicherung erfolgt als Transaktion im DAO.
             */
            untersuchungsDAO.speichern(
                    untersuchung
            );

            zeigeInformation(
                    "Speicherung erfolgreich",
                    "Die Untersuchung wurde erfolgreich gespeichert.\n"
                            + "Untersuchungsnummer: "
                            + untersuchungsnummer
            );

            /*
             * Nach erfolgreicher Speicherung
             * zurück zur Startseite wechseln.
             */
            zeigeStartseite();

        } catch (SQLException fehler) {

            zeigeFehler(
                    "Datenbankfehler",
                    "Die Untersuchung konnte nicht gespeichert werden.\n\n"
                            + fehler.getMessage()
            );

            fehler.printStackTrace();

        } catch (IllegalArgumentException fehler) {

            zeigeFehler(
                    "Ungültige Materialdaten",
                    fehler.getMessage()
            );
        }
    }

    /**
     * Prüft, ob alle Pflichtfelder ausgefüllt wurden.
     */
    private boolean eingabenGueltig(
            NeueUntersuchung ansicht
    ) {

        if (ansicht.getDatumFeld().getValue() == null) {

            zeigeWarnung(
                    "Fehlendes Untersuchungsdatum",
                    "Bitte geben Sie das Untersuchungsdatum ein."
            );

            return false;
        }

        if (istLeer(
                ansicht
                        .getPatientenIdFeld()
                        .getText()
        )) {

            zeigeWarnung(
                    "Fehlende Patienten-ID",
                    "Bitte geben Sie eine Patienten-ID ein."
            );

            return false;
        }

        if (istLeer(
                ansicht
                        .getVornameFeld()
                        .getText()
        )) {

            zeigeWarnung(
                    "Fehlender Vorname",
                    "Bitte geben Sie den Vornamen ein."
            );

            return false;
        }

        if (istLeer(
                ansicht
                        .getNachnameFeld()
                        .getText()
        )) {

            zeigeWarnung(
                    "Fehlender Nachname",
                    "Bitte geben Sie den Nachnamen ein."
            );

            return false;
        }

        if (ansicht
                .getGeburtsdatumFeld()
                .getValue() == null) {

            zeigeWarnung(
                    "Fehlendes Geburtsdatum",
                    "Bitte geben Sie das Geburtsdatum ein."
            );

            return false;
        }

        if (ansicht
                .getUntersuchungsartAuswahl()
                .getValue() == null) {

            zeigeWarnung(
                    "Fehlende Untersuchungsart",
                    "Bitte wählen Sie eine Untersuchungsart aus."
            );

            return false;
        }

        if (ansicht
                .getMaterialListe()
                .getItems()
                .isEmpty()) {

            zeigeWarnung(
                    "Kein Material erfasst",
                    "Bitte fügen Sie mindestens ein Verbrauchsmaterial hinzu."
            );

            return false;
        }

        return true;
    }

    /**
     * Wandelt die sichtbaren Listeneinträge in
     * Materialverbrauch-Objekte um.
     */
    private List<Materialverbrauch> materialverbrauchErstellen(
            NeueUntersuchung ansicht
    ) {

        List<Materialverbrauch> materialien =
                new ArrayList<>();

        String trennzeichen =
                " – Menge: ";

        for (String eintrag :
                ansicht
                        .getMaterialListe()
                        .getItems()) {

            int trennPosition =
                    eintrag.lastIndexOf(trennzeichen);

            if (trennPosition < 0) {

                throw new IllegalArgumentException(
                        "Der Materialeintrag hat ein ungültiges Format: "
                                + eintrag
                );
            }

            String bezeichnung =
                    eintrag
                            .substring(
                                    0,
                                    trennPosition
                            )
                            .trim();

            String mengeAlsText =
                    eintrag
                            .substring(
                                    trennPosition
                                            + trennzeichen.length()
                            )
                            .trim();

            try {
                int menge =
                        Integer.parseInt(
                                mengeAlsText
                        );

                materialien.add(
                        new Materialverbrauch(
                                bezeichnung,
                                menge
                        )
                );

            } catch (NumberFormatException fehler) {

                throw new IllegalArgumentException(
                        "Die Menge des Materials ist ungültig: "
                                + eintrag
                );
            }
        }

        return materialien;
    }

    /**
     * Erzeugt eine eindeutige Untersuchungsnummer.
     */
    private String erstelleUntersuchungsnummer() {

        DateTimeFormatter format =
                DateTimeFormatter.ofPattern(
                        "yyyyMMddHHmmssSSS"
                );

        return "U"
                + LocalDateTime
                .now()
                .format(format);
    }

    /**
     * Prüft einen Text auf leeren Inhalt.
     */
    private boolean istLeer(String text) {

        return text == null
                || text.trim().isEmpty();
    }

    /**
     * Zeigt eine Warnmeldung.
     */
    private void zeigeWarnung(
            String titel,
            String nachricht
    ) {

        Alert meldung =
                new Alert(
                        Alert.AlertType.WARNING
                );

        meldung.setTitle(titel);
        meldung.setHeaderText(null);
        meldung.setContentText(nachricht);
        meldung.initOwner(hauptFenster);
        meldung.showAndWait();
    }

    /**
     * Zeigt eine Erfolgsmeldung.
     */
    private void zeigeInformation(
            String titel,
            String nachricht
    ) {

        Alert meldung =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        meldung.setTitle(titel);
        meldung.setHeaderText(null);
        meldung.setContentText(nachricht);
        meldung.initOwner(hauptFenster);
        meldung.showAndWait();
    }

    /**
     * Zeigt eine Fehlermeldung.
     */
    private void zeigeFehler(
            String titel,
            String nachricht
    ) {

        Alert meldung =
                new Alert(
                        Alert.AlertType.ERROR
                );

        meldung.setTitle(titel);
        meldung.setHeaderText(null);
        meldung.setContentText(nachricht);
        meldung.initOwner(hauptFenster);
        meldung.showAndWait();
    }

    /**
 * Lädt alle gespeicherten Untersuchungen
 * aus MariaDB und zeigt sie in der Tabelle
 * auf der Startseite an.
 *
 * @param startseite aktuelle Startseitenansicht
 */
private void ladeUntersuchungen(
        Startseite startseite
) {

    try {

        /*
         * Daten über den DAO laden und
         * vollständig in die Tabelle übernehmen.
         */
        startseite
                .getUntersuchungsTabelle()
                .getItems()
                .setAll(
                        untersuchungsDAO.alleLaden()
                );

    } catch (SQLException fehler) {

        /*
         * Fehlermeldung anzeigen,
         * wenn die Daten nicht geladen werden können.
         */
        zeigeFehler(
                "Fehler beim Laden",
                "Die Untersuchungen konnten nicht geladen werden.\n\n"
                        + fehler.getMessage()
        );

        fehler.printStackTrace();
    }
}

/**
 * Lädt alle Materialien aus MariaDB
 * in die Lagerübersicht.
 *
 * @param lager aktuelle Lageransicht
 */
private void ladeMaterialien(
        Lager lager
) {

    try {

        lager
                .getLagerTabelle()
                .getItems()
                .setAll(
                        materialDAO.alleLaden()
                );

    } catch (SQLException fehler) {

        zeigeFehler(
                "Fehler beim Laden",
                "Die Lagerdaten konnten nicht geladen werden.\n\n"
                        + fehler.getMessage()
        );

        fehler.printStackTrace();
    }
}

    /**
     * Zeigt die Lagerübersicht.
     */
    public void zeigeLager() {

        Lager lager =
                new Lager();

                /*
 * Materialdaten aus MariaDB laden.
 */
ladeMaterialien(lager);

/*
 * Lagerbestand erhöhen.
 */
lager
        .getBestandErhoehenButton()
        .setOnAction(ereignis -> {

            Lagerartikel artikel =
                    lager
                            .getLagerTabelle()
                            .getSelectionModel()
                            .getSelectedItem();

            if (artikel == null) {

                zeigeFehler(
                        "Keine Auswahl",
                        "Bitte zuerst einen Materialartikel auswählen."
                );

                return;
            }

            int menge =
                    lager
                            .getMengenSpinner()
                            .getValue();

            try {

                materialDAO.bestandErhoehen(
                        artikel.getMaterialId(),
                        menge
                );

                /*
                 * Tabelle neu laden.
                 */
                ladeMaterialien(lager);

            } catch (SQLException fehler) {

                zeigeFehler(
                        "Fehler",
                        fehler.getMessage()
                );

                fehler.printStackTrace();
            }

        });

        /*
 * Lagerbestand verringern.
 */
lager
        .getBestandVerringernButton()
        .setOnAction(ereignis -> {

            Lagerartikel artikel =
                    lager
                            .getLagerTabelle()
                            .getSelectionModel()
                            .getSelectedItem();

            if (artikel == null) {

                zeigeFehler(
                        "Keine Auswahl",
                        "Bitte zuerst einen Materialartikel auswählen."
                );

                return;
            }

            int menge =
                    lager
                            .getMengenSpinner()
                            .getValue();

            try {

                materialDAO.bestandVerringern(
                        artikel.getMaterialId(),
                        menge
                );

                /*
                 * Tabelle neu laden.
                 */
                ladeMaterialien(lager);

            } catch (SQLException fehler) {

                zeigeFehler(
                        "Fehler",
                        fehler.getMessage()
                );

                fehler.printStackTrace();
            }

        });
        lager
                .getZurueckButton()
                .setOnAction(
                        ereignis ->
                                zeigeStartseite()
                );

        zeigeAnsicht(
                lager.getAnsicht()
        );
    }
}