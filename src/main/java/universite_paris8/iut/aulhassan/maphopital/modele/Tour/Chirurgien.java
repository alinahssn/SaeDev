package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class Chirurgien extends Tour {

    private Ennemi derniereCible;
    private boolean aFrappeCetteFrame = false;

    public Chirurgien() {
        super(150, 50, 1, 1, new ConfigProjectile("scalpel.png", 72, false));

    }

    // Avant, le cooldown était décrémenté 2 fois par frame (agir + executerAction) :
    // l'ancien 50 équivaut donc à 25 maintenant que le bug est corrigé.
    private static final int COOLDOWN_FRAMES = 25;

    @Override
    public Projectile executerAction(List<Ennemi> ennemisActifs) {
        aFrappeCetteFrame = false;

        Ennemi cible = choisirCible(ennemisActifs);
        if (cible != null) {
            cible.subirDegats(this.getDegat());
            setCooldownActuel(COOLDOWN_FRAMES);
            this.derniereCible = cible;
            this.aFrappeCetteFrame = true;
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