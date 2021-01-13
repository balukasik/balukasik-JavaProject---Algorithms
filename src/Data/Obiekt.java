package Data;

public class Obiekt extends Szpital{

	public Obiekt(int id, String nazwa, int x, int y) {
		super(id,nazwa,x,y,0,0);
	}

	public Obiekt(Obiekt obiekt) {
		super(obiekt);
	}

	public Obiekt(String[] data, int lineNum,int id) {
		super(data,lineNum,id);
	}

}