package modules.demo;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Aleksandr on 16/08/2016.
 */
public class FarCry extends Application {

    private static final int APP_W = 800;
    private static final int APP_H = 600;

    private Line loadingBar = new Line();
    private ResourceLoadingTask task = new ResourceLoadingTask();
    private LoadingCircle loadingCircle;


    private Parent createContent() throws IOException {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        ImageView img = new ImageView(new Image(Files.newInputStream(Paths.get("src/res/images/tiger.jpg"))));
        img.setFitWidth(APP_W);
        img.setFitHeight(APP_H);

        Rectangle bg = new Rectangle(APP_W, APP_H);
        bg.setOpacity(0.5);
        loadingCircle = new LoadingCircle();
        loadingCircle.setTranslateX(APP_W - 120);
        loadingCircle.setTranslateY(APP_H - 100);

        Line loadingBarBG = new Line(100, APP_H - 70, APP_W - 100, APP_H - 70);
        loadingBarBG.setStroke(Color.GREY);

        loadingBar.setStartX(100);
        loadingBar.setStartY(APP_H - 70);
        loadingBar.setEndX(100);
        loadingBar.setEndY(APP_H - 70);
        loadingBar.setStroke(Color.WHITE);

        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            double progress = newValue.doubleValue();
            loadingBar.setEndX(100 + progress * (APP_W - 200));
        });

        Title title = new Title("C A M P A I G N");
        title.setTranslateX(APP_W/2 -160);
        title.setTranslateY(APP_H/2 - 120);

        root.getChildren().addAll(img, bg,title, loadingCircle, loadingBarBG, loadingBar);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setTitle("Game name");
        primaryStage.setScene(scene);
        primaryStage.show();

        //background thread
        Thread t = new Thread(task);
        t.start();

    }

    private static class LoadingCircle extends Parent {
        private RotateTransition animation;

        public LoadingCircle() {
            Circle circle = new Circle(20);
            circle.setFill(null);
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(2);

            Rectangle rect = new Rectangle(20, 20);
            Shape shape = Shape.subtract(circle, rect);
            shape.setFill(Color.WHITE);
            getChildren().add(shape);
            animation = new RotateTransition(Duration.seconds(2.5), this);
            animation.setByAngle(-360);

            //circle will rotate with the same speed al the time
            animation.setInterpolator(Interpolator.LINEAR);

            //animation will play indefinitely
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();

        }

        public void stopRotation() {
            animation.stop();
        }
    }

    //create Title with rectangle stroke
    private static class Title extends StackPane {

        public Title(String name) {
            Rectangle bg = new Rectangle(320, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(3);
            bg.setFill(null);
            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.BOLD, 40));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

        }


    }

    //create background thread to UI thread
    private class ResourceLoadingTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {

            //Will be executed in different Thread:
            for (int i = 0; i < 100; i++) {
                Thread.sleep((int) (Math.random() * 100));//not working
                updateProgress(i + 1, 100);
            }
            System.out.println("Resource loaded");
            return null;
        }

        //stop rotation of the circle
        protected void succeeded() {
            loadingCircle.stopRotation();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
