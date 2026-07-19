package application;

import controller.NavigationsController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Einstiegspunkt der JavaFX-Anwendung.
 *
 * Die Klasse erzeugt das Hauptfenster und startet
 * die Navigation mit der Startseite.
 */
public class Hauptprogramm extends Application {

    /**
     * Wird von JavaFX beim Programmstart aufgerufen.
     *
     * @param hauptFenster gemeinsames Fenster der Anwendung
     */
    @Override
    public void start(Stage hauptFenster) {

        /*
         * Der NavigationsController verwaltet alle Seitenwechsel.
         */
        NavigationsController navigation =
                new NavigationsController(hauptFenster);

        /*
         * Beim Programmstart wird die Startseite angezeigt.
         */
        navigation.zeigeStartseite();
    }

    /**
     * Klassischer Einstiegspunkt des Programms.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
