package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.ArrayList;
import java.util.List;

public class Chirurgien extends Tour {

    private Ennemi derniereCible;
    private boolean aFrappeCetteFrame = false;

    public Chirurgien() {
        super(150, 50, 1, 1, new ConfigProjectile("scalpel.png", 72, false));

    }

    @Override
    public Projectile executerAction(List<Ennemi> ennemisActifs) {
        tickCooldown();
        aFrappeCetteFrame = false;

        List<Ennemi> copieEnnemis = new ArrayList<>(ennemisActifs);

        for (Ennemi e : copieEnnemis) {
            if (peutTirer(e)) {
                if (estCooldownPret()) {
                    e.subirDegats(this.getDegat());
                    setCooldownActuel(50);
                    this.derniereCible = e;
                    this.aFrappeCetteFrame = true;
                }
            }
        }
        return null;
    }

    public boolean aFrappeCetteFrame() {
        return aFrappeCetteFrame;
    }

    public Ennemi getDerniereCible() {
        return derniereCible;
    }
}





