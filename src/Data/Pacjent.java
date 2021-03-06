package Data;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class Pacjent {

	private int id;
	private int x;
	private int y;
    private Node node;

	public Pacjent(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public Pacjent(String[] data, int lineNum) {
		try {
			this.id = Integer.parseInt(data[0]);
			this.x = Integer.parseInt(data[1]);
			this.y = Integer.parseInt(data[2]);
		} catch (NumberFormatException e) {
			System.out.println("Zle dane w lini " + lineNum);
			System.exit(0);
		}
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
}
