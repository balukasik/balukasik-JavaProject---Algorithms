package Jimp_Proj_1;

public class Apteka {
    private int id;
    private String nazwa;
    private int zapotrzebowanie;

    public Apteka(int id, String nazwa, int zapotrzebowanie) {
        this.id = id;
        this.nazwa = nazwa;
        this.zapotrzebowanie = zapotrzebowanie;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getZapotrzebowanie() {
        return zapotrzebowanie;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setZapotrzebowanie(int zapotrzebowanie) {
        this.zapotrzebowanie = zapotrzebowanie;
    }
}
