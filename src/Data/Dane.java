package Data;

import GUI.Controller;
import javafx.scene.control.Control;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dane {
	public static ArrayList<Szpital> szpitale = new ArrayList<>();
	public static ArrayList<Droga> drogi = new ArrayList<>();
	public static ArrayList<Pacjent> pacjenci = new ArrayList<>();

	public static void resetDane() {
		szpitale = new ArrayList<>();
		drogi = new ArrayList<>();
		pacjenci = new ArrayList<>();
	}

	public static void resetPacjenci(){
		pacjenci = new ArrayList<>();
	}

	public static int read(String fileName) {
		resetDane();
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
							Controller.showErrorWindow("Zła ilość atrybutów\nLinia: " + lineNum);
							resetDane();
							return -1;
						}
						try {
							szpitale.add(new Szpital(attributes));
						} catch (NumberFormatException e) {
							Controller.showErrorWindow("Złe dane w linii " + lineNum);
							resetDane();
							return -1;
						}
						line = br.readLine();

					} else if (sekcja == 2) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 4) {
							Controller.showErrorWindow("Zła ilość atrybutów\nLinia: " + lineNum);
							resetDane();
							return -1;
						}
						try {
							szpitale.add(new Obiekt(attributes, szpitale.size() + 1));
						} catch (NumberFormatException e) {
							Controller.showErrorWindow("Złe dane w linii " + lineNum);
							resetDane();
							return -1;
						}
						line = br.readLine();

					} else if (sekcja == 3) {
						String[] attributes = line.trim().split("\\s*\\|\\s*");
						if (attributes.length != 4) {
							Controller.showErrorWindow("Zła ilość atrybutów\nLinia: " + lineNum);
							resetDane();
							return -1;
						}
						try {
							drogi.add(new Droga(attributes));
						} catch (NumberFormatException e) {
							Controller.showErrorWindow("Złe dane w linii " + lineNum);
							resetDane();
							return -1;
						}
						line = br.readLine();
					} else if (sekcja == 4) {
						break;
					} else {
						Controller.showErrorWindow("Brak linii rozpoczynającej się znakiem #");
						resetDane();
						return -1;
					}
				} else {
					line = br.readLine();
				}
			}
			if (sekcja != 3) {
				Controller.showErrorWindow("Brak 3 zbiorów danycj (Szpitale, Drogi, Obiekty");
				resetDane();
				return -1;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return 1;
	}

	public static int readPacjent(String fileName) {
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
						if (attributes.length != 3) {
							Controller.showErrorWindow("Zła ilość atrybutów\nLinia: " + lineNum);
							resetPacjenci();
							return -1;
						}
						pacjenci.add(new Pacjent(attributes, lineNum));
						line = br.readLine();

					} else if (sekcja == 2) {
						break;
					} else {
						Controller.showErrorWindow("Brak linii rozpoczynającej się znakiem #");
						resetPacjenci();
						return -1;
					}
				} else {
					line = br.readLine();
				}
			}
			if (sekcja != 1) {
				Controller.showErrorWindow("Brak zbioru danych (Pacjenci)");
				resetPacjenci();
				return -1;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return 1;
	}

	public static void skrzyzowania() {
		try {

			ArrayList<Droga> drogiTmp = drogi;
			for (Droga droga : drogiTmp) {
				for (Droga droga2 : drogiTmp) {
					if (droga.getId() != droga2.getId()) {
						Szpital skrzyzowanie = skrzyzowanie(droga.getIdSzpitala1(), droga.getIdSzpitala2(),
								droga2.getIdSzpitala1(), droga2.getIdSzpitala2());

						if (skrzyzowanie != null) {
							szpitale.add(skrzyzowanie);

							Droga[] drogiZastepcze = podzialDrogi(droga, skrzyzowanie);
							for (Droga d : drogiZastepcze) {
								drogiTmp.add(d);
							}
							drogiTmp.remove(drogiTmp.indexOf(droga));
							drogi = drogiTmp;
							drogiZastepcze = podzialDrogi(droga2, skrzyzowanie);
							for (Droga d : drogiZastepcze) {
								drogiTmp.add(d);
							}
							drogiTmp.remove(drogiTmp.indexOf(droga2));
							break;
						}
					}
				}
			}

			drogi = drogiTmp;
		} catch (ConcurrentModificationException e) {

		}
	}

	private static Droga[] podzialDrogi(Droga droga, Szpital skrzyzowanie) {
		Droga[] drogi = new Droga[2];
		double a = szpitale.get(droga.getIdSzpitala1() - 1).getX() - skrzyzowanie.getX();
		double b = szpitale.get(droga.getIdSzpitala1() - 1).getY() - skrzyzowanie.getY();
		double d1 = Math.sqrt((a * a) + (b * b));
		a = szpitale.get(droga.getIdSzpitala2() - 1).getX() - skrzyzowanie.getX();
		b = szpitale.get(droga.getIdSzpitala2() - 1).getY() - skrzyzowanie.getY();
		double d2 = Math.sqrt((a * a) + (b * b));
		drogi[0] = new Droga(droga.getId(), droga.getIdSzpitala1(), skrzyzowanie.getId(),
				droga.getOdlglosc() * (d1 / (d1 + d2)));
		drogi[1] = new Droga(Dane.drogi.size() + 1, droga.getIdSzpitala2(), skrzyzowanie.getId(),
				droga.getOdlglosc() * (d2 / (d1 + d2)));
		return drogi;
	}

	private static Szpital skrzyzowanie(int a1, int a2, int b1, int b2) {
		double x1 = szpitale.get(a1 - 1).getX();
		double y1 = szpitale.get(a1 - 1).getY();
		double x2 = szpitale.get(a2 - 1).getX();
		double y2 = szpitale.get(a2 - 1).getY();
		double x3 = szpitale.get(b1 - 1).getX();
		double y3 = szpitale.get(b1 - 1).getY();
		double x4 = szpitale.get(b2 - 1).getX();
		double y4 = szpitale.get(b2 - 1).getY();
		double d = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));
		if (d == 0) {
			return null;
		} else {
			double t = (((x1 - x3) * (y3 - y4)) - ((y1 - y3) * (x3 - x4))) / d;
			double u = -(((x1 - x2) * (y1 - y3)) - ((y1 - y2) * (x1 - x3))) / d;
			if (t > 0 && t < 1 && u > 0 && u < 1) {
				double px = x1 + t * (x2 - x1);
				double py = y1 + t * (y2 - y1);
				return new Szpital(szpitale.size() + 1, "skrzyzowanie" + (szpitale.size() + 1), px, py, 0, 0);
			} else {
				return null;
			}
		}
	}

	public static void clearObjects() {
		ArrayList<Szpital> obiektyTmp = new ArrayList<>();
		for (Szpital s : szpitale) {
			if (s instanceof Obiekt) {
				obiektyTmp.add(s);
			}
		}
		szpitale.removeAll(obiektyTmp);
	}

	public void clearAll() {
		szpitale = new ArrayList<>();
		drogi = new ArrayList<>();
		pacjenci = new ArrayList<>();

	}

}
