package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class CiblagePremierEnPortee implements StrategieCiblage {
    @Override
    public Ennemi choisir(Tour tour, List<Ennemi> ennemis) {
        for (Ennemi e : ennemis) {
            if (tour.peutTirer(e)) return e;
        }
        return null;
    }
}