package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class InterneDeGarde extends Tour {

    public InterneDeGarde() {
        super(50, 20, 3, 3, new ConfigProjectile("pillule.png",16, false));
    }

    @Override
    protected Projectile executerAction(List<Ennemi> ennemisActifs) {
        Ennemi cible = choisirCible(ennemisActifs);
        if (cible == null) return null;

        setCooldownActuel(60);
        return new Projectile(getX() + 16, getY() + 16, cible, getDegat(), getProjectileFixe(), getNomImageProjectile(), getTailleProjectile());
    }
}