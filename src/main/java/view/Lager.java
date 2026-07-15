package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Benutzeroberfläche der Lagerverwaltung.
 *
 * Aufgabe:
 * - Übersicht aller Materialien
 * - Anzeige des Lagerbestandes
 * - Vorbereitung für Sprint 3
 *
 * Diese Klasse enthält keine Geschäftslogik.
 */
public class Lager {

    /*
     * Hauptlayout.
     */
    private BorderPane hauptLayout;

    /*
     * Zurück Button.
     */
    private Button zurueckButton;

    /*
     * Tabelle.
     */
    private TableView<Object> lagerTabelle;

    /**
     * Konstruktor.
     */
    public Lager() {

        erstelleOberflaeche();

    }

    /**
     * Erstellt die komplette Oberfläche.
     */
    private void erstelleOberflaeche() {

        //----------------------------------------
        // Hauptlayout
        //----------------------------------------

        hauptLayout = new BorderPane();

        hauptLayout.setPadding(
                new Insets(20)
        );

        //----------------------------------------
        // Überschrift
        //----------------------------------------

        Label ueberschrift =
                new Label("Lagerverwaltung");

        ueberschrift.setStyle(

                "-fx-font-size:26px;" +

                "-fx-font-weight:bold;"

        );

        //----------------------------------------
        // Abstand
        //----------------------------------------

        Region abstand =
                new Region();

        HBox.setHgrow(
                abstand,
                Priority.ALWAYS
        );

        //----------------------------------------
        // Zurück Button
        //----------------------------------------

        zurueckButton =
                new Button("Zurück");

        //----------------------------------------
        // Kopfbereich
        //----------------------------------------

        HBox kopfbereich =
                new HBox(

                        10,

                        ueberschrift,

                        abstand,

                        zurueckButton

                );

        kopfbereich.setAlignment(
                Pos.CENTER_LEFT
        );

        //----------------------------------------
        // Tabelle
        //----------------------------------------

        lagerTabelle =
                new TableView<>();

        lagerTabelle.setColumnResizePolicy(

                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS

        );

        //----------------------------------------
        // Spalten
        //----------------------------------------

        TableColumn<Object,String> artikelnummer =
                new TableColumn<>("Artikelnummer");

        TableColumn<Object,String> bezeichnung =
                new TableColumn<>("Bezeichnung");

        TableColumn<Object,Integer> bestand =
                new TableColumn<>("Bestand");

        TableColumn<Object,Integer> mindestbestand =
                new TableColumn<>("Mindestbestand");

        TableColumn<Object,String> status =
                new TableColumn<>("Status");

        //----------------------------------------
        // Spalten hinzufügen
        //----------------------------------------

        lagerTabelle.getColumns().addAll(

                artikelnummer,

                bezeichnung,

                bestand,

                mindestbestand,

                status

        );

        //----------------------------------------
        // Platzhalter
        //----------------------------------------

        lagerTabelle.setPlaceholder(

                new Label(
                        "Noch keine Lagerdaten vorhanden."
                )

        );

        //----------------------------------------
        // Layout zusammensetzen
        //----------------------------------------

        hauptLayout.setTop(
                kopfbereich
        );

        hauptLayout.setCenter(
                lagerTabelle
        );

        BorderPane.setMargin(

                lagerTabelle,

                new Insets(20,0,0,0)

        );

    }

    /**
     * Gibt die Oberfläche zurück.
     */
    public Parent getAnsicht() {

        return hauptLayout;

    }

    /**
     * Getter für den Zurück Button.
     */
    public Button getZurueckButton() {

        return zurueckButton;

    }

    /**
     * Getter für die Tabelle.
     */
    public TableView<Object> getLagerTabelle() {

        return lagerTabelle;

    }

}