package universite_paris8.iut.aulhassan.maphopital.modele;

public class Patient {
    private static final int pvDepart = 200;
    private int pv;
    private int pvMax;
    private boolean vivant;

    public Patient() {
        this.pv = pvDepart;
        this.pvMax = pvDepart;
        this.vivant = true;
    }

    public int getPv() {
        return pv;
    }
    public int getPvMax() {
        return pvMax;
    }
    public boolean estVivant() {
        return vivant;
    }
    public void setPv(int pv) {
        this.pv = pv;
        if (pv < 0){
            vivant = false;
        }
    }

    public void soigner(int soin){
        if(vivant && pv<pvMax) {
            this.pv += soin;
        }
        else {
            System.out.println("PV FULL");
        }

    }
}
