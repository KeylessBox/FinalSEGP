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
import sql.LoginDB;
import sql.Account;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class SignInController {

    private boolean remember;

    @FXML
    private Pane SignUp;

    @FXML
    private Label ErrorMessage;
    @FXML
    private Label ErrMessage;

    @FXML
    private Pane SignIn;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField email;

    @FXML
    private Button signUp;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private PasswordField pw;

    @FXML
    private TextField username;

    @FXML
    private Pane movingpart;

    public String user = "";

    @FXML
    private CheckBox rember;

    @FXML
    public void initialize() {
        try (FileReader fileReader = new FileReader(new File("res/users/Account.dat"));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String user = bufferedReader.readLine();
            String password = bufferedReader.readLine();
            if (user != null && !user.equals("")) {
                username.setText(user);
            }
            if (password != null && !password.equals("")) {
                pw.setText(password);
                rember.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void changeToSignUp(ActionEvent event) {
        SignIn.setVisible(true);
        SignUp.setVisible(false);
        movingpart.setPrefWidth(49);
        movingpart.setLayoutX(47);
    }

    @FXML
    void changeToSignIn(ActionEvent event) {
        SignIn.setVisible(false);
        SignUp.setVisible(true);
        movingpart.setPrefWidth(125);
        movingpart.setLayoutX(151);
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {

        if (name.getText().length() < 6) {
            ErrMessage.setText("First Name is too short");
            if (name.getText().length() == 0) {
                ErrMessage.setText("First Name field is empty");
            }
            ErrMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);
        } else if (surname.getText().length() < 6) {
            ErrMessage.setText("Last Name is too short");
            if (surname.getText().length() == 0) {
                ErrMessage.setText("Last Name field is empty");
            }
            ErrMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);
        } else if (email.getText().length() < 6) {
            ErrMessage.setText("Username is too short");
            if (email.getText().length() == 0) {
                ErrMessage.setText("Username field is empty");
            }
            ErrMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);

        } else if (pass.getText().length() < 6) {
            ErrMessage.setText("Password is too short");
            if (pass.getText().length() == 0) {
                ErrMessage.setText("Password field is empty");
            }
            ErrMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);
        } else {
            Account.createAccount(name.getText(), surname.getText(), email.getText(), pass.getText(), "users");
            username.setText(email.getText());
            pw.setText("");
            rember.setSelected(false);
            SignIn.setVisible(true);
            SignUp.setVisible(false);
            movingpart.setPrefWidth(49);
            movingpart.setLayoutX(47);
        }
    }

    @FXML
    void forget() {
        ErrorMessage.setText("Your users details send to email");
        ErrorMessage.setVisible(true);
        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                if (i < 100) {

                } else {
                    ErrorMessage.setVisible(false);
                    this.cancel();
                }
                i++;
            }

        }, 2000, 25);
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {

        if (username.getText().length() < 6) {
            ErrorMessage.setText("Username is too short");
            if (username.getText().length() == 0) {
                ErrorMessage.setText("Username field is empty");
            }
            ErrorMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrorMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);

        } else if (pw.getText().length() < 6) {
            ErrorMessage.setText("Password is too short");
            if (pw.getText().length() == 0) {
                ErrorMessage.setText("Password field is empty");
            }
            ErrorMessage.setVisible(true);
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {

                    } else {
                        ErrorMessage.setVisible(false);
                        this.cancel();
                    }
                    i++;
                }

            }, 2000, 25);

        } else {
            try {
                if (!LoginDB.checkUserDetails(username.getText(), pw.getText())) {
                    /**
                     * If the account details introduced are wrong
                     * Alert Box is shown
                     */
                    ErrorMessage.setText("Login or password are incorrect!");
                    ErrorMessage.setVisible(true);
                    Timer animTimer = new Timer();
                    animTimer.scheduleAtFixedRate(new TimerTask() {
                        int i = 0;

                        @Override
                        public void run() {
                            if (i < 100) {

                            } else {
                                ErrorMessage.setVisible(false);
                                this.cancel();
                            }
                            i++;
                        }

                    }, 2000, 25);

                } else {
                    /**
                     * If the account details entered are correct,
                     * Program moves to the main Window
                     * It passes the account details to a file (that will be soon deleted).
                     */

                    user = username.getText();
                    //PrintWriter writer = new PrintWriter(new File("src/res/users/Account.dat"));
                    File dir = new File(System.getProperty("user.dir"), "./res/users");
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    PrintWriter writer = new PrintWriter(new File("res/users/Account.dat"));
                    writer.write(user);
                    if (rember.isSelected()) {
                        writer.write("\n" + pw.getText());
                    }
                    writer.close();
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
            }catch (Exception e){
                e.printStackTrace();
                ErrorMessage.setText("Database connection failed!");
                ErrorMessage.setVisible(true);
                Timer animTimer = new Timer();
                animTimer.scheduleAtFixedRate(new TimerTask() {
                    int i = 0;

                    @Override
                    public void run() {
                        if (i < 100) {

                        } else {
                            ErrorMessage.setVisible(false);
                            this.cancel();
                        }
                        i++;
                    }

                }, 2000, 25);
            }
        }
    }

}
