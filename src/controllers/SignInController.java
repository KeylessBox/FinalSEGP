package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sql.Account;
import sql.LoginDB;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Aleksandr Stserbatski on 12/3/2017.
 * Controller for Users Admin Pane
 */
public class SignInController {

    //fxml elements:
    @FXML
    private Pane signUpPane;
    @FXML
    private Pane signInPane;
    @FXML
    private Pane movingPart;
    @FXML
    private Label errorMessage;
    @FXML
    private Label errorMessage2;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button signUp;
    @FXML
    private CheckBox remember;

    public String user = "";

    /**
     * Intilialize the SignIn controller
     */
    @FXML
    public void initialize() {
        try (FileReader fileReader = new FileReader(new File("res/users/Account.dat"));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String user = bufferedReader.readLine();
            String password = bufferedReader.readLine();
            if (user != null && !user.equals("")) {
                usernameField.setText(user);
            }
            if (password != null && !password.equals("")) {
                passwordField2.setText(password);
                remember.setSelected(true);
            }
        } catch (IOException e) {
            System.out.println("Problem with signin initialize");
        }
    }

    /**
     * Switch to SignUp Tab
     * @param event
     */
    @FXML
    void changeToSignUp(ActionEvent event) {
        signInPane.setVisible(true);
        signUpPane.setVisible(false);
        movingPart.setPrefWidth(49);
        movingPart.setLayoutX(47);
    }

    /**
     * Switch to SignIn Tab
     * @param event
     */
    @FXML
    void changeToSignIn(ActionEvent event) {
        signInPane.setVisible(false);
        signUpPane.setVisible(true);
        movingPart.setPrefWidth(125);
        movingPart.setLayoutX(151);
    }

    /**
     * Create new user account
     * Includes input validations
     * @param event
     * @throws IOException
     */
    @FXML
    void signUp(ActionEvent event) throws IOException {

        // Validations
        if (nameField.getText().length() < 6) {

            if (nameField.getText().length() == 0) {
                showErrorMessage2("First Name field is empty");
            } else {
                showErrorMessage2("First Name is too short");
            }

        } else if (surnameField.getText().length() < 6) {

            if (surnameField.getText().length() == 0) {
                showErrorMessage2("Last Name field is empty");
            } else {
                showErrorMessage2("Last Name is too short");
            }

        } else if (emailField.getText().length() < 6) {

            if (emailField.getText().length() == 0) {
                showErrorMessage2("Username field is empty");
            } else {
                showErrorMessage2("Username is too short");
            }

        } else if (passwordField.getText().length() < 6) {

            if (passwordField.getText().length() == 0) {
                showErrorMessage2("Password field is empty");
            } else {
                showErrorMessage2("Password is too short");
            }

        } else {
            /**
             * Only if all data input is correct create a new account
             */
            Account.createAccount(nameField.getText(), surnameField.getText(), emailField.getText(), passwordField.getText(), "users");
            usernameField.setText(emailField.getText());
            passwordField2.setText("");
            remember.setSelected(false);
            signInPane.setVisible(true);
            signUpPane.setVisible(false);
            movingPart.setPrefWidth(49);
            movingPart.setLayoutX(47);
        }
    }

    /**
     * Forget the user details
     */
    @FXML
    void forget() {
        showErrorMessage("Your users details send to emailField");
    }

    /**
     * Sign In controller
     * Includes input validations
     * Saving user option
     * @param event
     * @throws IOException
     */
    @FXML
    void signIn(ActionEvent event) throws IOException {

        // validations for signIn
        if (usernameField.getText().length() < 6) {

            if (usernameField.getText().length() == 0) {
                showErrorMessage("Username field is empty");
            } else {
                showErrorMessage("Username is too short");
            }

        } else if (passwordField2.getText().length() < 6) {

            if (passwordField2.getText().length() == 0) {
                showErrorMessage("Password field is empty");
            } else {
                showErrorMessage("Password is too short");
            }

        } else {
            try {
                if (!LoginDB.checkUserDetails(usernameField.getText(), passwordField2.getText())) {
                    /**
                     * If the account details introduced are wrong
                     * Alert Box is shown
                     */
                    showErrorMessage("Login or password are incorrect!");

                } else {
                    /**
                     * If the account details entered are correct,
                     * Program moves to the main Window
                     * It passes the account details to a file (that will be soon deleted).
                     */
                    user = usernameField.getText();

                    File directory = new File(System.getProperty("user.dir"), "./res/users");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    PrintWriter writer = new PrintWriter(new File("res/users/Account.dat"));
                    writer.write(user);
                    if (remember.isSelected()) {
                        writer.write("\n" + passwordField2.getText());
                    }
                    writer.close();

                    File priv = new File(System.getProperty("user.dir"), "./res/tmp");
                    if (!priv.exists()) {
                        priv.mkdirs();
                    }

                    PrintWriter write = new PrintWriter(new File ("res/tmp/sf1332f1ewf"));
                    if (LoginDB.checkPrivilege(user))
                        write.write("admin");
                    else write.write("user");

                    write.close();


                    /**
                     * The connection to the main app
                     */
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));/* Exception */
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setWidth(1815);
                    stage.setHeight(1040);
                    stage.centerOnScreen();
                    stage.setResizable(true);
                    stage.show();
                }
            } catch (Exception e) {
                System.out.println("Database connection failed");
                showErrorMessage("Database connection failed!");
            }
        }
    }

    /**
     * Show Error Message for several seconds
     * @param message
     */
    public void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i < 100) {
                } else {
                    errorMessage.setVisible(false);
                    this.cancel();
                }
                i++;
            }
        }, 2000, 25);
    }

    /**
     * Show Error Message 2 for several seconds
     * @param message
     */
    public void showErrorMessage2(String message) {
        errorMessage2.setText(message);
        errorMessage2.setVisible(true);
        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i < 100) {

                } else {
                    errorMessage2.setVisible(false);
                    this.cancel();
                }
                i++;
            }
        }, 2000, 25);
    }

}
