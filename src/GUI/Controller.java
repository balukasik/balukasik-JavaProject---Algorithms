package GUI;

import Data.Dane;
import Data.Droga;
import Data.Pacjent;
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
import java.util.LinkedList;
import java.util.Queue;
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

    private Queue<Pacjent> patientsQueue = new LinkedList<>();

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
    public void openMapFileClicked(Event e) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        data.read(file.getAbsolutePath());
        calculateScaleMap();
        for(Szpital szpital : Dane.szpitale) {
            Circle circle = new Circle(convertPointX(szpital.getX()), convertPointY(szpital.getY()), 10);
            circle.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> objectName.setText(szpital.getNazwa()));
            map.getChildren().add(circle);
        }
        for(Droga droga : Dane.drogi) {
            Szpital szpital1 = Dane.szpitale.get(droga.getIdSzpitala1() - 1);
            Szpital szpital2 = Dane.szpitale.get(droga.getIdSzpitala2() - 1);
            map.getChildren().add(new Line(convertPointX(szpital1.getX()),
                    convertPointY(szpital1.getY()),
                    convertPointX(szpital2.getX()),
                    convertPointY(szpital2.getY())));
        }
        map.addEventHandler(MouseEvent.MOUSE_CLICKED, this::addPatientOnClick);

    }

    @FXML
    public void openPatientFileClicked(Event e) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        //TODO
    }

    @FXML
    public void addPatientOnClick(Event e) {
        MouseEvent mouseEvent = (MouseEvent) e;
        int x = convertPointXFromScene(mouseEvent.getSceneX() - 34);
        int y = convertPointYFromScene(mouseEvent.getSceneY() - 34);

        patientsQueue.add(new Pacjent(Dane.pacjenci.size(), x, y));
        Circle circle = new Circle(mouseEvent.getSceneX() - 34, mouseEvent.getSceneY() - 34, 5);
        System.out.println(x);
        System.out.println(y);
        circle.setFill(Color.RED);
        map.getChildren().add(circle);
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

    private int convertPointX(double x) {
        return (int) (middlePanel - ((middleMapX - (x - mapLeftBorder)) * mapScale));
    }

    private int convertPointY(double y) {
        return (int) (middlePanel + ((middleMapY - (y - mapDownBorder)) * mapScale));
    }

    private int convertPointXFromScene(double x) {
        return (int)(middleMapX + mapLeftBorder - ((middlePanel - x) / mapScale));
    }

    private int convertPointYFromScene(double y) {
        return (int)(middleMapY + mapDownBorder + ((middlePanel - y) / mapScale));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
