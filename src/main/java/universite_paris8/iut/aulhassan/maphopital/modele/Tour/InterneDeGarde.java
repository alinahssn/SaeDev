package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class InterneDeGarde extends Tour {

    public InterneDeGarde() {
        super(50, 20, 3, 3, new ConfigProjectile("pillule.png",16, false));
    }
    @Override
    protected Projectile executerAction(List<Ennemi> ennemisActifs) {
        for (Ennemi e : ennemisActifs) {
            if (peutTirer(e)) {
                Projectile proj = new Projectile(getX() + 16, getY() + 16, e, getDegat(), getProjectileFixe(), getNomImageProjectile(), getTailleProjectile());
               setCooldownActuel(60);
                return proj;
            }
        }
        return null;
    }
}