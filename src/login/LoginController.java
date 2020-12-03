package login;

import checkers.CheckersApp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    public Button exitButton;

    @FXML
    public ImageView checkerBoardView;

    @FXML
    public Label invalidNameText;

    @FXML
    public Button StartButton;

    @FXML
    public TextField player2Input;

    @FXML
    public TextField player1Input;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File checkerBoardFile = new File("Images/MockUI.png");
        Image checkerBoardImage = new Image(checkerBoardFile.toURI().toString());
        checkerBoardView.setImage(checkerBoardImage);
    }

    public void leaveLoginPage(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void startGame(ActionEvent event) {
        if(checkInvalidNames(player1Input.getText(), player2Input.getText())) {
            invalidNameText.setText("Invalid Names :( !!");
            return;
        }

        Stage s = new Stage();
        try {
            new CheckersApp().start(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkInvalidNames(String name1, String name2) {
        return name1.length() <= 0 || name2.length() <= 0 || name1.equals(name2);
    }
}
