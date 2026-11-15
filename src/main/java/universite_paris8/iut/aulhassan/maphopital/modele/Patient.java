package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Patient {
    private static final int PV_DEPART = 200;

    private final IntegerProperty pv = new SimpleIntegerProperty(PV_DEPART);
    private final int pvMax = PV_DEPART;

    public int getPv() {
        return pv.get();
    }

    public int getPvMax() {
        return pvMax;
    }

    public IntegerProperty pvProperty() {
        return pv;
    }

    public boolean estVivant() {
        return pv.get() > 0;
    }

    public boolean estBlesse() {
        return estVivant() && pv.get() < pvMax;
    }

    public void subirDegats(int degats) {
        pv.set(Math.max(0, pv.get() - degats));
    }

    public void soigner(int soin) {
        if (estBlesse()) {
            pv.set(Math.min(pvMax, pv.get() + soin));
        }
    }
}