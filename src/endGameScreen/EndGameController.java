package endGameScreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    @FXML
    public Button returnButton;

    @FXML
    public ImageView checkerBoardView;

    @FXML
    public Label winnerText;

    @FXML
    public Label numberOfWins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File checkerBoardFile = new File("Images/MockUI.png");
        Image checkerBoardImage = new Image(checkerBoardFile.toURI().toString());
        checkerBoardView.setImage(checkerBoardImage);
    }

    public void returnButton(ActionEvent event){
        Stage primaryStage = (Stage)returnButton.getScene().getWindow();
        primaryStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
