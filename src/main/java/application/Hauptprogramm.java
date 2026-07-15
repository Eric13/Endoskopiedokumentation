package application;

import controller.NavigationsController;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Einstiegspunkt der JavaFX-Anwendung.
 *
 * Diese Klasse:
 * - startet JavaFX,
 * - erzeugt den NavigationsController,
 * - öffnet beim Programmstart die Startseite.
 */
public class Hauptprogramm extends Application {

    /**
     * Diese Methode wird automatisch von JavaFX aufgerufen.
     *
     * @param hauptFenster Hauptfenster der Anwendung
     */
    @Override
    public void start(Stage hauptFenster) {

        /*
         * NavigationsController erzeugen.
         * Er verwaltet den Wechsel zwischen:
         * - Startseite
         * - Neue Untersuchung
         * - Lager
         */
        NavigationsController navigationsController =
                new NavigationsController(hauptFenster);

        /*
         * Beim Start wird die Startseite geöffnet.
         */
        navigationsController.zeigeStartseite();
    }

    /**
     * Klassischer Programmeinstieg.
     *
     * launch() startet die JavaFX-Laufzeit
     * und ruft anschließend start() auf.
     */
    public static void main(String[] args) {
        launch(args);
    }
}