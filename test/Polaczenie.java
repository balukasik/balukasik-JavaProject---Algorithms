package Jimp_Proj_1;

public class Polaczenie {
    int id_prod;
    int id_apt;
    int max_dost;
    double koszt;

    public Polaczenie(int id_prod, int id_apt, int max_dost, double koszt) {
        this.id_prod = id_prod;
        this.id_apt = id_apt;
        this.max_dost = max_dost;
        this.koszt = koszt;
    }


    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_apt() {
        return id_apt;
    }

    public void setId_apt(int id_apt) {
        this.id_apt = id_apt;
    }

    public int getMax_dost() {
        return max_dost;
    }

    public void setMax_dost(int max_dost) {
        this.max_dost = max_dost;
    }

    public double getKoszt() {
        return koszt;
    }

    public void setKoszt(int koszt) {
        this.koszt = koszt;
    }
}
