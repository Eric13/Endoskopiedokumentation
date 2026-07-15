package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
 * Moderne Benutzeroberfläche zur Erfassung
 * einer neuen Untersuchung.
 *
 * Die View enthält ausschließlich Elemente
 * der Benutzeroberfläche.
 *
 * Validierung, Speicherung und Datenbankzugriffe
 * werden später in einem Controller umgesetzt.
 */
public class NeueUntersuchung {

    /*
     * Hauptlayout der Seite.
     */
    private final BorderPane hauptLayout;

    /*
     * Eingabefelder für Patienten- und Leistungsdaten.
     */
    private final DatePicker datumFeld;
    private final TextField patientenIdFeld;
    private final TextField vornameFeld;
    private final TextField nachnameFeld;
    private final DatePicker geburtsdatumFeld;
    private final ComboBox<String> untersuchungsartAuswahl;

    /*
     * Eingabefelder für den Materialverbrauch.
     */
    private final ComboBox<String> materialAuswahl;
    private final Spinner<Integer> mengenAuswahl;
    private final ListView<String> materialListe;

    /*
     * Aktionsbuttons.
     */
    private final Button materialHinzufuegenButton;
    private final Button materialEntfernenButton;
    private final Button speichernButton;
    private final Button zurueckButton;

    /**
     * Konstruktor erstellt die vollständige Oberfläche.
     */
    public NeueUntersuchung() {

        /*
         * Hauptlayout erstellen.
         */
        hauptLayout = new BorderPane();
        hauptLayout.setPadding(new Insets(0));

        /*
         * Kopfbereich erstellen.
         */
        HBox kopfbereich = new HBox(12);
        kopfbereich.setAlignment(Pos.CENTER_LEFT);
        kopfbereich.getStyleClass().add("kopfbereich");

        /*
         * Titel.
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

        untertitel.setStyle(
                "-fx-text-fill: #6b7b8c;" +
                "-fx-font-size: 13px;"
        );

        /*
         * Titel und Untertitel untereinander anordnen.
         */
        VBox titelBereich =
                new VBox(
                        3,
                        ueberschrift,
                        untertitel
                );

        /*
         * Flexibler Abstand verschiebt den Zurück-Button
         * nach rechts.
         */
        Region abstand = new Region();

        HBox.setHgrow(
                abstand,
                Priority.ALWAYS
        );

        /*
         * Zurück-Button.
         *
         * Die Aktion wird im NavigationsController festgelegt.
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
        datumFeld.setPromptText("Untersuchungsdatum");

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
         * Auswahl der Untersuchungsart.
         *
         * Diese Werte werden später aus MariaDB geladen.
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

        untersuchungsartAuswahl.setMaxWidth(
                Double.MAX_VALUE
        );

        /*
         * Materialauswahl.
         *
         * Die Daten werden später aus der Tabelle Material geladen.
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

        materialAuswahl.setPrefWidth(260);

        /*
         * Mengenauswahl:
         * mindestens 1,
         * höchstens 100,
         * Startwert 1.
         */
        mengenAuswahl =
                new Spinner<>(1, 100, 1);

        mengenAuswahl.setPrefWidth(90);

        /*
         * Material hinzufügen.
         *
         * Die eigentliche Aktion wird später
         * vom Controller festgelegt.
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
         * Liste zur Anzeige der bereits ausgewählten Materialien.
         */
        materialListe =
                new ListView<>();

        materialListe.setPrefHeight(170);

        materialListe.setPlaceholder(
                new Label(
                        "Noch kein Material hinzugefügt."
                )
        );

        /*
         * Speichern-Button.
         *
         * Die Speicherlogik folgt später im Controller.
         */
        speichernButton =
                new Button("Untersuchung speichern");

        speichernButton
                .getStyleClass()
                .add("erfolgsbutton");

        speichernButton.setPrefWidth(220);

        /*
         * Patienten- und Leistungsdatenkarte.
         */
        GridPane patientenFormular =
                new GridPane();

        patientenFormular.setHgap(18);
        patientenFormular.setVgap(16);

        /*
         * Erste Spalte enthält Bezeichnungen.
         */
        ColumnConstraints beschriftungsSpalte =
                new ColumnConstraints();

        beschriftungsSpalte.setMinWidth(170);

        /*
         * Zweite Spalte enthält Eingabefelder
         * und soll den restlichen Platz nutzen.
         */
        ColumnConstraints eingabeSpalte =
                new ColumnConstraints();

        eingabeSpalte.setMinWidth(320);
        eingabeSpalte.setHgrow(Priority.ALWAYS);

        patientenFormular
                .getColumnConstraints()
                .addAll(
                        beschriftungsSpalte,
                        eingabeSpalte
                );

        datumFeld.setMaxWidth(Double.MAX_VALUE);
        patientenIdFeld.setMaxWidth(Double.MAX_VALUE);
        vornameFeld.setMaxWidth(Double.MAX_VALUE);
        nachnameFeld.setMaxWidth(Double.MAX_VALUE);
        geburtsdatumFeld.setMaxWidth(Double.MAX_VALUE);

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
         * Materialeingabe horizontal anordnen.
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

        /*
         * Überschrift für Patientendaten.
         */
        Label patientenTitel =
                new Label(
                        "Patienten- und Leistungsdaten"
                );

        patientenTitel.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #173b63;"
        );

