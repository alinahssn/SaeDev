package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

public interface StrategieCiblage {
    Ennemi choisir(Tour tour, List<Ennemi> ennemis);
}