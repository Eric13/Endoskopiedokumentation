package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Lagerartikel;
import javafx.scene.control.TableRow;
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
 * - Warnfunktion,
 * - manuelle Bestandsanpassung.
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
    private final TableView<Lagerartikel> lagerTabelle;

    /*
     * Eingabefeld für die Bestandsänderung.
     */
    private final Spinner<Integer> mengenSpinner;

    /*
     * Buttons zum manuellen Ändern
     * des Lagerbestands.
     */
    private final Button bestandErhoehenButton;

    private final Button bestandVerringernButton;

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

        kopfbereich.setAlignment(
                Pos.CENTER_LEFT
        );

        kopfbereich
                .getStyleClass()
                .add("kopfbereich");

        /*
         * Überschrift.
         */
        Label ueberschrift =
                new Label(
                        "Lagerverwaltung"
                );

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
                new Button(
                        "Zurück"
                );

        /*
         * Kopfbereich zusammensetzen.
         */
        kopfbereich
                .getChildren()
                .addAll(
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
         * Spalten passen sich an
         * die Fensterbreite an.
         */
        lagerTabelle.setColumnResizePolicy(
                TableView
                        .CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
/*
 * Markiert Materialien,
 * deren Bestand den Mindestbestand
 * unterschreitet.
 */
/*
 * Markiert Materialien optisch,
 * deren Bestand unter dem Mindestbestand liegt.
 */
lagerTabelle.setRowFactory(
        tabelle -> new TableRow<>() {

            @Override
            protected void updateItem(
                    Lagerartikel artikel,
                    boolean leer
            ) {

                super.updateItem(
                        artikel,
                        leer
                );

                /*
                 * Die Klasse zunächst entfernen,
                 * da JavaFX Tabellenzeilen wiederverwendet.
                 */
                getStyleClass().remove(
                        "lager-warnung"
                );

                if (!leer
                        && artikel != null
                        && artikel.getBestand()
                        < artikel.getMindestbestand()) {

                    /*
                     * CSS-Klasse für kritischen Bestand setzen.
                     */
                    getStyleClass().add(
                            "lager-warnung"
                    );
                }
            }
        }
);
        /*
         * Spalte: Artikelnummer.
         */
        TableColumn<Lagerartikel, String>
                artikelnummerSpalte =
                new TableColumn<>(
                        "Artikelnummer"
                );

        artikelnummerSpalte.setCellValueFactory(
                eintrag ->
                        new ReadOnlyStringWrapper(
                                eintrag
                                        .getValue()
                                        .getArtikelnummer()
                        )
        );

        artikelnummerSpalte.setPrefWidth(180);

        /*
         * Spalte: Bezeichnung.
         */
        TableColumn<Lagerartikel, String>
                bezeichnungSpalte =
                new TableColumn<>(
                        "Bezeichnung"
                );

        bezeichnungSpalte.setCellValueFactory(
                eintrag ->
                        new ReadOnlyStringWrapper(
                                eintrag
                                        .getValue()
                                        .getBezeichnung()
                        )
        );

        bezeichnungSpalte.setPrefWidth(350);

        /*
         * Spalte: aktueller Bestand.
         */
        TableColumn<Lagerartikel, Number>
                bestandSpalte =
                new TableColumn<>(
                        "Bestand"
                );

        bestandSpalte.setCellValueFactory(
                eintrag ->
                        new ReadOnlyObjectWrapper<>(
                                eintrag
                                        .getValue()
                                        .getBestand()
                        )
        );

        bestandSpalte.setPrefWidth(150);

        /*
         * Spalte: Mindestbestand.
         */
        TableColumn<Lagerartikel, Number>
                mindestbestandSpalte =
                new TableColumn<>(
                        "Mindestbestand"
                );

        mindestbestandSpalte.setCellValueFactory(
                eintrag ->
                        new ReadOnlyObjectWrapper<>(
                                eintrag
                                        .getValue()
                                        .getMindestbestand()
                        )
        );

        mindestbestandSpalte.setPrefWidth(180);

        /*
         * Spalte: Status.
         */
        TableColumn<Lagerartikel, String>
                statusSpalte =
                new TableColumn<>(
                        "Status"
                );

        statusSpalte.setCellValueFactory(
                eintrag ->
                        new ReadOnlyStringWrapper(
                                eintrag
                                        .getValue()
                                        .getStatus()
                        )
        );

        statusSpalte.setPrefWidth(200);

        /*
         * Spalten hinzufügen.
         */
        lagerTabelle
                .getColumns()
                .addAll(
                        artikelnummerSpalte,
                        bezeichnungSpalte,
                        bestandSpalte,
                        mindestbestandSpalte,
                        statusSpalte
                );

        /*
         * Abschnittsüberschrift der Tabelle.
         */
        Label tabellenTitel =
                new Label(
                        "Materialbestand"
                );

        tabellenTitel
                .getStyleClass()
                .add("abschnittsueberschrift");

        /*
         * Überschrift der Bestandspflege.
         */
        Label bestandTitel =
                new Label(
                        "Bestand manuell anpassen"
                );

        bestandTitel
                .getStyleClass()
                .add("abschnittsueberschrift");

        /*
         * Mengenauswahl.
         *
         * Minimum: 1
         * Maximum: 1000
         * Startwert: 1
         */
        mengenSpinner =
                new Spinner<>();

        mengenSpinner.setValueFactory(
                new SpinnerValueFactory
                        .IntegerSpinnerValueFactory(
                        1,
                        1000,
                        1
                )
        );

        mengenSpinner.setEditable(true);

        /*
         * Bestand erhöhen.
         */
        bestandErhoehenButton =
                new Button(
                        "Bestand erhöhen"
                );

        bestandErhoehenButton
                .getStyleClass()
                .add("erfolgsbutton");

        /*
         * Bestand verringern.
         */
        bestandVerringernButton =
                new Button(
                        "Bestand verringern"
                );

        /*
         * Bereich für die Bestandspflege.
         */
        HBox bestandBereich =
                new HBox(
                        10,
                        new Label("Menge:"),
                        mengenSpinner,
                        bestandErhoehenButton,
                        bestandVerringernButton
                );

        bestandBereich.setAlignment(
                Pos.CENTER_LEFT
        );

        /*
         * Inhaltsbereich.
         */
        VBox inhaltsBereich =
                new VBox(
                        15,
                        tabellenTitel,
                        lagerTabelle,
                        bestandTitel,
                        bestandBereich
                );

        inhaltsBereich.setPadding(
                new Insets(24)
        );

        inhaltsBereich.setFillWidth(true);

        /*
         * Tabelle füllt den übrigen
         * Bildschirmbereich.
         */
        VBox.setVgrow(
                lagerTabelle,
                Priority.ALWAYS
        );

        /*
         * Layout zusammensetzen.
         */
        hauptLayout.setTop(
                kopfbereich
        );

        hauptLayout.setCenter(
                inhaltsBereich
        );
    }

    /**
     * Gibt die vollständige Ansicht zurück.
     *
     * @return Lageransicht
     */
    public Parent getAnsicht() {
        return hauptLayout;
    }

    /**
     * Gibt den Zurück-Button zurück.
     *
     * @return Zurück-Button
     */
    public Button getZurueckButton() {
        return zurueckButton;
    }

    /**
     * Gibt die Lagertabelle zurück.
     *
     * @return Tabelle mit Lagerartikeln
     */
    public TableView<Lagerartikel>
    getLagerTabelle() {
        return lagerTabelle;
    }

    /**
     * Gibt den Mengen-Spinner zurück.
     *
     * @return Mengenauswahl
     */
    public Spinner<Integer> getMengenSpinner() {
        return mengenSpinner;
    }

    /**
     * Gibt den Button zum Erhöhen
     * des Lagerbestands zurück.
     *
     * @return Bestand-erhöhen-Button
     */
    public Button getBestandErhoehenButton() {
        return bestandErhoehenButton;
    }

    /**
     * Gibt den Button zum Verringern
     * des Lagerbestands zurück.
     *
     * @return Bestand-verringern-Button
     */
    public Button getBestandVerringernButton() {
        return bestandVerringernButton;
    }
}