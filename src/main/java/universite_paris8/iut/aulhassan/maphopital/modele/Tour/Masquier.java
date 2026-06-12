package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class Masquier extends Tour {

    private static final int RAYON_DETECTION = 16;
    private static final int PV_MAX = 100;
    private SimpleIntegerProperty pv = new SimpleIntegerProperty(PV_MAX);

    public Masquier() {
        super(50, 0, 0, 0, "masque.png", 16, false);
    }

    public void subirDegats(int degats) {
        pv.set(Math.max(0, pv.get() - degats));
    }

    public boolean estDetruit() {
        return pv.get() <= 0;
    }

    public int getPv() { return pv.get(); }
    public int getPvMax() { return PV_MAX; }
    public SimpleIntegerProperty pvProperty() { return pv; }

    @Override
   public Projectile agir(List<Ennemi> ennemisActifs) {
        for (Ennemi e : ennemisActifs) {
            if (e.estVivant()) {
                double dx = getX() - e.getX();
                double dy = getY() - e.getY();
                if (Math.sqrt(dx * dx + dy * dy) <= RAYON_DETECTION) {
                    e.bloquer(2);
                    e.attaquerMasquier(this);
                }
            }
        }
        return null;
    }
}