package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class FlaqueGel extends Projectile {

    private int tempsRestant = 200; // 4 sec

    public FlaqueGel(double startX, double startY, int degats) {
        super(startX, startY, null, degats, true);
    }

    public boolean estEncoreActive() {
        return this.tempsRestant > 0;
    }

    public void appliquerDegatsZone(List<Ennemi> ennemisActifs) {
        if (!estEncoreActive()) {
            this.setActif(false);
            return;
        }

        this.tempsRestant--;

        for (Ennemi e : ennemisActifs) {
            if (e != null && e.estVivant()) {
                double dx = this.getX() - e.getX();
                double dy = this.getY() - e.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < 20) {
                    e.subirDegats(1);
                }
            }
        }
    }

   @Override
    public void deplacer() {
        if (tempsRestant > 0) {
            tempsRestant--;

            if (getCible() != null && getCible().estVivant()) {
                double dx = this.getX() - getCible().getX();
                double dy = this.getY() - getCible().getY();
                if (Math.sqrt(dx*dx + dy*dy) < 40) {
                    getCible().subirDegats(1);
                }
            }
        } else {
            this.actif = false;
        }
    }

    
}