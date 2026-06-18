package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class Anesthésiste extends Tour {

    private static final double FACTEUR_RALENTISSEMENT = 0.5;
    private boolean actif = false;

    public Anesthésiste() {
        super(75, 0, 1, 2, "nuage.png", 64, true);
    }

    @Override
    public Projectile agir(List<Ennemi> ennemisActifs) {
        int porteePixels = getPortee() * 32;
        actif = false;
        for (Ennemi e : ennemisActifs) {
            if (!e.estVivant()) continue;

            int dx = getX() - e.getX();
            int dy = getY() - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance <= porteePixels) {
                e.ralentir(FACTEUR_RALENTISSEMENT);
                actif = true;
            } else {
                e.restaurerVitesse();
            }
        }

        return null;
    }

    public boolean estActif() { return actif; }
}