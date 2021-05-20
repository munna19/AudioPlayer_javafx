package AudioPlayer;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
     #####     #####     #####
     @author: Abdul Al Mamun (Munna)
     #####     #####     #####
*/

public class Controller implements Initializable {

    private int[] speeds ={25,50,75,100,125,150,175,200};
    private Media media;
    private MediaPlayer mediaPlayer;
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;


    @FXML
    private Label label_id;

    @FXML
    private Slider timer_slider_id;

    @FXML
    private ComboBox<String> speed_box_id;
    //private ComboBox<?> speed_box_id;

    @FXML
    private Button previous_button_id;

    @FXML
    private Button play_button_id;

    @FXML
    private Button next_button_id;

    @FXML
    void Add_music_Action(ActionEvent event) {
        try {
            FileChooser fc = new FileChooser();
            File selectedFile = fc.showOpenDialog(null);
            media = new Media(selectedFile.toURI().toString());

            //all operation relies
            if(mediaPlayer != null){
                mediaPlayer.dispose();
            }

            mediaPlayer = new MediaPlayer(media);
            //System.out.println(media);
            //System.out.println(mediaPlayer);



        }
        catch(Exception e){
            e.printStackTrace();
        }

        //time slider
        mediaPlayer.setOnReady(()->{
            //this function work when the player gets ready
            timer_slider_id.setMin(0);
            timer_slider_id.setMax(mediaPlayer.getMedia().getDuration().toMinutes());
            timer_slider_id.setValue(0);

            try {
                play_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        //listener on player
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

                Duration durationD = mediaPlayer.getCurrentTime();

                //slider_id.setValue(durationD.toSeconds());
                timer_slider_id.setValue(durationD.toMinutes());

            }

        });

        // slider position change
        timer_slider_id.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(timer_slider_id.isPressed()){
                    double slider_value = timer_slider_id.getValue();
                    //player.seek(new Duration(slider_value*1000));
                    mediaPlayer.seek(new Duration(slider_value * 60 * 1000)); // for minute
                }
            }
        });

        //speed set
        //Speed_Action(null);
        //mediaPlayer.setRate(0);
        speed_box_id.setValue(null);
    }


    @FXML
    void PlayButton_Action(ActionEvent event) {
        Speed_Action(null);
        //mediaPlayer.play();
        try {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if(status == MediaPlayer.Status.PLAYING){
                mediaPlayer.pause();
                play_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            }
            else {
                mediaPlayer.play();
                play_button_id.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pause.png"))));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void NextButtonAction(ActionEvent event) {
        //for duration forward
        double d = mediaPlayer.getCurrentTime().toSeconds();
        d = d + 10;
        mediaPlayer.seek(new Duration(d * 1000));
    }

    @FXML
    void PreviousButtonAction(ActionEvent event) {
        //for duration backward
        double d = mediaPlayer.getCurrentTime().toSeconds();
        d = d - 10;
        mediaPlayer.seek(new Duration(d * 1000));
    }

    @FXML
    void Reset_Action(ActionEvent event) {
        mediaPlayer.seek(Duration.seconds(0));
    }

    @FXML
    void Speed_Action(ActionEvent event) {
        if(speed_box_id.getValue() ==null){
            mediaPlayer.setRate(1);
        }
        else {
            //mediaPlayer.setRate(Integer.parseInt(speed_box_id.getValue()) * 0.01);
            mediaPlayer.setRate(Integer.parseInt(speed_box_id.getValue().substring(0,speed_box_id.getValue().length()-1)) * 0.01);
        }
    }

    @FXML
    void Exit_Action(ActionEvent event) {
        Platform.exit();
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

        //songs = new ArrayList<File>();
        for(int i=0; i< speeds.length; i++){
            speed_box_id.getItems().add(Integer.toString(speeds[i]) + "%");
        }
        speed_box_id.setOnAction(this::Speed_Action);
    }
}
