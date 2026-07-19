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
 * Benutzeroberfläche der Startseite.
 *
 * Die Startseite zeigt:
 * - die Untersuchungsübersicht,
 * - den Button zur Lagerseite,
 * - den Button zur Erfassung einer neuen Untersuchung.
 *
 * Die Klasse enthält keine Datenbanklogik.
 */
public class Startseite {

    /*
     * Hauptlayout der Seite.
     */
    private final BorderPane hauptLayout;

    /*
     * Navigationsbuttons.
     */
    private final Button lagerButton;
    private final Button neueUntersuchungButton;

    /*
     * Tabelle der Untersuchungen.
     */
    private final TableView<Object> untersuchungsTabelle;

    /**
     * Konstruktor erstellt die vollständige Startseite.
     */
    public Startseite() {

        /*
         * Hauptlayout ohne feste Größe.
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
                new Label("Endoskopie-Dokumentation");

        ueberschrift
                .getStyleClass()
                .add("ueberschrift");

        /*
         * Untertitel.
         */
        Label untertitel =
                new Label(
                        "Übersicht der dokumentierten Untersuchungen"
                );

        untertitel
                .getStyleClass()
                .add("untertitel");

        /*
         * Titel und Untertitel vertikal darstellen.
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
         * Lagerbutton.
         */
        lagerButton =
                new Button("Lager");

        /*
         * Button für eine neue Untersuchung.
         */
        neueUntersuchungButton =
                new Button("Neue Untersuchung");

        neueUntersuchungButton
                .getStyleClass()
                .add("hauptbutton");

        /*
         * Kopfbereich zusammensetzen.
         */
        kopfbereich.getChildren().addAll(
                titelBereich,
                abstand,
                lagerButton,
                neueUntersuchungButton
        );

        /*
         * Untersuchungstabelle.
         */
        untersuchungsTabelle =
                new TableView<>();

        /*
         * Tabelle nutzt den verfügbaren Raum.
         */
        untersuchungsTabelle.setMaxWidth(
                Double.MAX_VALUE
        );

        untersuchungsTabelle.setMaxHeight(
                Double.MAX_VALUE
        );

        /*
         * Spalten verteilen sich auf die Tabellenbreite.
         */
        untersuchungsTabelle.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        /*
         * Anzeige bei leerer Tabelle.
         */
        untersuchungsTabelle.setPlaceholder(
                new Label(
                        "Noch keine Untersuchungen vorhanden."
                )
        );

        /*
         * Spalte: Datum.
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

        geburtsdatumSpalte.setPrefWidth(160);

        /*
         * Spalte: Untersuchungsart.
         */
        TableColumn<Object, String> untersuchungsartSpalte =
                new TableColumn<>("Untersuchungsart");

        untersuchungsartSpalte.setPrefWidth(190);

        /*
         * Spalte: Verbrauchsmaterial.
         */
        TableColumn<Object, String> materialSpalte =
                new TableColumn<>("Verbrauchsmaterial");

        materialSpalte.setPrefWidth(380);

        /*
         * Spalten hinzufügen.
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
         * Überschrift über der Tabelle.
         */
        Label tabellenTitel =
                new Label("Untersuchungsübersicht");

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
                        untersuchungsTabelle
                );

        inhaltsBereich.setPadding(
                new Insets(24)
        );

        inhaltsBereich.setFillWidth(true);

        /*
         * Tabelle nutzt den restlichen vertikalen Platz.
         */
        VBox.setVgrow(
                untersuchungsTabelle,
                Priority.ALWAYS
        );

        /*
         * Gesamtlayout zusammensetzen.
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
     * Gibt den Lagerbutton zurück.
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
