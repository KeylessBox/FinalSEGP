package sample;

/**
 * Created by Flow on 10/22/2016.
 */
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;

/**
 *
 * @author Flow
 */
public class LoginWindow extends Application {

    GridPane gridLogin;
    GridPane gridSignUp;
    TextField userField;
    TextField createUserField;

    Label loginLabel;
    Label userLabel;
    Label passLabel;
    Label createAccLabel;
    Label createUserLabel;
    Label createPassLabel;
    Label confirmPassLabel;

    PasswordField passField;
    PasswordField createPassField;
    PasswordField confirmPassField;

    Button loginButton;
    Button signupButton;
    Button sendButton;
    Button goToLogin;

    HBox buttons;

    Scene loginScene;
    Scene signupScene;

    CheckBox rememberUser;

    Stage window;

    //Method to store account details in a file
    private void createAccount(TextField usr, PasswordField psw, PasswordField cpsw){
        String user  = usr.getText();
        String pass  = psw.getText();
        String cpass = cpsw.getText();
        if(user.length()>0)
            if(pass.length() >= 6 && pass.equals(cpass))
            {
                File outFile = new File(".\\res\\Account.dat");
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                    writer.write(user);
                    writer.newLine();
                    writer.write(pass);
                    writer.newLine();

                    writer.close();

                }catch(Exception e){

                }
            }
    }
    //Method to save the Username when the Checkbox is ticked
    private void rememberUsername(TextField usr) {

        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(".\\res\\CheckBox.txt"));
            write.write(usr.getText());
            write.close();

        } catch (Exception e) {
        }
    }

    //Method to Fill the "Username Field" with the saved Username if the Checkbox was ticked last time
    private String rememberedUsername (){
        File f = new File(".\\res\\CheckBox.txt");
        if(f.exists()) {
            try {
                BufferedReader read = new BufferedReader(new FileReader(f));
                String usr = read.readLine();
                read.close();
                if (usr != null)
                    return usr;

            } catch (Exception e) {

            }
        }
        return "";

    }

    //Method to Check if the User login details are entered correctly

    private boolean checkUserDetails (TextField user, PasswordField pass) {
        String User = user.getText();
        String Pass = pass.getText();
        try{
            BufferedReader read = new BufferedReader(new FileReader(".\\res\\Account.dat"));
            String usr = read.readLine();
            String psw = read.readLine();
            read.close();

            if (User.equals(usr) && Pass.equals(psw))
                return true;

        }catch (Exception e){

        }

            return false;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception{
        window = primaryStage;
        window.setTitle("Modules/Login");


        gridLogin = new GridPane();
        gridLogin.setPadding(new Insets(20, 20, 20, 60));
        gridLogin.setVgap(20);
        gridLogin.setHgap(40);

        gridSignUp = new GridPane();
        gridSignUp.setPadding(new Insets(20, 20, 20, 20));
        gridSignUp.setVgap(20);
        gridSignUp.setHgap(20);

        //login Label

        loginLabel = new Label("Modules/Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        GridPane.setConstraints(loginLabel, 1, 0);

        //User Label

        userLabel = new Label("Username");
        userLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GridPane.setConstraints(userLabel, 0, 1);

        //User Input

        userField = new TextField();
        String usr = rememberedUsername();

        if(usr.equals(null))
            userField.setPromptText("Username");
        else
            userField.setText(usr);

        GridPane.setConstraints(userField, 1, 1);

        //Password Label

        passLabel = new Label("Password");
        passLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GridPane.setConstraints(passLabel, 0, 2);

        //Password Field

        passField = new PasswordField();
        passField.setPromptText("Password");
        GridPane.setConstraints(passField, 1, 2);


        //login Button

        loginButton = new Button("Modules/Login");
        loginButton.setOnAction(e -> {
            if (checkUserDetails(userField, passField)) {
                System.out.println("Username and password are correct!");
                if(rememberUser.isSelected())rememberUsername(userField);
            }

            else
                System.out.println("Username and password are incorrect!");
        });


        //Sign Up Button

        signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> window.setScene(signupScene));

        //CheckBox for remembering username

        rememberUser = new CheckBox("Remember Username");
        if(rememberedUsername().length()>0)
            rememberUser.setSelected(true);
        else
            rememberUser.setSelected(false);
        rememberUser.setOnAction(e -> {
            if (rememberUser.isSelected()) {
                rememberUsername(userField);
            }
        }
        );
        GridPane.setConstraints(rememberUser, 1, 4);

        //login and Signup button in a HBox

        buttons = new HBox();
        buttons.getChildren().addAll(loginButton, signupButton);
        gridLogin.setConstraints(buttons, 1, 3);

        //Use in pane the elements above
        gridLogin.getChildren().addAll(loginLabel, userLabel, userField, passLabel, passField, rememberUser, buttons);

        loginScene= new Scene(gridLogin, 450, 300);
        signupScene = new Scene(gridSignUp, 450, 300);

        //Create account Label

        createAccLabel = new Label("Create Account");
        createAccLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        GridPane.setConstraints(createAccLabel, 1, 0);

        //Create User Label

        createUserLabel = new Label("Username");
        createUserLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GridPane.setConstraints(createUserLabel, 0, 1);

        //Create User Input

        createUserField = new TextField();
        GridPane.setConstraints(createUserField, 1, 1);

        //Create Password Label

        createPassLabel = new Label("Password");
        createPassLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GridPane.setConstraints(createPassLabel, 0, 2);

        //Confirm Password Label

        confirmPassLabel = new Label("Confirm Password");
        confirmPassLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GridPane.setConstraints(confirmPassLabel, 0, 3);

        //Create Password Field

        createPassField = new PasswordField();
        createPassField.setPromptText("Password");
        GridPane.setConstraints(createPassField, 1, 2);

        //Confirm Password Field

        confirmPassField = new PasswordField();
        confirmPassField.setPromptText("Password");
        GridPane.setConstraints(confirmPassField, 1, 3);

        //Send Button

        sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            createAccount(createUserField, createPassField, confirmPassField);
            window.setScene(loginScene);
        });


        gridSignUp.setConstraints(sendButton, 1, 4);


        //GoToLogin Button

        goToLogin = new Button("Go To login");
        goToLogin.setOnAction(e -> window.setScene(loginScene));
        gridSignUp.setConstraints(goToLogin, 0, 4);

        gridSignUp.getChildren().addAll(createAccLabel, createUserLabel, createUserField, createPassLabel, createPassField, confirmPassLabel, confirmPassField, sendButton, goToLogin);



        //Default Scene is loginScene (login) if there is an account created or signupScene (SignUp) if not
        File f = new File(".\\res\\Account.dat");
        if(f.exists())
            window.setScene(loginScene);
        else
            window.setScene(signupScene);

        window.show();

    }

}
