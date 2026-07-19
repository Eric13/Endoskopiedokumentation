package controller;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Lager;
import view.NeueUntersuchung;
import view.Startseite;

/**
 * Steuert die Navigation zwischen den Ansichten.
 *
 * Während der gesamten Laufzeit werden nur ein Stage
 * und eine Scene verwendet. Beim Seitenwechsel wird
 * lediglich das Root-Element der Scene ausgetauscht.
 *
 * Dadurch bleibt das Fenster beim Wechsel zwischen
 * den Seiten maximiert.
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

    /**
     * Konstruktor.
     *
     * @param hauptFenster gemeinsames JavaFX-Fenster
     */
    public NavigationsController(Stage hauptFenster) {

        this.hauptFenster = hauptFenster;

        /*
         * Für die Erzeugung der Scene wird zunächst
         * eine Startseitenansicht verwendet.
         */
        Startseite vorlaeufigeStartseite =
                new Startseite();

        /*
         * Die Scene wird nur einmal erzeugt.
         */
        hauptScene = new Scene(
                vorlaeufigeStartseite.getAnsicht()
        );

        /*
         * Zentrale CSS-Datei laden.
         */
        ladeDesign();

        /*
         * Allgemeine Fenstereinstellungen.
         */
        hauptFenster.setTitle(
                "Endoskopie-Dokumentation"
        );

        /*
         * Verhindert ein zu kleines Fenster.
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
     * Stage und Scene bleiben erhalten.
     *
     * @param ansicht neue Benutzeroberfläche
     */
    private void zeigeAnsicht(Parent ansicht) {

        /*
         * Nur der Inhalt der Scene wird ausgetauscht.
         */
        hauptScene.setRoot(ansicht);

        /*
         * Maximierung nach dem Seitenwechsel absichern.
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
         * Navigation zur Lagerseite.
         */
        startseite
                .getLagerButton()
                .setOnAction(
                        ereignis -> zeigeLager()
                );

        /*
         * Navigation zur Seite "Neue Untersuchung".
         */
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

            /*
             * Unter Windows funktioniert das Maximieren
             * nach show() zuverlässiger.
             */
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
         * Vorläufige Bedienfunktion:
         * ausgewähltes Material in die Liste übernehmen.
         *
         * Die eigentliche Controller- und Datenbanklogik
         * wird später ergänzt.
         */
        neueUntersuchung
                .getMaterialHinzufuegenButton()
                .setOnAction(
                        ereignis -> {

                            String material =
                                    neueUntersuchung
                                            .getMaterialAuswahl()
                                            .getValue();

                            int menge =
                                    neueUntersuchung
                                            .getMengenAuswahl()
                                            .getValue();

                            if (material != null) {

                                neueUntersuchung
                                        .getMaterialListe()
                                        .getItems()
                                        .add(
                                                material
                                                        + " – Menge: "
                                                        + menge
                                        );

                                neueUntersuchung
                                        .getMaterialAuswahl()
                                        .setValue(null);

                                neueUntersuchung
                                        .getMengenAuswahl()
                                        .getValueFactory()
                                        .setValue(1);
                            }
                        }
                );

        /*
         * Markierten Materialeintrag entfernen.
         */
        neueUntersuchung
                .getMaterialEntfernenButton()
                .setOnAction(
                        ereignis -> {

                            String ausgewaehlt =
                                    neueUntersuchung
                                            .getMaterialListe()
                                            .getSelectionModel()
                                            .getSelectedItem();

                            if (ausgewaehlt != null) {

                                neueUntersuchung
                                        .getMaterialListe()
                                        .getItems()
                                        .remove(ausgewaehlt);
                            }
                        }
                );

        zeigeAnsicht(
                neueUntersuchung.getAnsicht()
        );
    }

    /**
     * Zeigt die Lagerübersicht.
     */
    public void zeigeLager() {

        Lager lager =
                new Lager();

        /*
         * Zurück zur Startseite.
         */
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
