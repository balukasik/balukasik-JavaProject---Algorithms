
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dane {
	public static List<Szpital> szpitale = new ArrayList<>();
	public static List<Obiekt> obiekty = new ArrayList<>();
	public static List<Droga> drogi = new ArrayList<>();
	public static List<Pacjent> pacjenci = new ArrayList<>();

	public void read(String fileName) {
		int sekcja = 0;
		int lineNum = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
			String line = br.readLine();
			while (line != null) {
				lineNum++;
				if (line.length() > 0) {
					if (line.charAt(0) == '#') {
						sekcja++;
						line = br.readLine();
					} else if (sekcja == 1) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 6) {
							System.out.println("zla ilosc atrybotow \nLinia: " + lineNum);
							System.exit(0);
						}
						szpitale.add(new Szpital(attributes, lineNum));
						line = br.readLine();

					} else if (sekcja == 2) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 4) {
							System.out.println("zla ilosc atrybotow \nLinia: " + lineNum);
							System.exit(0);
						}
						obiekty.add(new Obiekt(attributes, lineNum));
						line = br.readLine();

					} else if (sekcja == 3) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 4) {
							System.out.println("zla ilosc atrybotow \nLinia: " + lineNum);
							System.exit(0);
						}
						drogi.add(new Droga(attributes, lineNum));
						line = br.readLine();
					} else if (sekcja == 4) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 3) {
							System.out.println("zla ilosc atrybotow \nLinia: " + lineNum);
							System.exit(0);
						}
						pacjenci.add(new Pacjent(attributes, lineNum));
						line = br.readLine();

					} else if (sekcja == 5) {
						break;
					} else {
						System.out.println("brak lini rozpoczynajacej sie zankiem #");
						System.exit(0);
					}
				} else {
					line = br.readLine();
				}
			}
			if (sekcja != 4) {
				System.out.println("brak 4 zbiorow danych");
				System.exit(0);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
