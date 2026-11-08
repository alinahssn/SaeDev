package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public class CiblagePlusFaible implements StrategieCiblage {
    @Override
    public Ennemi choisir(Tour tour, List<Ennemi> ennemis) {
        Ennemi meilleur = null;
        for (Ennemi e : ennemis) {
            if (tour.peutTirer(e) && (meilleur == null || e.getPv() < meilleur.getPv())) {
                meilleur = e;
            }
        }
        return meilleur;
    }
}