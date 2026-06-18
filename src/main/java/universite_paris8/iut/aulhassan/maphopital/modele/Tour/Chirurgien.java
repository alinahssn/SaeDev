package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.ArrayList;
import java.util.List;

public class Chirurgien extends Tour {

    private boolean aAttaque = false;
    private int tempsAffichage = 0;
    private Ennemi cibleActuelle = null;


    public Chirurgien() {
        super(150, 50, 1, 1, "scalpel.png", 72, false);
    }

    @Override
    public Projectile agir(List<Ennemi> ennemisActifs) {
        tickCooldown();

        if (this.tempsAffichage > 0) {
            this.tempsAffichage--;
            if (this.tempsAffichage == 0) {
                this.aAttaque = false;
                this.cibleActuelle = null;
            }
        }

        List<Ennemi> copieEnnemis = new ArrayList<>(ennemisActifs);

        for (Ennemi e : copieEnnemis) {
            if (peutTirer(e)) {
                if (this.cooldownActuel <= 0) {
                    e.subirDegats(this.getDegat());
                    this.cooldownActuel = 50;
                    this.aAttaque = true;
                    this.tempsAffichage = 12;
                    this.cibleActuelle = e;
                }
            }
        }
        return null;
    }

    public boolean isaAttaque() {
        return this.aAttaque;
    }
    public Ennemi getCibleActuelle() { return cibleActuelle; }

}





