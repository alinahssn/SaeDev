package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class Infirmier extends Tour {

    private FlaqueGel flaqueActuelle = null;

    public Infirmier() {
        super(75, 5, 1, 2, new ConfigProjectile("flaque.png", 72, true));

    }


    @Override
    public Projectile executerAction(List<Ennemi> ennemisActifs) {
        if (flaqueActuelle != null && flaqueActuelle.estEncoreActive()) {
            flaqueActuelle.appliquerDegatsZone(ennemisActifs);
            return null;
        }
        if (flaqueActuelle != null && !flaqueActuelle.estEncoreActive()) {
            flaqueActuelle = null;
        }

        Ennemi cible = choisirCible(ennemisActifs);
        if (cible != null) {
            flaqueActuelle = new FlaqueGel(cible.getX() - 16, cible.getY() - 16, getDegat());
            setCooldownActuel(60);
            return flaqueActuelle;
        }
        return null;
    }
}