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
import javafx.scene.layout.VBox;

/**
 * Benutzeroberfläche der Lagerübersicht.
 *
 * Sprint 2:
 * - responsive Oberfläche,
 * - Tabellenstruktur,
 * - Navigation.
 *
 * Sprint 3:
 * - Bestandsberechnung,
 * - Restbestand,
 * - Warnfunktion.
 */
public class Lager {

    /*
     * Hauptlayout.
     */
    private final BorderPane hauptLayout;

    /*
     * Zurück-Button.
     */
    private final Button zurueckButton;

    /*
     * Lagertabelle.
     */
    private final TableView<Object> lagerTabelle;

    /**
     * Konstruktor erstellt die vollständige Lageransicht.
     */
    public Lager() {

        /*
         * Hauptlayout.
         */
        hauptLayout = new BorderPane();

        /*
         * Kopfbereich.
         */
        HBox kopfbereich = new HBox(12);
        kopfbereich.setAlignment(Pos.CENTER_LEFT);
        kopfbereich.getStyleClass().add("kopfbereich");

        /*
         * Überschrift.
         */
        Label ueberschrift =
                new Label("Lagerverwaltung");

        ueberschrift
                .getStyleClass()
                .add("ueberschrift");

        /*
         * Untertitel.
         */
        Label untertitel =
                new Label(
                        "Übersicht der Verbrauchsmaterialien und Lagerbestände"
                );

        untertitel
                .getStyleClass()
                .add("untertitel");

        /*
         * Titelbereich.
         */
        VBox titelBereich =
                new VBox(
                        3,
                        ueberschrift,
                        untertitel
                );

        /*
         * Flexibler Abstand.
         */
        Region abstand = new Region();

        HBox.setHgrow(
                abstand,
                Priority.ALWAYS
        );

        /*
         * Zurück-Button.
         */
        zurueckButton =
                new Button("Zurück");

        kopfbereich.getChildren().addAll(
                titelBereich,
                abstand,
                zurueckButton
        );

        /*
         * Lagertabelle.
         */
        lagerTabelle =
                new TableView<>();

        lagerTabelle.setMaxWidth(
                Double.MAX_VALUE
        );

        lagerTabelle.setMaxHeight(
                Double.MAX_VALUE
        );

        lagerTabelle.setPlaceholder(
                new Label(
                        "Noch keine Lagerdaten vorhanden."
                )
        );

        /*
         * Spalten passen sich an die Fensterbreite an.
         */
        lagerTabelle.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        /*
         * Spalte: Artikelnummer.
         */
        TableColumn<Object, String> artikelnummerSpalte =
                new TableColumn<>("Artikelnummer");

        artikelnummerSpalte.setPrefWidth(180);

        /*
         * Spalte: Bezeichnung.
         */
        TableColumn<Object, String> bezeichnungSpalte =
                new TableColumn<>("Bezeichnung");

        bezeichnungSpalte.setPrefWidth(350);

        /*
         * Spalte: aktueller Bestand.
         */
        TableColumn<Object, Number> bestandSpalte =
                new TableColumn<>("Bestand");

        bestandSpalte.setPrefWidth(150);

        /*
         * Spalte: Mindestbestand.
         */
        TableColumn<Object, Number> mindestbestandSpalte =
                new TableColumn<>("Mindestbestand");

        mindestbestandSpalte.setPrefWidth(180);

        /*
         * Spalte: Status.
         *
         * Wird später für "OK" beziehungsweise
         * "Nachbestellen" verwendet.
         */
        TableColumn<Object, String> statusSpalte =
                new TableColumn<>("Status");

        statusSpalte.setPrefWidth(200);

        /*
         * Spalten hinzufügen.
         */
        lagerTabelle.getColumns().addAll(
                artikelnummerSpalte,
                bezeichnungSpalte,
                bestandSpalte,
                mindestbestandSpalte,
                statusSpalte
        );

        /*
         * Abschnittsüberschrift.
         */
        Label tabellenTitel =
                new Label("Materialbestand");

        tabellenTitel
                .getStyleClass()
                .add("abschnittsueberschrift");

        /*
         * Inhaltsbereich.
         */
        VBox inhaltsBereich =
                new VBox(
                        15,
                        tabellenTitel,
                        lagerTabelle
                );

        inhaltsBereich.setPadding(
                new Insets(24)
        );

        inhaltsBereich.setFillWidth(true);

        /*
         * Tabelle füllt den übrigen Bildschirmbereich.
         */
        VBox.setVgrow(
                lagerTabelle,
                Priority.ALWAYS
        );

        /*
         * Layout zusammensetzen.
         */
        hauptLayout.setTop(kopfbereich);
        hauptLayout.setCenter(inhaltsBereich);
    }

    /**
     * Gibt die vollständige Ansicht zurück.
     */
    public Parent getAnsicht() {
        return hauptLayout;
    }

    /**
     * Gibt den Zurück-Button zurück.
     */
    public Button getZurueckButton() {
        return zurueckButton;
    }

    /**
     * Gibt die Lagertabelle zurück.
     */
    public TableView<Object> getLagerTabelle() {
        return lagerTabelle;
    }
}

