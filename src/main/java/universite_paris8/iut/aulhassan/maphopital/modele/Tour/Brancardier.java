package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class Brancardier extends Tour {

    private double brancardX;
    private double brancardY;
    private boolean enMouvement = false;
    private double dirX = 0;// direction ou il va
    private double dirY = 0;

    public Brancardier() {
        super(150, 20, 1, 2, "branca.png", 32, true);
    }

    @Override
    public Projectile agir(List<Ennemi> ennemisActifs) {
        tickCooldown();

        if (!enMouvement) {
            this.brancardX = this.getX();
            this.brancardY = this.getY();//bouge pas = reste sur la case de la tour

            for (Ennemi e : ennemisActifs) {
                if (peutTirer(e)) {
                    enMouvement = true;

                    double dx = e.getX() - this.getX();
                    double dy = e.getY() - this.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);// distance entre tour et virus

                    if (dist > 0) {
                        this.dirX = dx / dist;//creation du vecteur unitaire
                        this.dirY = dy / dist;
                    } else {
                        this.dirX = 0;
                        this.dirY = 0;
                    }
                    break;
                }
            }
        }

        if (enMouvement) {
            this.brancardX += this.dirX * 0.3; //chaque frame avance de ça
            this.brancardY += this.dirY * 0.3;

            for (Ennemi e : ennemisActifs) {
                double dx = this.brancardX - e.getX();
                double dy = this.brancardY - e.getY();

                if (Math.sqrt(dx * dx + dy * dy) < 24 && this.cooldownActuel <= 0 && e.estVivant()) {
                    e.subirDegats(this.getDegat());
                    this.cooldownActuel = 40;
                }
            }

            double dxBase = this.brancardX - this.getX();
            double dyBase = this.brancardY - this.getY();

            if (Math.sqrt(dxBase * dxBase + dyBase * dyBase) >= 64) {
                this.enMouvement = false;
            }
        }

        return null;
    }

    public double getBrancardX() {
        return this.brancardX;
    }

    public double getBrancardY() {
        return this.brancardY;
    }
}