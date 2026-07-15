package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Lager;
import view.NeueUntersuchung;
import view.Startseite;

/**
 * Steuert die Navigation zwischen den drei Ansichten.
 *
 * Es wird immer dasselbe Hauptfenster verwendet.
 * Beim Seitenwechsel wird nur die Scene ausgetauscht.
 */
public class NavigationsController {

    /*
     * Ein einziges Hauptfenster für die komplette Anwendung.
     */
    private final Stage hauptFenster;

    /**
     * Konstruktor.
     *
     * @param hauptFenster Hauptfenster der JavaFX-Anwendung
     */
    public NavigationsController(Stage hauptFenster) {

        this.hauptFenster = hauptFenster;

        /*
         * Gemeinsame Einstellungen für alle Seiten.
         */
        this.hauptFenster.setTitle(
                "Endoskopie-Dokumentation"
        );

        /*
         * Fenster darf nicht kleiner als diese Werte werden.
         */
        this.hauptFenster.setMinWidth(1000);
        this.hauptFenster.setMinHeight(700);

        /*
         * Anwendung immer maximiert öffnen.
         */
        this.hauptFenster.setMaximized(true);
    }

    /**
     * Lädt das gemeinsame CSS-Design.
     */
    private void ladeDesign(Scene scene) {

        var cssDatei =
                getClass().getResource("/design.css");

        if (cssDatei != null) {

            scene.getStylesheets().add(
                    cssDatei.toExternalForm()
            );

        } else {

            System.out.println(
                    "Die Datei design.css wurde nicht gefunden."
            );
        }
    }

    /**
     * Zeigt eine Scene im Hauptfenster an.
     *
     * Diese Hilfsmethode stellt sicher,
     * dass jede Seite maximiert angezeigt wird.
     */
    private void zeigeScene(Scene scene) {

        ladeDesign(scene);

        /*
         * Neue Scene in dasselbe Fenster einsetzen.
         */
        hauptFenster.setScene(scene);

        /*
         * Fenster anzeigen.
         */
        hauptFenster.show();

        /*
         * Nach jedem Seitenwechsel erneut maximieren.
         */
        hauptFenster.setMaximized(true);
    }

    /**
     * Öffnet die Startseite.
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
         * Navigation zur neuen Untersuchung.
         */
        startseite
                .getNeueUntersuchungButton()
                .setOnAction(
                        ereignis ->
                                zeigeNeueUntersuchung()
                );

        /*
         * Keine feste Fenstergröße angeben.
         */
        Scene scene =
                new Scene(
                        startseite.getAnsicht()
                );

        zeigeScene(scene);
    }

    /**
     * Öffnet die Seite "Neue Untersuchung".
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
         * Keine feste Fenstergröße verwenden.
         */
        Scene scene =
                new Scene(
                        neueUntersuchung.getAnsicht()
                );

        zeigeScene(scene);
    }

    /**
     * Öffnet die Lagerseite.
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

        /*
         * Keine feste Fenstergröße verwenden.
         */
        Scene scene =
                new Scene(
                        lager.getAnsicht()
                );

        zeigeScene(scene);
    }
}