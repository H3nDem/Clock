import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class HorlogeGraphiqueApp extends Application {
    private Circle circle;
    private Line hourHand;
    private Line minuteHand;
    private Line secondHand;

    private double hourTick = 0;
    private double minuteTick = 0;
    private double secondTick = 0;

    private Pane creeHorloge() {
        Pane pane = new Pane();

        // Dessine le cadran
        circle = new Circle(150, 150, 100);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);

        // Dessine les traits indiquant chaque 5min
        for (int i = 0; i < 12; i++) {
            double x1 = 150 + Math.cos(i * 30 * Math.PI / 180) * 95;
            double y1 = 150 - Math.sin(i * 30 * Math.PI / 180) * 95;
            double x2 = 150 + Math.cos(i * 30 * Math.PI / 180) * 90;
            double y2 = 150 - Math.sin(i * 30 * Math.PI / 180) * 90;
            Line tick = new Line(x1, y1, x2, y2);
            pane.getChildren().add(tick);
        }

        // Desinne les 3 aiguilles
        hourHand = new Line(150, 150, 150, 100);
        minuteHand = new Line(150, 150, 150, 75);
        secondHand = new Line(150, 150, 150, 60);
        hourHand.setStroke(Color.RED);
        minuteHand.setStroke(Color.LIGHTGREEN);
        secondHand.setStroke(Color.BLUE);
        pane.getChildren().add(secondHand);
        pane.getChildren().add(minuteHand);
        pane.getChildren().add(hourHand);

        return pane;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = creeHorloge();
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Horloge GRAPHIQUE");
        primaryStage.setScene(scene);
        primaryStage.show();
        startTicks(1000);
    }

    public void secondTick(double angle) {
        double endX = 150 + Math.sin(angle * 30 * Math.PI / 180) * 90;
        double endY = 150 - Math.cos(angle * 30 * Math.PI / 180) * 90;
        secondHand.setStartX(150);
        secondHand.setStartY(150);
        secondHand.setEndX(endX);
        secondHand.setEndY(endY);
    }

    public void minuteTick(double angle) {
        double endX = 150 + Math.sin(angle * 30 * Math.PI / 180) * 75;
        double endY = 150 - Math.cos(angle * 30 * Math.PI / 180) * 75;
        minuteHand.setStartX(150);
        minuteHand.setStartY(150);
        minuteHand.setEndX(endX);
        minuteHand.setEndY(endY);
    }

    public void hourTick(double angle) {
        double endX = 150 + Math.sin(angle * 30 * Math.PI / 180) * 50;
        double endY = 150 - Math.cos(angle * 30 * Math.PI / 180) * 50;
        hourHand.setStartX(150);
        hourHand.setStartY(150);
        hourHand.setEndX(endX);
        hourHand.setEndY(endY);
    }

    public void tick(int second) {
        secondTick(secondTick);
        if (second % 60 == 0) {
            minuteTick(minuteTick);
            if (second % 3600 == 0) {
                hourTick(hourTick);
                hourTick += 0.2 % 3600;
            }
            minuteTick += 0.2 % 360;
        }
        secondTick += 0.2 % 360;
    }

    private void startTicks(int periode) {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(periode),
                new EventHandler<ActionEvent>() {
                    int second = 0;
                    public void handle(ActionEvent event) {
                        tick(second);
                        second++;
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}