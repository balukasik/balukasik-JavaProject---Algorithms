package Data;
public class Szpital {

	private int id;
	private String nazwa;
	private int x;
	private int y;
	private int lozka;
	private int wolneMiejsca;

	public Szpital(int id, String nazwa, int x, int y, int lozka, int wolneMiejsca) {
		this.id = id;
		this.nazwa = nazwa;
		this.x = x;
		this.y = y;
		this.lozka = lozka;
		this.wolneMiejsca = wolneMiejsca;
	}

	public Szpital(String[] data, int lineNum) {
		try {
			this.id = Integer.parseInt(data[0]);
			this.nazwa = data[1];
			this.x = Integer.parseInt(data[2]);
			this.y = Integer.parseInt(data[3]);
			this.lozka = Integer.parseInt(data[4]);
			this.wolneMiejsca = Integer.parseInt(data[5]);
		} catch (NumberFormatException e) {
			System.out.println("Zle dane w lini " + lineNum);
			System.exit(0);
		}
	}

	public Szpital(String[] data, int lineNum,int id) {
		try {
			this.id = id;
			this.nazwa = data[1];
			this.x = Integer.parseInt(data[2]);
			this.y = Integer.parseInt(data[3]);
			this.lozka = 0;
			this.wolneMiejsca = 0;
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

	public int getLozka() {
		return lozka;
	}

	public int getWolne_lozka() {
		return wolneMiejsca;
	}

	public void setWolne_lozka(int wolneMiejsca) {
		this.wolneMiejsca = wolneMiejsca;
	}

	public int getId() {
		return id;
	}
}
