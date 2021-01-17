package GUI;

import Data.Dane;
import Data.Droga;
import Data.Pacjent;
import Data.Szpital;
import Dijkstra.Dijkstra;
import IsInside.IsInside;
import Jarvis.Jarvis;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

	private Szpital[] borderHospitals;
	@FXML
	private AnchorPane map;

	@FXML
	private Slider animationSpeedSlider;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TextFlow logsTextFlow;

	@FXML
	private TextFlow hospitalInfoWindow;

	@FXML
	private Text hospitalNameWindow;

	@FXML
	private Text hospitalNumberOfBeds;

	@FXML
	private Text hospitalNumberOfBedsLeft;

	@FXML
	private Text numberNotAcceptedPatients;

	@FXML
	private TextFlow objectInfoWindow;

	@FXML
	private Text objectNameWindow;

	private final Font logHeaderFont = Font.font("System", FontWeight.BOLD, 18);
	private final Font logHospitalFont = Font.font("System", 16);
	private final Color textColor = Color.valueOf("#948C75");
	private final Color notAcceptedPatientColor = Color.valueOf("#990000");
	private final Color acceptedPatientColor = Color.valueOf("#009900");

	public Controller() {

	}

	@FXML
	public void openMapFileClicked(Event e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\data"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null) {
			return;
		}
		if (Dane.read(file.getAbsolutePath()) == -1) {
			return;
		}
		map.getChildren().removeAll(map.getChildren());
		map.getChildren().addAll(hospitalInfoWindow, objectInfoWindow);
		logsTextFlow.getChildren().removeAll();
		calculateScaleMap();

		borderHospitals = Jarvis.convexHull().toArray(Szpital[]::new);
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

		for (Droga droga : Dane.drogi) {
			Szpital szpital1 = Dane.szpitale.get(droga.getIdSzpitala1() - 1);
			Szpital szpital2 = Dane.szpitale.get(droga.getIdSzpitala2() - 1);
			Line line = new Line(convertPointX(szpital1.getX()), convertPointY(szpital1.getY()),
					convertPointX(szpital2.getX()), convertPointY(szpital2.getY()));
			line.setStrokeWidth(3);
			map.getChildren().add(line);
		}

		for (Szpital szpital : Dane.szpitale) {
			Circle circle = new Circle(convertPointX(szpital.getX()), convertPointY(szpital.getY()), 10);
			circle.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
				PauseTransition hideWindow = new PauseTransition(Duration.seconds(2));

				if(szpital.getLozka() > 0) {
					hospitalInfoWindow.toFront();
					hospitalNameWindow.setText(szpital.getNazwa());
					hospitalNumberOfBeds.setText("Liczba łóżek: " + szpital.getLozka());
					hospitalNumberOfBedsLeft.setText("Liczba wolnych łóżek: " + szpital.getWolne_lozka());
					numberNotAcceptedPatients.setText("Liczba oczekujących pacjentów: " + szpital.getLiczbaOczekujacychPacjentów());
					hospitalInfoWindow.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent1 -> hideWindow.playFromStart());
					hideWindow.setOnFinished(e2 -> hospitalInfoWindow.setVisible(false));
					setPopupWindowPosition(hospitalInfoWindow, circle.getCenterX(), circle.getCenterY());
					hideWindow.play();
					hospitalInfoWindow.setVisible(true);
				} else {
					objectInfoWindow.toFront();
					objectNameWindow.setText(szpital.getNazwa());
					objectInfoWindow.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent1 -> hideWindow.playFromStart());
					hideWindow.setOnFinished(e2 -> objectInfoWindow.setVisible(false));
					setPopupWindowPosition(objectInfoWindow, circle.getCenterX(), circle.getCenterY());
					hideWindow.play();
					objectInfoWindow.setVisible(true);
				}
			});
			map.getChildren().add(circle);
		}
		Dane.clearObjects();
		Dane.skrzyzowania();

		map.addEventHandler(MouseEvent.MOUSE_CLICKED, this::addPatientOnClick);

	}

	public void setPopupWindowPosition(TextFlow textFlow, double windowPositionX, double windowPositionY) {
		if(windowPositionX < middlePanel && windowPositionY > middlePanel) {
			textFlow.setLayoutX(windowPositionX);
			textFlow.setLayoutY(windowPositionY - textFlow.getHeight());
		} else if (windowPositionX > middlePanel && windowPositionY < middlePanel) {
			textFlow.setLayoutX(windowPositionX - textFlow.getWidth());
			textFlow.setLayoutY(windowPositionY);
		} else if (windowPositionX > middlePanel && windowPositionY > middlePanel) {
			textFlow.setLayoutX(windowPositionX - textFlow.getWidth());
			textFlow.setLayoutY(windowPositionY - textFlow.getHeight());
		} else {
			textFlow.setLayoutX(windowPositionX);
			textFlow.setLayoutY(windowPositionY);
		}
	}

	@FXML
	public void openPatientFileClicked(Event e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\data"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (Dane.readPacjent(file.getAbsolutePath()) == -1) {
			return;
		}
	}

	@FXML
	public void addPatientOnClick(Event e) {
		MouseEvent mouseEvent = (MouseEvent) e;
		int x = convertPointXFromScene(mouseEvent.getSceneX() - 34);
		int y = convertPointYFromScene(mouseEvent.getSceneY() - 34);
		Pacjent newPatient = new Pacjent(Dane.getPatientNewId(), x, y, "GUI");
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
		if (IsInside.isInside(borderHospitals, pacjent)) {
			Path path = new Path();
			path.getElements().add(
					new MoveTo(((Circle) pacjent.getNode()).getCenterX(), ((Circle) pacjent.getNode()).getCenterY()));
			int startId = Jarvis.findNearest(pacjent).getId();
			Dijkstra d = new Dijkstra(Dane.szpitale);
			int[] drogaPacjenta = d.drogaPacjenta(startId);
			Text patientLogHeader = new Text("Pacjent " + pacjent.getId() + " z " + pacjent.getFrom() + ":\n");
			patientLogHeader.setFont(logHeaderFont);
			patientLogHeader.setFill(textColor);
			logsTextFlow.getChildren().add(patientLogHeader);
			for (int i : drogaPacjenta) {
				Szpital szpital = Dane.szpitale.get(i - 1);
				Text hospitalText = new Text("\t" + szpital.getNazwa() + "\n");
				hospitalText.setFont(logHospitalFont);
				hospitalText.setFill(textColor);
				logsTextFlow.getChildren().add(hospitalText);
				path.getElements().add(new LineTo(convertPointX(szpital.getX()), convertPointY(szpital.getY())));
			}
			Text patientSummaryText = new Text();
			patientSummaryText.setFont(logHospitalFont);
			if (Dane.szpitale.get(drogaPacjenta[drogaPacjenta.length - 1] - 1).getWolne_lozka() > 0) {
				patientSummaryText.setText("\tPacjent przyjęty\n");
				patientSummaryText.setFill(acceptedPatientColor);
			} else {
				Dane.szpitale.get(drogaPacjenta[drogaPacjenta.length - 1] - 1).increaseLiczbaOczekujacychPacjentow();
				patientSummaryText.setText("\tBrak miejsc, pacjent nieprzyjęty\n");
				patientSummaryText.setFill(notAcceptedPatientColor);
			}
			logsTextFlow.getChildren().add(patientSummaryText);
			PathTransition pathTransition = new PathTransition(Duration.millis(animationSpeed * drogaPacjenta.length),
					path, pacjent.getNode());
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					Dane.szpitale.get(drogaPacjenta[drogaPacjenta.length - 1] - 1).decreaseWolneMiejsca();
					if (patientsQueue.size() != 0 || Dane.pacjenci.size() != 0) {
						moveNextPatient();
					}
				}
			});

			pathTransition.play();
		} else {
			Text abroadPatientLogHeader = new Text("Pacjent " + pacjent.getId() + " z " + pacjent.getFrom() + " poza granicami kraju\n");
			abroadPatientLogHeader.setFont(logHeaderFont);
			abroadPatientLogHeader.setFill(notAcceptedPatientColor);
			logsTextFlow.getChildren().add(abroadPatientLogHeader);
			if (patientsQueue.size() != 0 || Dane.pacjenci.size() != 0) {
				moveNextPatient();
			}
		}
	}

	public static void showErrorWindow(String errorText) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Okno błędu");
		alert.setHeaderText(null);
		alert.setContentText(errorText);
		alert.showAndWait();
	}

	@FXML
	public void startAnimation(Event e) {
		if (patientsQueue.size() != 0 || Dane.pacjenci.size() != 0) {
			moveNextPatient();
		} else {
			showErrorWindow("Proszę dodać pacjentów");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		animationSpeedSlider.valueProperty()
				.addListener((observableValue, number, t1) -> animationSpeed = t1.intValue());
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(logsTextFlow);
		scrollPane.vvalueProperty().bind(logsTextFlow.heightProperty());
	}
}
