package Data;
public class Droga {

	private int id;
	private int idSzpitala1;
	private int idSzpitala2;
	private int odlglosc;

	public Droga(int id, int idSzpitala1, int idSzpitala2, int odlglosc) {
		this.id = id;
		this.idSzpitala1 = idSzpitala1;
		this.idSzpitala2 = idSzpitala2;
		this.odlglosc = odlglosc;
	}

	public Droga(String[] data, int lineNum) {
		try {
			this.id = Integer.parseInt(data[0]);
			this.idSzpitala1 = Integer.parseInt(data[1]);
			this.idSzpitala2 = Integer.parseInt(data[2]);
			this.odlglosc = Integer.parseInt(data[3]);
		} catch (NumberFormatException e) {
			System.out.println("Zle dane w lini " + lineNum);
			System.exit(0);
		}
	}

	public int getIdSzpitala1() {
		return idSzpitala1;
	}

	public int getIdSzpitala2() {
		return idSzpitala2;
	}

	public int getOdlglosc() {
		return odlglosc;
	}

	public int getId() {
		return id;
	}

}
