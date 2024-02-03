
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

public class HorlogeApp extends Application {

    private Compteur compteur1;
    private CompteurCompose compteur2;
    private CompteurCompose compteur3;

    private Label labelValeurHeure;
    private Label labelValeurMinute;
    private Label labelValeurSeconde;

    private TextArea textHeure;
    private TextArea textMinute;
    private TextArea textSeconde;

    private Button setHorlogeButton;
    private boolean isSet = false;

    @Override
    public void start(Stage primaryStage) {
        creeCompteur();
        primaryStage.setTitle("Horloge");
        FlowPane pane = creeContenuFenetre();
        bindModelView();
        primaryStage.setTitle("Horloge DIGITALE");
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    private void creeCompteur() {
        compteur1 = new Compteur(0,12);
        compteur2 = new CompteurCompose(0, 60, compteur1);
        compteur3 = new CompteurCompose(0,60, compteur2);
    }

    private FlowPane creeContenuFenetre() {
        FlowPane pane = new FlowPane();
        labelValeurHeure = new Label("Heure" + compteur1.getInit());
        labelValeurMinute = new Label("Minute" + compteur2.getInit());
        labelValeurSeconde = new Label("Seconde" + compteur3.getInit());

        textHeure = new TextArea();
        textHeure.setPrefColumnCount(0); textHeure.setPrefRowCount(0);
        textMinute = new TextArea();
        textMinute.setPrefColumnCount(0); textMinute.setPrefRowCount(0);
        textSeconde = new TextArea();
        textSeconde.setPrefColumnCount(0); textSeconde.setPrefRowCount(0);

        setHorlogeButton = new Button("Set");
        setHorlogeButton.setOnAction(e -> setValueToHorloge());

        pane.getChildren().addAll(
                new Label("  "), setHorlogeButton, new Label("   "),
                textHeure,
                textMinute,
                textSeconde, new Label("   "),
                labelValeurHeure, new Label("h"),
                labelValeurMinute, new Label("min"),
                labelValeurSeconde, new Label("s")
        );
        return pane;
    }

    private void startTicks(Compteur compteur, int periode) {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(periode),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        compteur.tick();
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void bindModelView() {
        labelValeurHeure.textProperty().bind(compteur1.valeurProperty().asString());
        labelValeurMinute.textProperty().bind(compteur2.valeurProperty().asString());
        labelValeurSeconde.textProperty().bind(compteur3.valeurProperty().asString());
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
            compteur1.setValeur(heure);
            compteur2.setValeur(minute);
            compteur3.setValeur(seconde);
        } else {
            System.out.println("Erreur : Format d'heure incorrect");
        }

        // L'horloge demarre une fois que l'on clique sur "set"
        // si on n'effectue plusieurs fois l'appel a cette methode, cela va accelerer la vitesse des ticks de temps
        // et donc la vitesse a laquelle s'ecoule le temps cette condition est faite pour empecher cela
        if (!isSet) {
            startTicks(compteur3, 1000);
            isSet = true;
        }
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
