
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.compteur.Compteur;
import model.compteur.CompteurCompose;

public class ReveilApp extends Application {

    private Compteur cHeure;
    private CompteurCompose cMinute;
    private CompteurCompose cSeconde;
    private Compteur cHeureRev;
    private Compteur cMinuteRev;

    private Label lHeure;
    private Label lMinute;
    private Label lSeconde;
    private Label lHeureRev;
    private Label lMinuteRev;

    private TextArea textHeure;
    private TextArea textMinute;
    private TextArea textSeconde;
    private TextArea textHeureRev;
    private TextArea textMinuteRev;

    private Button setHorloge;
    private Button setReveil;

    private boolean isSet = false;
    private boolean isRevSet = false;

    private FlowPane pane;

    @Override
    public void start(Stage primaryStage) {
        creeCompteur();
        primaryStage.setTitle("Horloge");
        FlowPane pane = creeContenuFenetre();
        bindModelView();
        primaryStage.setTitle("Horloge REVEIL");
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    private void creeCompteur() {
        cHeure = new Compteur(0,12);
        cMinute = new CompteurCompose(0, 60, cHeure);
        cSeconde = new CompteurCompose(0,60, cMinute);
        cHeureRev = new Compteur(0,12);
        cMinuteRev = new Compteur(0,60);
    }

    private FlowPane creeContenuFenetre() {
        pane = new FlowPane();
        lHeure = new Label("Heure" + cHeure.getInit());
        lMinute = new Label("Minute" + cMinute.getInit());
        lSeconde = new Label("Seconde" + cSeconde.getInit());
        lHeureRev = new Label("Heure" + cHeureRev.getInit());
        lMinuteRev = new Label("Minute" + cMinuteRev.getInit());

        textHeure = new TextArea();
        textHeure.setPrefColumnCount(0); textHeure.setPrefRowCount(0);
        textMinute = new TextArea();
        textMinute.setPrefColumnCount(0); textMinute.setPrefRowCount(0);
        textSeconde = new TextArea();
        textSeconde.setPrefColumnCount(0); textSeconde.setPrefRowCount(0);

        textHeureRev = new TextArea();
        textHeureRev.setPrefColumnCount(0); textHeureRev.setPrefRowCount(0);
        textMinuteRev = new TextArea();
        textMinuteRev.setPrefColumnCount(0); textMinuteRev.setPrefRowCount(0);


        setHorloge = new Button("Set");
        setHorloge.setOnAction(e -> setValueToHorloge());

        setReveil = new Button("Reveil");
        setReveil.setOnAction(e -> setValueToReveil());

        pane.getChildren().addAll(
                new Label("  "), setHorloge, new Label("   "),
                textHeure,
                textMinute,
                textSeconde, new Label("   "),
                lHeure, new Label("h"),
                lMinute, new Label("min"),
                lSeconde, new Label("s"),

                textHeureRev,
                textMinuteRev,
                lHeureRev, new Label("h"),
                lMinuteRev, new Label("min"),
                new Label("  "), setReveil, new Label("   ")
        );
        return pane;
    }

    private void startTicks(Compteur compteur, int periode) {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(periode),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        compteur.tick();
                        checkReveil();
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void bindModelView() {
        lHeure.textProperty().bind(cHeure.valeurProperty().asString());
        lMinute.textProperty().bind(cMinute.valeurProperty().asString());
        lSeconde.textProperty().bind(cSeconde.valeurProperty().asString());
        lHeureRev.textProperty().bind(cHeureRev.valeurProperty().asString());
        lMinuteRev.textProperty().bind(cMinuteRev.valeurProperty().asString());
    }

    // Applique les valeurs ecrite dans l'horloge
    private void setValueToHorloge() {
/*  Exemple : si on souhaite mettre 10h 5min 30s on ecrit dans
        1er zone de texte : "10"
        2eme zone de texte : "5"
        3eme zone de texte : "30"

        sans le remove bracket,textHeure/Minute/Seconde valent respectivement [10],[5] et [30]
        les crochets cause des erreurs avec le Integer.parseInt
*/
        int heure = Integer.parseInt(removeBracket(textHeure.getParagraphs().toString())); // heure = 10
        int minute = Integer.parseInt(removeBracket(textMinute.getParagraphs().toString())); // minute = 5
        int seconde = Integer.parseInt(removeBracket(textSeconde.getParagraphs().toString())); // seconde = 30

        // on met nos 3 valeurs dans l'horloge si le format est respecté,
        // car on ne peut pas regler une horloge a 25h 78min 152s par exemple
        // on est dans un format 12h
        if (heure <= 11 && minute <= 59 && seconde <= 59 && heure >= 0 && minute >= 0 && seconde >= 0) {
            cHeure.setValeur(heure);
            cMinute.setValeur(minute);
            cSeconde.setValeur(seconde);
        } else {
            System.out.println("Erreur : Format d'heure incorrect");
        }

        // L'horloge demarre une fois que l'on clique sur "set"
        // si on n'effectue plusieurs fois l'appel a cette methode, cela va accelerer la vitesse des ticks de temps
        // et donc la vitesse a laquelle s'ecoule le temps cette condition est faite pour empecher cela
        if (!isSet) {
            startTicks(cSeconde, 1000);
            isSet = true;
        }
    }

    private void setValueToReveil() {
        int heure = Integer.parseInt(removeBracket(textHeureRev.getParagraphs().toString()));
        int minute = Integer.parseInt(removeBracket(textMinuteRev.getParagraphs().toString()));

        if (heure <= 11 && minute <= 59 && heure >= 0 && minute >= 0) {
            cHeureRev.setValeur(heure);
            cMinuteRev.setValeur(minute);
            isRevSet = true;
        } else {
            System.out.println("Erreur : Format d'heure incorrect");
        }
    }

    private void checkReveil() {
        if (cHeure.getValeur() == cHeureRev.getValeur() && cMinute.getValeur() == cMinuteRev.getValeur() && isRevSet) {
            System.out.println("REVEIL");
            pane.getChildren().removeAll();
            setReveil = new Button("STOP");

        }
    }

    private void stopRev() {
        setReveil = new Button("Reveil");
        isRevSet = false;
        cHeureRev.setValeur(0);
        cMinuteRev.setValeur(0);
    }

    // enleve les [] entre les valeurs pour recouperer par la suite en entier pour le mette dans l'horloge
    private String removeBracket(String s) {
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '[' && s.charAt(i) != ']') {
                newString += s.charAt(i);
            }
        }
        return newString;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
