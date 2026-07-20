package view;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import model.UntersuchungsEintrag;

/**
 * Benutzeroberfläche der Startseite.
 *
 * Die Startseite zeigt:
 * - alle gespeicherten Untersuchungen,
 * - den Button zur Lagerverwaltung,
 * - den Button zum Erfassen einer neuen Untersuchung.
 *
 * Die Klasse enthält ausschließlich die Benutzeroberfläche.
 * Das Laden der Daten aus MariaDB erfolgt im Controller.
 */
public class Startseite {

    /* Hauptlayout der Seite. */
    private final BorderPane hauptLayout;

    /* Navigationsbuttons. */
    private final Button lagerButton;
    private final Button neueUntersuchungButton;

    /* Tabelle mit den gespeicherten Untersuchungen. */
    private final TableView<UntersuchungsEintrag> untersuchungsTabelle;

    /** Erstellt die vollständige Startseite. */
    public Startseite() {

        /* Hauptlayout ohne feste Größe. */
        hauptLayout = new BorderPane();

        /* Kopfbereich. */
        HBox kopfbereich = new HBox(12);
        kopfbereich.setAlignment(Pos.CENTER_LEFT);
        kopfbereich.getStyleClass().add("kopfbereich");

        /* Hauptüberschrift. */
        Label ueberschrift = new Label("Endoskopie-Dokumentation");
        ueberschrift.getStyleClass().add("ueberschrift");

        /* Untertitel. */
        Label untertitel = new Label(
                "Übersicht der dokumentierten Untersuchungen"
        );
        untertitel.getStyleClass().add("untertitel");

        /* Überschrift und Untertitel untereinander anordnen. */
        VBox titelBereich = new VBox(3, ueberschrift, untertitel);

        /* Flexibler Abstand verschiebt die Buttons nach rechts. */
        Region abstand = new Region();
        HBox.setHgrow(abstand, Priority.ALWAYS);

        /* Button zur Lagerübersicht. */
        lagerButton = new Button("Lager");

        /* Button zur Erfassung einer neuen Untersuchung. */
        neueUntersuchungButton = new Button("Neue Untersuchung");
        neueUntersuchungButton.getStyleClass().add("hauptbutton");

        /* Kopfbereich zusammensetzen. */
        kopfbereich.getChildren().addAll(
                titelBereich,
                abstand,
                lagerButton,
                neueUntersuchungButton
        );

        /* Untersuchungstabelle erzeugen. */
        untersuchungsTabelle = new TableView<>();
        untersuchungsTabelle.setMaxWidth(Double.MAX_VALUE);
        untersuchungsTabelle.setMaxHeight(Double.MAX_VALUE);
        untersuchungsTabelle.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        untersuchungsTabelle.setPlaceholder(
                new Label("Noch keine Untersuchungen vorhanden.")
        );

        /* Spalte: Untersuchungsdatum. */
        TableColumn<UntersuchungsEintrag, String> datumSpalte =
                new TableColumn<>("Datum");
        datumSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getDatum()
                )
        );
        datumSpalte.setPrefWidth(145);

        /* Spalte: Untersuchungsnummer. */
        TableColumn<UntersuchungsEintrag, String> idSpalte =
                new TableColumn<>("ID");
        idSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getUntersuchungsnummer()
                )
        );
        idSpalte.setPrefWidth(190);

        /* Spalte: vollständiger Patientenname. */
        TableColumn<UntersuchungsEintrag, String> nameSpalte =
                new TableColumn<>("Name");
        nameSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getPatientenname()
                )
        );
        nameSpalte.setPrefWidth(220);

        /* Spalte: Geburtsdatum. */
        TableColumn<UntersuchungsEintrag, String> geburtsdatumSpalte =
                new TableColumn<>("Geburtsdatum");
        geburtsdatumSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getGeburtsdatum()
                )
        );
        geburtsdatumSpalte.setPrefWidth(155);

        /* Spalte: Untersuchungsart. */
        TableColumn<UntersuchungsEintrag, String> untersuchungsartSpalte =
                new TableColumn<>("Untersuchungsart");
        untersuchungsartSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getUntersuchungsart()
                )
        );
        untersuchungsartSpalte.setPrefWidth(190);

        /* Spalte: verwendete Verbrauchsmaterialien. */
        TableColumn<UntersuchungsEintrag, String> materialSpalte =
                new TableColumn<>("Verbrauchsmaterial");
        materialSpalte.setCellValueFactory(
                eintrag -> new ReadOnlyStringWrapper(
                        eintrag.getValue().getMaterialverbrauch()
                )
        );
        materialSpalte.setPrefWidth(380);

        /* Alle Spalten zur Tabelle hinzufügen. */
        untersuchungsTabelle.getColumns().addAll(
                datumSpalte,
                idSpalte,
                nameSpalte,
                geburtsdatumSpalte,
                untersuchungsartSpalte,
                materialSpalte
        );

        /* Überschrift über der Untersuchungstabelle. */
        Label tabellenTitel = new Label("Untersuchungsübersicht");
        tabellenTitel.getStyleClass().add("abschnittsueberschrift");

        /* Inhaltsbereich der Startseite. */
        VBox inhaltsBereich = new VBox(
                15,
                tabellenTitel,
                untersuchungsTabelle
        );
        inhaltsBereich.setPadding(new Insets(24));
        inhaltsBereich.setFillWidth(true);

        /* Die Tabelle verwendet den restlichen vertikalen Platz. */
        VBox.setVgrow(untersuchungsTabelle, Priority.ALWAYS);

        /* Gesamtlayout zusammensetzen. */
        hauptLayout.setTop(kopfbereich);
        hauptLayout.setCenter(inhaltsBereich);
    }

    /** @return Hauptlayout der Startseite */
    public Parent getAnsicht() {
        return hauptLayout;
    }

    /** @return Button zur Lagerübersicht */
    public Button getLagerButton() {
        return lagerButton;
    }

    /** @return Button zur Erfassung einer neuen Untersuchung */
    public Button getNeueUntersuchungButton() {
        return neueUntersuchungButton;
    }

    /**
     * Gibt die Tabelle der Untersuchungsübersicht zurück.
     * Der Controller lädt die gespeicherten Untersuchungen
     * aus MariaDB und trägt sie in diese Tabelle ein.
     *
     * @return Tabelle mit gespeicherten Untersuchungen
     */
    public TableView<UntersuchungsEintrag> getUntersuchungsTabelle() {
        return untersuchungsTabelle;
    }
}