package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Benutzeroberfläche zur Erfassung einer neuen Untersuchung.
 *
 * Die Ansicht enthält:
 * - Patienten- und Leistungsdaten,
 * - Auswahl der Untersuchungsart,
 * - Erfassung mehrerer Verbrauchsmaterialien,
 * - Materialmenge,
 * - Speichern- und Navigationsbuttons.
 *
 * Die Datenbank- und Speicherlogik wird später ergänzt.
 */
public class NeueUntersuchung {

    /*
     * Hauptlayout.
     */
    private final BorderPane hauptLayout;

    /*
     * Patienten- und Leistungsfelder.
     */
    private final DatePicker datumFeld;
    private final TextField patientenIdFeld;
    private final TextField vornameFeld;
    private final TextField nachnameFeld;
    private final DatePicker geburtsdatumFeld;
    private final ComboBox<String> untersuchungsartAuswahl;

    /*
     * Materialfelder.
     */
    private final ComboBox<String> materialAuswahl;
    private final Spinner<Integer> mengenAuswahl;
    private final ListView<String> materialListe;

    /*
     * Buttons.
     */
    private final Button materialHinzufuegenButton;
    private final Button materialEntfernenButton;
    private final Button speichernButton;
    private final Button zurueckButton;

    /**
     * Konstruktor erstellt die gesamte Ansicht.
     */
    public NeueUntersuchung() {

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
                new Label("Neue Untersuchung");

        ueberschrift
                .getStyleClass()
                .add("ueberschrift");

        /*
         * Untertitel.
         */
        Label untertitel =
                new Label(
                        "Patienten-, Leistungs- und Materialdaten erfassen"
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
         * Eingabefelder erzeugen.
         */
        datumFeld = new DatePicker();
        datumFeld.setPromptText(
                "Untersuchungsdatum"
        );

        patientenIdFeld = new TextField();
        patientenIdFeld.setPromptText(
                "z. B. P-1001"
        );

        vornameFeld = new TextField();
        vornameFeld.setPromptText("Vorname");

        nachnameFeld = new TextField();
        nachnameFeld.setPromptText("Nachname");

        geburtsdatumFeld = new DatePicker();
        geburtsdatumFeld.setPromptText(
                "Geburtsdatum"
        );

        /*
         * Untersuchungsarten.
         *
         * Später werden diese aus MariaDB geladen.
         */
        untersuchungsartAuswahl =
                new ComboBox<>();

        untersuchungsartAuswahl.getItems().addAll(
                "Gastroskopie",
                "Koloskopie",
                "ERCP"
        );

        untersuchungsartAuswahl.setPromptText(
                "Untersuchungsart auswählen"
        );

        /*
         * Eingabefelder über die verfügbare Breite ziehen.
         */
        datumFeld.setMaxWidth(Double.MAX_VALUE);
        patientenIdFeld.setMaxWidth(Double.MAX_VALUE);
        vornameFeld.setMaxWidth(Double.MAX_VALUE);
        nachnameFeld.setMaxWidth(Double.MAX_VALUE);
        geburtsdatumFeld.setMaxWidth(Double.MAX_VALUE);
        untersuchungsartAuswahl.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * Formular für Patientendaten.
         */
        GridPane patientenFormular =
                new GridPane();

        patientenFormular.setHgap(18);
        patientenFormular.setVgap(16);

        /*
         * Spalte für Feldbezeichnungen.
         */
        ColumnConstraints beschriftungsSpalte =
                new ColumnConstraints();

        beschriftungsSpalte.setMinWidth(180);

        /*
         * Flexible Spalte für Eingabefelder.
         */
        ColumnConstraints eingabeSpalte =
                new ColumnConstraints();

        eingabeSpalte.setMinWidth(250);
        eingabeSpalte.setHgrow(
                Priority.ALWAYS
        );

        eingabeSpalte.setFillWidth(true);

        patientenFormular
                .getColumnConstraints()
                .addAll(
                        beschriftungsSpalte,
                        eingabeSpalte
                );

        /*
         * Formularfelder einfügen.
         */
        patientenFormular.add(
                new Label("Untersuchungsdatum"),
                0,
                0
        );

        patientenFormular.add(
                datumFeld,
                1,
                0
        );

        patientenFormular.add(
                new Label("Patienten-ID"),
                0,
                1
        );

        patientenFormular.add(
                patientenIdFeld,
                1,
                1
        );

        patientenFormular.add(
                new Label("Vorname"),
                0,
                2
        );

        patientenFormular.add(
                vornameFeld,
                1,
                2
        );

        patientenFormular.add(
                new Label("Nachname"),
                0,
                3
        );

        patientenFormular.add(
                nachnameFeld,
                1,
                3
        );

        patientenFormular.add(
                new Label("Geburtsdatum"),
                0,
                4
        );

        patientenFormular.add(
                geburtsdatumFeld,
                1,
                4
        );

        patientenFormular.add(
                new Label("Untersuchungsart"),
                0,
                5
        );

        patientenFormular.add(
                untersuchungsartAuswahl,
                1,
                5
        );

        /*
         * Materialauswahl.
         */
        materialAuswahl =
                new ComboBox<>();

        materialAuswahl.getItems().addAll(
                "Biopsiezange",
                "Clip",
                "Injektionsnadel",
                "Polypektomieschlinge"
        );

        materialAuswahl.setPromptText(
                "Material auswählen"
        );

        materialAuswahl.setMaxWidth(
                Double.MAX_VALUE
        );

        HBox.setHgrow(
                materialAuswahl,
                Priority.ALWAYS
        );

        /*
         * Mengenauswahl:
         * Minimum 1, Maximum 100, Startwert 1.
         */
        mengenAuswahl =
                new Spinner<>(1, 100, 1);

        mengenAuswahl.setPrefWidth(100);

        /*
         * Material hinzufügen.
         */
        materialHinzufuegenButton =
                new Button("Material hinzufügen");

        materialHinzufuegenButton
                .getStyleClass()
                .add("hauptbutton");

        /*
         * Material entfernen.
         */
        materialEntfernenButton =
                new Button("Material entfernen");

        /*
         * Liste der ausgewählten Materialien.
         */
        materialListe =
                new ListView<>();

        materialListe.setPlaceholder(
                new Label(
                        "Noch kein Material hinzugefügt."
                )
        );

        materialListe.setMinHeight(160);
        materialListe.setMaxHeight(
                Double.MAX_VALUE
        );

        materialListe.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * Materialeingabe.
         */
        HBox materialEingabe =
                new HBox(
                        10,
                        materialAuswahl,
                        mengenAuswahl,
                        materialHinzufuegenButton
                );

        materialEingabe.setAlignment(
                Pos.CENTER_LEFT
        );

        materialEingabe.setFillHeight(true);

        /*
         * Speichern-Button.
         */
        speichernButton =
                new Button("Untersuchung speichern");

        speichernButton
                .getStyleClass()
                .add("erfolgsbutton");

        speichernButton.setPrefWidth(230);

        /*
         * Überschriften der Formularkarten.
         */
        Label patientenTitel =
                new Label(
                        "Patienten- und Leistungsdaten"
                );

        patientenTitel
                .getStyleClass()
                .add("abschnittsueberschrift");

        Label materialTitel =
                new Label("Verbrauchsmaterial");

        materialTitel
                .getStyleClass()
                .add("abschnittsueberschrift");

        /*
         * Karte für Patienten- und Leistungsdaten.
         */
        VBox patientenKarte =
                new VBox(
                        18,
                        patientenTitel,
                        patientenFormular
                );

        patientenKarte
                .getStyleClass()
                .add("formular-karte");

        patientenKarte.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * Karte für Materialdaten.
         */
        VBox materialKarte =
                new VBox(
                        14,
                        materialTitel,
                        materialEingabe,
                        materialListe,
                        materialEntfernenButton
                );

        materialKarte
                .getStyleClass()
                .add("formular-karte");

        materialKarte.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * Materialliste nutzt zusätzlichen Platz.
         */
        VBox.setVgrow(
                materialListe,
                Priority.ALWAYS
        );

        /*
         * Speichern-Button rechts ausrichten.
         */
        HBox speichernBereich =
                new HBox(speichernButton);

        speichernBereich.setAlignment(
                Pos.CENTER_RIGHT
        );

        /*
         * Gesamter Inhaltsbereich.
         */
        VBox inhaltsBereich =
                new VBox(
                        20,
                        patientenKarte,
                        materialKarte,
                        speichernBereich
                );

        inhaltsBereich.setPadding(
                new Insets(24)
        );

        inhaltsBereich.setFillWidth(true);
        inhaltsBereich.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * ScrollPane ermöglicht die Nutzung auch
         * auf kleineren Displays.
         */
        ScrollPane scrollBereich =
                new ScrollPane(inhaltsBereich);

        scrollBereich.setFitToWidth(true);
        scrollBereich.setFitToHeight(false);
        scrollBereich.setPannable(true);

        scrollBereich
                .getStyleClass()
                .add("transparenter-scrollbereich");

        /*
         * Layout zusammensetzen.
         */
        hauptLayout.setTop(kopfbereich);
        hauptLayout.setCenter(scrollBereich);
    }

    public Parent getAnsicht() {
        return hauptLayout;
    }

    public DatePicker getDatumFeld() {
        return datumFeld;
    }

    public TextField getPatientenIdFeld() {
        return patientenIdFeld;
    }

    public TextField getVornameFeld() {
        return vornameFeld;
    }

    public TextField getNachnameFeld() {
        return nachnameFeld;
    }

    public DatePicker getGeburtsdatumFeld() {
        return geburtsdatumFeld;
    }

    public ComboBox<String> getUntersuchungsartAuswahl() {
        return untersuchungsartAuswahl;
    }

    public ComboBox<String> getMaterialAuswahl() {
        return materialAuswahl;
    }

    public Spinner<Integer> getMengenAuswahl() {
        return mengenAuswahl;
    }

    public ListView<String> getMaterialListe() {
        return materialListe;
    }

    public Button getMaterialHinzufuegenButton() {
        return materialHinzufuegenButton;
    }

    public Button getMaterialEntfernenButton() {
        return materialEntfernenButton;
    }

    public Button getSpeichernButton() {
        return speichernButton;
    }

    public Button getZurueckButton() {
        return zurueckButton;
    }
}
