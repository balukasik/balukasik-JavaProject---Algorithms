package Jimp_Proj_1;


import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class Dane {
    public static List<Producent> producenci= new ArrayList<>();
    public static List<Apteka> apteki= new ArrayList<>();
    public static List<Polaczenie> polaczenia= new ArrayList<>();
    public static int[][] result;
    public static double sumaKoszt;



    public static void read(String fileName){
        int sekcja=0;
        int lineNum=0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            String line = br.readLine();
            while (line != null) {
                lineNum++;
                if(line.length() > 0) {
                    if (line.charAt(0) == '#') {
                        sekcja++;
                        line = br.readLine();
                    } else if (sekcja == 1) {
                        String[] attributes = line.trim().split("\\s*\\|\\s*");
                        if(attributes.length!=3){
                            System.out.println("Zła ilość atrybutów producenci \nLinia: "+ lineNum);
                            System.exit(0);
                        }
                        Producent producent = createProducent(attributes);
                        if(producent==null){
                            System.out.println("Złe dane w lini " + lineNum);
                            System.exit(0);
                        }
                        producenci.add(producent);
                        line = br.readLine();

                    } else if (sekcja == 2) {
                        String[] attributes = line.trim().split("\\s*\\|\\s*");
                        if(attributes.length!=3){
                            System.out.println("Zła ilość atrybutów apteki \nLinia: "+ lineNum);
                            System.exit(0);
                        }
                        Apteka apteka = createApteka(attributes);
                        if(apteka==null){
                            System.out.println("Złe dane w lini " + lineNum);
                            System.exit(0);
                        }
                        apteki.add(apteka);
                        line = br.readLine();

                    } else if (sekcja == 3) {
                        String[] attributes = line.trim().split("\\s*\\|\\s*");
                        if(attributes.length!=4){
                            System.out.println("Zła ilość atrybutów połączenia \nLinia: "+ lineNum);
                            System.exit(0);
                        }
                        Polaczenie polaczenie = createPolaczenie(attributes);
                        if(polaczenie==null){
                            System.out.println("Złe dane w lini " + lineNum);
                            System.exit(0);
                        }
                        polaczenia.add(polaczenie);
                        line = br.readLine();
                    } else if (sekcja == 4) {
                        break;
                    } else{
                        System.out.println("brak lini rozpoczynajacej sie zankiem #");
                        System.exit(0);
                    }
                }
                else{
                    line = br.readLine();
                }
            }
            if(sekcja!=3){
                System.out.println("brak 3 zbiorów danych");
                System.exit(0);
            }
            if(apteki.size()*producenci.size() != polaczenia.size()){
                System.out.println("Ilość połączeń aptek i prodeucentów się nie zgadza");
                System.exit(0);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void doAlgorytm() throws Exception {
        int[] produkcja=new int[producenci.size()];
        int[] zapotrzebowanie=new int[apteki.size()];
        int[][] limity=new int[producenci.size()][apteki.size()];
        double[][] koszt=new double[producenci.size()][apteki.size()];
        for(int i=0;i<producenci.size();i++){
            produkcja[i]=producenci.get(i).getProdukcja();
        }
        for(int i=0;i<apteki.size();i++){
            zapotrzebowanie[i]=apteki.get(i).getZapotrzebowanie();
        }
        int licznik=0;
        for(int i=0;i<producenci.size();i++){
            for(int j=0;j<apteki.size();j++) {
                koszt[i][j] = polaczenia.get(licznik).getKoszt();
                limity[i][j]= polaczenia.get(licznik).getMax_dost();
                licznik++;
            }
        }
        Algorytm algorytm=new Algorytm(produkcja,zapotrzebowanie,limity,koszt);
        algorytm.doAlgorytm();
        result=algorytm.result;
        sumaKoszt=algorytm.sumaKoszt;
    }

    private static Producent createProducent(String[] metadata) {
        try {
            int id = Integer.parseInt(metadata[0]);
            String nazwa = metadata[1];
            int produkcja = Integer.parseInt(metadata[2]);
            return new Producent(id, nazwa, produkcja);
        }
        catch (NumberFormatException e){
            return null;
        }
    }

    private static Apteka createApteka(String[] metadata) {
        try {
            int id = Integer.parseInt(metadata[0]);
            String nazwa = metadata[1];
            int zapotrzebowanie = Integer.parseInt(metadata[2]);
            return new Apteka(id, nazwa, zapotrzebowanie);
        }
        catch (NumberFormatException e){
            return null;
        }
    }

    private static Polaczenie createPolaczenie(String[] metadata) {
        try {
            int id_prod = Integer.parseInt(metadata[0]);
            int id_apt = Integer.parseInt(metadata[1]);
            int max_dost = Integer.parseInt(metadata[2]);
            double koszt = Double.parseDouble(metadata[3]);
            return new Polaczenie(id_prod, id_apt, max_dost, koszt);
        }
        catch (NumberFormatException e){
            return null;
        }

    }

    public static void out(){
        try {
            File myObj = new File("wynik.txt");
            if (myObj.createNewFile()) {
                System.out.println("Plik stworzyny " + myObj.getName());
            } else {
                System.out.println("Nadpisano "+ myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("wynik.txt");
            PrintWriter printWriter = new PrintWriter(myWriter);
            int licznik = 0;
            for(int i=0;i<producenci.size();i++) {
                for(int j=0;j<apteki.size();j++) {
                    if(result[i][j]>0) {
                        printWriter.printf("%-30s -> %-30s [Koszt = %d * %s = %s zł]\n", producenci.get(i).getNazwa(), apteki.get(j).getNazwa(), result[i][j], String.format("%.2f", polaczenia.get(licznik).getKoszt()), String.format("%.2f", result[i][j] * polaczenia.get(licznik).getKoszt()));
                    }
                    licznik++;
                }
            }
            printWriter.printf("Opłaty całkowite: "+ String.format("%.2f", sumaKoszt)+ " zł");
            myWriter.close();
            System.out.println("Dane zapisane");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
