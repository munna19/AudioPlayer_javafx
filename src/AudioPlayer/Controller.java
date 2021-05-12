package AudioPlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/*
     #####     #####     #####
     @author: Abdul Al Mamun (Munna)
     #####     #####     #####
*/

public class Controller implements Initializable {

    @FXML
    private Button previous_button_id;

    @FXML
    private Button play_button_id;

    @FXML
    private Button next_button_id;


    @FXML
    void PlayButton_Action(ActionEvent event) {

    }

    @FXML
    void NextButtonAction(ActionEvent event) {

    }

    @FXML
    void PreviousButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            play_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            previous_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/previous.png"))));
            next_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/next.png"))));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
