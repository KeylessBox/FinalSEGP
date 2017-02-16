package modules.demo;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

//Tasks left:
//add video resolution 
//add audio volume

public class GameMenuDemo extends Application {

    private GameMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //create root 
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        //input picture
        InputStream is = Files.newInputStream(Paths.get("src/res/images/thief.jpg"));
        Image img = new Image(is);
        is.close();

        //set picture the same size like root
        ImageView imv = new ImageView(img);
        imv.setFitWidth(800);
        imv.setFitHeight(600);

        //intialize and add game menu to root
        gameMenu = new GameMenu();
        gameMenu.setVisible(false);
        root.getChildren().addAll(imv, gameMenu);

        //create scene 
        Scene scene = new Scene(root);

        //create escape pressed event (Game Pause like)
        scene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ESCAPE) {

                //show menu
                if (!gameMenu.isVisible()) {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    gameMenu.setVisible(true);
                    ft.play();

                //fade menu    
                } else {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> {
                        gameMenu.setVisible(false);

                    });
                    ft.play();

                }

            }

        });

        //stage default operations list:
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    //custom button with effects
    private static class MenuButton extends StackPane {

        private Text text;
        
        //constructor for custom button
        public MenuButton(String name) {

            //label like text
            text = new Text(name);
            text.setFont(text.getFont().font(20));
            text.setFill(Color.WHITE);

            //background for this label
            Rectangle bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, text);

            //adding effects on mouse entered
            setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            //remove this effect
            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });
            
            //effect for pressing button
            DropShadow ds = new DropShadow(50, Color.WHITE);
            ds.setInput(new Glow());

            //adding click mouse event
            setOnMousePressed(event -> {
                setEffect(ds);

            });
            
            //removing this effect
            setOnMouseReleased(event -> {
                setEffect(null);
            });

        }

    }

    //custom menu with 2 menus inside
    private static class GameMenu extends Parent {

        public GameMenu() {

            VBox menu0 = new VBox(10);//  10 distance beetween two elements
            VBox menu1 = new VBox(10);

            //set default menus positions
            menu0.setTranslateX(100);
            menu0.setTranslateY(200);
            menu1.setTranslateX(100);
            menu1.setTranslateY(200);

            //when initialize second menu will be at the left side outside of the view
            final int offset = 400;
            menu1.setTranslateX(offset);

            //remove all effects and leave only the picture
            MenuButton btnResume = new MenuButton("RESUME");
            btnResume.setOnMouseClicked(event -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> setVisible(false));
                ft.play();
            });

            //change menus (Open Second Menu)
            MenuButton btnOptions = new MenuButton("OPTIONS");
            btnOptions.setOnMouseClicked(event -> {

                getChildren().add(menu1);

                //move menu to the left off screen
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                //move second menu on first menu position
                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                //start these translations
                tt.play();
                tt1.play();

                //remove first menu  
                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });

            });

            //Exit from the game
            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(event -> {

                System.exit(0);

            });

            MenuButton btnBack = new MenuButton("BACK");

            //Change Menus back (Open First Menu)
            btnBack.setOnMouseClicked(event -> {

                getChildren().add(menu0);

                //move second menu to the left
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                //move first menu back
                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                //start these translations
                tt.play();
                tt1.play();

                //remove second menu
                tt.setOnFinished(evt -> {

                    getChildren().remove(menu1);

                });

            });

            //extra buttons in Second menu(Not Working yet)
            MenuButton btnSound = new MenuButton("SOUND");
            MenuButton btnVideo = new MenuButton("VIDEO");
            
            //adding everything to the menus
            menu0.getChildren().addAll(btnResume, btnOptions, btnExit);
            menu1.getChildren().addAll(btnBack, btnSound, btnVideo);

            //Grey background layer on top of picture
            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, menu0);

        }
    }

    //launch everything
    public static void main(String[] args) {
        launch(args);
    }

}
