package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Patient {
    private static final int pvDepart = 200;

    private IntegerProperty pv = new SimpleIntegerProperty(pvDepart);
    private int pvMax;
    private boolean vivant;

    public Patient() {
        this.pvMax = pvDepart;
        this.vivant = true;
    }

    public int getPv() {
        return pv.get();
    }
    public int getPvMax() {
        return pvMax;
    }
    public boolean estVivant() {
        return vivant;
    }

    public IntegerProperty pvProperty() {
        return pv;
    }

    public void setPv(int nouveauPv) {
        this.pv.set(nouveauPv);
        if (nouveauPv <= 0){
            vivant = false;
        }
    }

    public void soigner(int soin){
        if(vivant && getPv() < pvMax) {
            this.setPv(getPv() + soin);
        } else {
            System.out.println("PV FULL");
    }


  }
}
