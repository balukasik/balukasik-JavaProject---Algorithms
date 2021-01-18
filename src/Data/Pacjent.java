package Data;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.regex.Pattern;

public class Pacjent {

    private int id;
    private int x;
    private int y;
    private Node node;
    private String from;

    public Pacjent(int id, int x, int y, String from) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.from = from;
    }

    public Pacjent(String[] data, String from) throws NumberFormatException {
        this.id = Integer.parseInt(data[0]);
        this.x = Integer.parseInt(data[1]);
        this.y = Integer.parseInt(data[2]);
        this.from = splitFrom(from);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public String getFrom() {
        return from;
    }

    private String splitFrom(String from) {
        String[] splitted = from.split(Pattern.quote("\\"));
        return splitted[splitted.length - 1];
    }
}
