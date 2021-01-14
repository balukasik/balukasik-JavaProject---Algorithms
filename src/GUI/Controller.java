package GUI;

import Data.Dane;
import Data.Droga;
import Data.Pacjent;
import Data.Szpital;
import Jarvis.Jarvis;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


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
    private double mapScale;

    private double mapLeftBorder = Integer.MAX_VALUE;
    private double mapRightBorder = 0;
    private double mapDownBorder = Integer.MAX_VALUE;
    private double mapUpBorder = 0;

    private final int middlePanel = PANEL_SIZE / 2;

    private int animationSpeed = 1000;

    private Queue<Pacjent> patientsQueue = new LinkedList<>();

    @FXML
    private AnchorPane map;

    @FXML
    private Slider animationSpeedSlider;

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

        Szpital[] borderHospitals = Jarvis.convexHull().toArray(Szpital[]::new);
        Double[] borderPoints = new Double[borderHospitals.length * 2];
        int iter = 0;
        for (Szpital szpital : borderHospitals) {
            borderPoints[iter++] = Double.valueOf(convertPointX(szpital.getX()));
            borderPoints[iter++] = Double.valueOf(convertPointY(szpital.getY()));
        }
        Polygon border = new Polygon();
        border.getPoints().addAll(borderPoints);
        border.setFill(Color.rgb(205, 190, 152));
        map.getChildren().add(border);

        for (Szpital szpital : Dane.szpitale) {
            Circle circle = new Circle(convertPointX(szpital.getX()), convertPointY(szpital.getY()), 10);
            circle.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> objectName.setText(szpital.getNazwa()));
            map.getChildren().add(circle);
        }

        for (Droga droga : Dane.drogi) {
            Szpital szpital1 = Dane.szpitale.get(droga.getIdSzpitala1() - 1);
            Szpital szpital2 = Dane.szpitale.get(droga.getIdSzpitala2() - 1);
            Line line = new Line(convertPointX(szpital1.getX()),
                    convertPointY(szpital1.getY()),
                    convertPointX(szpital2.getX()),
                    convertPointY(szpital2.getY()));
            line.setStrokeWidth(3);
            map.getChildren().add(line);
        }

        map.addEventHandler(MouseEvent.MOUSE_CLICKED, this::addPatientOnClick);

    }

    @FXML
    public void openPatientFileClicked(Event e) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        data.readPacjent(file.getAbsolutePath());
    }

    @FXML
    public void addPatientOnClick(Event e) {
        MouseEvent mouseEvent = (MouseEvent) e;
        int x = convertPointXFromScene(mouseEvent.getSceneX() - 34);
        int y = convertPointYFromScene(mouseEvent.getSceneY() - 34);
        Pacjent newPatient = new Pacjent(Dane.pacjenci.size(), x, y);
        Circle circle = new Circle(mouseEvent.getSceneX() - 34, mouseEvent.getSceneY() - 34, 5);
        newPatient.setNode(circle);
        patientsQueue.add(newPatient);
        circle.setFill(Color.RED);
        map.getChildren().add(circle);
    }

    private void calculateScaleMap() {

        for (Szpital szpital : Dane.szpitale) {
            if (szpital.getY() > mapUpBorder) {
                mapUpBorder = szpital.getY();
            }
            if (szpital.getY() < mapDownBorder) {
                mapDownBorder = szpital.getY();
            }
            if (szpital.getX() > mapRightBorder) {
                mapRightBorder = szpital.getX();
            }
            if (szpital.getX() < mapLeftBorder) {
                mapLeftBorder = szpital.getX();
            }
        }

        middleMapX = (mapRightBorder - mapLeftBorder) / 2;
        middleMapY = (mapUpBorder - mapDownBorder) / 2;

        if (mapRightBorder - mapLeftBorder > mapUpBorder - mapDownBorder) {
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
        return (int) (middleMapX + mapLeftBorder - ((middlePanel - x) / mapScale));
    }

    private int convertPointYFromScene(double y) {
        return (int) (middleMapY + mapDownBorder + ((middlePanel - y) / mapScale));
    }

    private void moveTo(Node node, double startX, double startY, int endX, int endY) {
        Path path = new Path();
        path.getElements().add(new MoveTo(startX, startY));
        path.getElements().add(new LineTo(endX, endY));

        PathTransition pathTransition = new PathTransition(Duration.millis(animationSpeed), path, node);
        pathTransition.play();
    }

    public void moveNextPatient() {
        Pacjent pacjent;
        if (patientsQueue.size() != 0) {
            pacjent = patientsQueue.remove();
        } else {
            pacjent = Dane.pacjenci.remove(0);
            Circle circle = new Circle(convertPointX(pacjent.getX()), convertPointY(pacjent.getY()), 5);
            pacjent.setNode(circle);
            circle.setFill(Color.RED);
            map.getChildren().add(circle);
        }
        Path path = new Path();
        path.getElements().add(new MoveTo(((Circle) pacjent.getNode()).getCenterX(), ((Circle) pacjent.getNode()).getCenterY()));
        //Tu dodać szpital w kolejności
        for(Szpital szpital : Dane.szpitale) {
            path.getElements().add(new LineTo(convertPointX(szpital.getX()), convertPointY(szpital.getY())));
        }
        path.getElements().add(new LineTo(convertPointX(100), convertPointY(100)));

        PathTransition pathTransition = new PathTransition(Duration.millis(animationSpeed * (Dane.szpitale.size() + 1 )), path, pacjent.getNode());
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (patientsQueue.size() != 0 || Dane.pacjenci.size() != 0) {
                    moveNextPatient();
                }
            }
        });

        pathTransition.play();
    }

    @FXML
    public void startAnimation(Event e) {
        moveNextPatient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animationSpeedSlider.valueProperty().addListener((observableValue, number, t1) -> animationSpeed = t1.intValue());
    }
}
