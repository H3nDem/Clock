
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.compteur.Compteur;
import model.compteur.CompteurCompose;

public class CompteurApp extends Application {
	
	private Compteur compteur1;
	private CompteurCompose compteur2;

	private Label labelValeurCompteur1;
	private Label labelValeurCompteur2;
	
	@Override
	public void start(Stage primaryStage) {
		
		creeCompteur();
		primaryStage.setTitle("Compteur composé");
		FlowPane pane = creeContenuFenetre();		
		startTicks(compteur2, 1000);
		bindModelView();
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}
	
	private void creeCompteur() {
		compteur1 = new Compteur(0,3);
		compteur2 = new CompteurCompose(0, 5, compteur1);
	}
	
	private FlowPane creeContenuFenetre() {
		FlowPane pane = new FlowPane();
		labelValeurCompteur1 = new Label("" + compteur1.getInit());
		labelValeurCompteur2 = new Label("" + compteur2.getInit());
		pane.getChildren().addAll(labelValeurCompteur1, new Label(":"), labelValeurCompteur2);
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
		labelValeurCompteur1.textProperty().bind(compteur1.valeurProperty().asString());
		labelValeurCompteur2.textProperty().bind(compteur2.valeurProperty().asString());		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
