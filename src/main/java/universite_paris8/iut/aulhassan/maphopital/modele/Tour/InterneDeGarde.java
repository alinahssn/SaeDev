package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class InterneDeGarde extends Tour {

    private static final int DEGATS_BASE = 15;
    private static final int BONUS_DEGATS = 5;

    public InterneDeGarde() {
        super(50, DEGATS_BASE + BONUS_DEGATS, 3, 3, new ConfigProjectile("pillule.png", 16, false));
    }

    @Override
    protected Projectile executerAction(List<Ennemi> ennemisActifs) {
        Ennemi cible = choisirCible(ennemisActifs);
        if (cible == null) return null;

        setCooldownActuel(60);
        return new ProjectileDegatsBonus(creerProjectile(cible, DEGATS_BASE), BONUS_DEGATS);
    }

    @Override
    public int getNumCaseApresRetrait() {
        return 1;
    }
}