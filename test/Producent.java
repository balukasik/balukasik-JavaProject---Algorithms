package Jimp_Proj_1;

public class Producent{
    private int id;
    private String nazwa;
    private int produkcja;

    public Producent(int id,String nazwa,int produkcja){
        this.id=id;
        this.nazwa=nazwa;
        this.produkcja=produkcja;
    }

    public int getProdukcja() {
        return produkcja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setProdukcja(int produkcja) {
        this.produkcja = produkcja;
    }

    public String getNazwa() {
        return nazwa;
    }

}
