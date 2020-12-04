package endGameScreen;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
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

    private String winner;
    private String loser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File checkerBoardFile = new File("Images/MockUI.png");
        Image checkerBoardImage = new Image(checkerBoardFile.toURI().toString());
        checkerBoardView.setImage(checkerBoardImage);
    }

    public void init(GameResult gameResult) {
        this.winner = gameResult.getWinner();
        this.loser = gameResult.getLoser();
        getData();
        numberOfWins.setText(winner);

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


    public FinalInfo getData() {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        try {
            String JSON = mapper.writeValueAsString(new GameResult(winner, loser));
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:9999" + "/" + winner + "/" + loser))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            FinalInfo listCar = mapper.readValue(response.body(), FinalInfo.class);

            return listCar;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new FinalInfo(winner, -1);
    }
}