        /*
         * Überschrift für den Materialbereich.
         */
        Label materialTitel =
                new Label(
                        "Verbrauchsmaterial"
                );

        materialTitel.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #173b63;"
        );

        /*
         * Karte für Patientendaten.
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

        /*
         * Speichern-Button rechts ausrichten.
         */
        HBox speichernBereich =
                new HBox(speichernButton);

        speichernBereich.setAlignment(
                Pos.CENTER_RIGHT
        );

        /*
         * Hauptinhalt.
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
     * Gibt das Untersuchungsdatum zurück.
     */
    public DatePicker getDatumFeld() {
        return datumFeld;
    }

    /**
     * Gibt das Feld für die Patienten-ID zurück.
     */
    public TextField getPatientenIdFeld() {
        return patientenIdFeld;
    }

    /**
     * Gibt das Vornamenfeld zurück.
     */
    public TextField getVornameFeld() {
        return vornameFeld;
    }

    /**
     * Gibt das Nachnamenfeld zurück.
     */
    public TextField getNachnameFeld() {
        return nachnameFeld;
    }

    /**
     * Gibt das Feld für das Geburtsdatum zurück.
     */
    public DatePicker getGeburtsdatumFeld() {
        return geburtsdatumFeld;
    }

    /**
     * Gibt die Auswahl der Untersuchungsart zurück.
     */
    public ComboBox<String> getUntersuchungsartAuswahl() {
        return untersuchungsartAuswahl;
    }

    /**
     * Gibt die Materialauswahl zurück.
     */
    public ComboBox<String> getMaterialAuswahl() {
        return materialAuswahl;
    }

    /**
     * Gibt die Mengenauswahl zurück.
     */
    public Spinner<Integer> getMengenAuswahl() {
        return mengenAuswahl;
    }

    /**
     * Gibt die Liste der ausgewählten Materialien zurück.
     */
    public ListView<String> getMaterialListe() {
        return materialListe;
    }

    /**
     * Gibt den Hinzufügen-Button zurück.
     */
    public Button getMaterialHinzufuegenButton() {
        return materialHinzufuegenButton;
    }

    /**
     * Gibt den Entfernen-Button zurück.
     */
    public Button getMaterialEntfernenButton() {
        return materialEntfernenButton;
    }

    /**
     * Gibt den Speichern-Button zurück.
     */
    public Button getSpeichernButton() {
        return speichernButton;
    }

    /**
     * Gibt den Zurück-Button zurück.
     */
    public Button getZurueckButton() {
        return zurueckButton;
    }
}