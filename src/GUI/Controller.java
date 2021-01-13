package GUI;

import Data.Dane;
import Data.Droga;
import Data.Szpital;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Dane data = new Dane();

    private final int PANEL_SIZE = 830;

    private double middleMapX;
    private double middleMapY;
    private double  mapScale;

    private double mapLeftBorder = Integer.MAX_VALUE;
    private double mapRightBorder = 0;
    private double mapDownBorder = Integer.MAX_VALUE;
    private double mapUpBorder = 0;

    private final int middlePanel = PANEL_SIZE / 2;

    @FXML
    private AnchorPane map;

    @FXML
    private Label objectName;

    public Controller() throws FileNotFoundException {

    }

    @FXML
    public void startClicked(Event e) {
        System.out.println("Start clicked");
    }

    @FXML
    public void openFileClicked(Event e) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        data.read(file.getAbsolutePath());
        calculateScaleMap();
        for(Szpital szpital : Dane.szpitale) {
            Circle circle = new Circle(convertPoint(szpital.getX(), szpital.getY())[0], convertPoint(szpital.getX(), szpital.getY())[1], 10);
            circle.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> objectName.setText(szpital.getNazwa()));
            map.getChildren().add(circle);
        }
        for(Droga droga : Dane.drogi) {
            Szpital szpital1 = Dane.szpitale.get(droga.getIdSzpitala1() - 1);
            Szpital szpital2 = Dane.szpitale.get(droga.getIdSzpitala2() - 1);
            map.getChildren().add(new Line(convertPoint(szpital1.getX(), szpital1.getY())[0],
                    convertPoint(szpital1.getX(), szpital1.getY())[1],
                    convertPoint(szpital2.getX(), szpital2.getY())[0],
                    convertPoint(szpital2.getX(), szpital2.getY())[1]));
        }

    }

    private void calculateScaleMap() {

        for (Szpital szpital : Dane.szpitale) {
            if(szpital.getY() > mapUpBorder) {
                mapUpBorder = szpital.getY();
            }
            if(szpital.getY() < mapDownBorder) {
                mapDownBorder = szpital.getY();
            }
            if(szpital.getX() > mapRightBorder) {
                mapRightBorder = szpital.getX();
            }
            if(szpital.getX() < mapLeftBorder) {
                mapLeftBorder = szpital.getX();
            }
        }

        middleMapX = (mapRightBorder - mapLeftBorder) / 2;
        middleMapY = (mapUpBorder - mapDownBorder) / 2;

        if(mapRightBorder - mapLeftBorder > mapUpBorder - mapDownBorder) {
            mapScale = (PANEL_SIZE * 1.0) / (mapRightBorder - mapLeftBorder);
        } else {
            mapScale = (PANEL_SIZE * 1.0) / (mapUpBorder - mapDownBorder);
        }
    }

    private int[] convertPoint(double x, double y) {
        int[] position = new int[2];

        position[0] = (int) (middlePanel - ((middleMapX - (x - mapLeftBorder)) * mapScale));
        position[1] = (int) (middlePanel + ((middleMapY - (y - mapDownBorder)) * mapScale));

        return position;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
