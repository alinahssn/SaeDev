package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class Infirmier extends Tour {

    private FlaqueGel flaqueActuelle = null;

    public Infirmier() {
        super(75, 5, 1, 2, "flaque.png", 72, true);
    }


    @Override
    public Projectile agir(List<Ennemi> ennemisActifs) {
        if (flaqueActuelle != null && flaqueActuelle.estEncoreActive()) {
            flaqueActuelle.appliquerDegatsZone(ennemisActifs);
            return null;
        }
        if (flaqueActuelle != null && !flaqueActuelle.estEncoreActive()) {
            flaqueActuelle = null;
        }

        tickCooldown();

        for (Ennemi e : ennemisActifs) {
            if (e != null && peutTirer(e)) {
                flaqueActuelle = new FlaqueGel(e.getX() - 16, e.getY() - 16, getDegat());

                this.cooldownActuel = 60;
                return flaqueActuelle;
            }
        }
        return null;
    }
}


