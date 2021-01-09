package Data;

public class Obiekt {

	private int id;
	private String nazwa;
	private int x;
	private int y;

	public Obiekt(int id, String nazwa, int x, int y) {
		this.id = id;
		this.nazwa = nazwa;
		this.x = x;
		this.y = y;
	}

	public Obiekt(String[] data, int lineNum) {
		try {
			this.id = Integer.parseInt(data[0]);
			this.nazwa = data[1];
			this.x = Integer.parseInt(data[2]);
			this.y = Integer.parseInt(data[3]);
		} catch (NumberFormatException e) {
			System.out.println("Zle dane w lini " + lineNum);
			System.exit(0);
		}
	}

	public String getNazwa() {
		return nazwa;
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
}
