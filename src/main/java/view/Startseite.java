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
 * Moderne Benutzeroberfläche der Startseite.
 *
 * Aufgaben:
 * - Übersicht aller Untersuchungen
 * - Navigation zur Lagerübersicht
 * - Navigation zur neuen Untersuchung
 *
 * Die View enthält keine Geschäftslogik.
 * Die Navigation wird durch den Controller gesteuert.
 */
public class Startseite {

    /*
     * Hauptlayout der Seite.
     */
    private final BorderPane hauptLayout;

    /*
     * Buttons für die Navigation.
     */
    private final Button lagerButton;
    private final Button neueUntersuchungButton;

    /*
     * Tabelle zur Anzeige der Untersuchungen.
     */
    private final TableView<Object> untersuchungsTabelle;

    /**
     * Konstruktor erstellt die komplette Oberfläche.
     */
    public Startseite() {

        /*
         * Hauptlayout anlegen.
         */
        hauptLayout = new BorderPane();
        hauptLayout.setPadding(new Insets(0));

        /*
         * Oberen Kopfbereich erstellen.
         */
        HBox kopfbereich = new HBox(12);
        kopfbereich.setAlignment(Pos.CENTER_LEFT);
        kopfbereich.getStyleClass().add("kopfbereich");

        /*
         * Hauptüberschrift.
         */
        Label ueberschrift =
                new Label("Endoskopie-Dokumentation");

        ueberschrift
                .getStyleClass()
                .add("ueberschrift");

        /*
         * Untertitel zur besseren Orientierung.
         */
        Label untertitel =
                new Label(
                        "Übersicht der dokumentierten Untersuchungen"
                );

        untertitel.setStyle(
                "-fx-text-fill: #6b7b8c;" +
                "-fx-font-size: 13px;"
        );

        /*
         * Titel und Untertitel vertikal anordnen.
         */
        VBox titelBereich =
                new VBox(
                        3,
                        ueberschrift,
                        untertitel
                );

        /*
         * Flexibler Abstand verschiebt die Buttons nach rechts.
         */
        Region abstand = new Region();
        HBox.setHgrow(
                abstand,
                Priority.ALWAYS
        );

        /*
         * Button zur Lagerübersicht.
         */
        lagerButton =
                new Button("Lager");

        lagerButton.setPrefWidth(120);

        /*
         * Button für eine neue Untersuchung.
         */
        neueUntersuchungButton =
                new Button("Neue Untersuchung");

        neueUntersuchungButton.setPrefWidth(190);

        /*
         * Blauer Hauptbutton gemäß CSS.
         */
        neueUntersuchungButton
                .getStyleClass()
                .add("hauptbutton");

        /*
         * Alle Elemente in den Kopfbereich einsetzen.
         */
        kopfbereich.getChildren().addAll(
                titelBereich,
                abstand,
                lagerButton,
                neueUntersuchungButton
        );

        /*
         * Tabelle anlegen.
         */
        untersuchungsTabelle =
                new TableView<>();

        untersuchungsTabelle.setPlaceholder(
                new Label(
                        "Noch keine Untersuchungen vorhanden."
                )
        );

        /*
         * Spalten passen sich automatisch an.
         */
        untersuchungsTabelle.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        /*
         * Spalte: Untersuchungsdatum.
         */
        TableColumn<Object, String> datumSpalte =
                new TableColumn<>("Datum");

        datumSpalte.setPrefWidth(120);

        /*
         * Spalte: Untersuchungs-ID.
         */
        TableColumn<Object, String> idSpalte =
                new TableColumn<>("ID");

        idSpalte.setPrefWidth(120);

        /*
         * Spalte: Patientenname.
         */
        TableColumn<Object, String> nameSpalte =
                new TableColumn<>("Name");

        nameSpalte.setPrefWidth(240);

        /*
         * Spalte: Geburtsdatum.
         */
        TableColumn<Object, String> geburtsdatumSpalte =
                new TableColumn<>("Geburtsdatum");

        geburtsdatumSpalte.setPrefWidth(150);

        /*
         * Spalte: Untersuchungsart.
         */
        TableColumn<Object, String> untersuchungsartSpalte =
                new TableColumn<>("Untersuchungsart");

        untersuchungsartSpalte.setPrefWidth(180);

        /*
         * Spalte: Verbrauchsmaterial.
         */
        TableColumn<Object, String> materialSpalte =
                new TableColumn<>("Verbrauchsmaterial");

        materialSpalte.setPrefWidth(360);

        /*
         * Spalten zur Tabelle hinzufügen.
         */
        untersuchungsTabelle.getColumns().addAll(
                datumSpalte,
                idSpalte,
                nameSpalte,
                geburtsdatumSpalte,
                untersuchungsartSpalte,
                materialSpalte
        );

        /*
         * Inhaltsbereich mit weißer Karte.
         */
        VBox inhaltsBereich =
                new VBox(15);

        inhaltsBereich.setPadding(
                new Insets(24)
        );

        /*
         * Kleine Abschnittsüberschrift.
         */
        Label tabellenTitel =
                new Label(
                        "Untersuchungsübersicht"
                );

        tabellenTitel.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #173b63;"
        );

        /*
         * Tabelle soll den verfügbaren Platz nutzen.
         */
        VBox.setVgrow(
                untersuchungsTabelle,
                Priority.ALWAYS
        );

        /*
         * Inhalt zusammensetzen.
         */
        inhaltsBereich.getChildren().addAll(
                tabellenTitel,
                untersuchungsTabelle
        );

        /*
         * Kopfbereich und Inhalt in das Hauptlayout einsetzen.
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
     * Gibt den Lager-Button zurück.
     */
    public Button getLagerButton() {
        return lagerButton;
    }

    /**
     * Gibt den Button für eine neue Untersuchung zurück.
     */
    public Button getNeueUntersuchungButton() {
        return neueUntersuchungButton;
    }

    /**
     * Gibt die Untersuchungstabelle zurück.
     */
    public TableView<Object> getUntersuchungsTabelle() {
        return untersuchungsTabelle;
    }
}