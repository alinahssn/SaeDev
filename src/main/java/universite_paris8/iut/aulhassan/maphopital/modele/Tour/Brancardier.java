package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class Brancardier extends Tour {

    private double brancardX;
    private double brancardY;
    private boolean enMouvement = false;
    private double dirX = 0;
    private double dirY = 0;

    public Brancardier() {
        super(150, 20, 1, 2, "branca.png", 32, true);
    }

    @Override
    public Projectile agir(List<Ennemi> ennemisActifs) {
        tickCooldown();

        if (!enMouvement) {
            this.brancardX = this.getX();
            this.brancardY = this.getY();

            for (Ennemi e : ennemisActifs) {
                if (peutTirer(e)) {
                    enMouvement = true;
                    double dx = e.getX() - this.getX();
                    double dy = e.getY() - this.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if (dist > 0) {
                        this.dirX = dx / dist;
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
            this.brancardX += this.dirX * 0.3;
            this.brancardY += this.dirY * 0.3;

            for (Ennemi e : ennemisActifs) {
                double distEnnemi = Math.sqrt(Math.pow(this.brancardX - e.getX(), 2) + Math.pow(this.brancardY - e.getY(), 2));
                if (distEnnemi < 24 && this.cooldownActuel <= 0 && e.estVivant()) {
                    e.subirDegats(this.getDegat());
                    this.cooldownActuel = 40;
                }
            }

            double distBase = Math.sqrt(Math.pow(this.brancardX - this.getX(), 2) + Math.pow(this.brancardY - this.getY(), 2));
            if (distBase >= 64) {
                this.enMouvement = false;
            }
        }

        return null;
    }

    public double getBrancardX() { return this.brancardX; }
    public double getBrancardY() { return this.brancardY; }
}