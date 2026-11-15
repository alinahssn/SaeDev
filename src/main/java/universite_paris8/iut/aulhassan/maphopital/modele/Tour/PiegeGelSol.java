package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class PiegeGelSol {
    private double x, y;
    private int tempsRestant = 200;
    private int degats;

    public PiegeGelSol(double x, double y, int degats) {
        this.x = x;
        this.y = y;
        this.degats = degats;
    }

    public boolean estEncoreActif() {
        return this.tempsRestant > 0;
    }

    public void tickEtGererEffet(List<Ennemi> ennemisActifs) {
        if (tempsRestant > 0) {
            tempsRestant--;
        }
        if (tempsRestant % 10 == 0 && ennemisActifs != null) {            for (int i = ennemisActifs.size() - 1; i >= 0; i--) {
                Ennemi e = ennemisActifs.get(i);
                if (e != null && e.estVivant()) {
                    double distance = Position.calculerDistance(this.x, this.y, e.getX(), e.getY());
                    if (distance < 20) {
                        e.subirDegats(degats);
                    }
                }
            }
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
}