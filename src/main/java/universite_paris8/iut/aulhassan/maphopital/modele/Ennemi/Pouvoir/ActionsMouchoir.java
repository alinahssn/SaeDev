package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Enrhumé;

import java.util.List;

public interface ActionsMouchoir {
    boolean ajouterMouchoir(MouchoirEnrhumé mouchoir);
    void transformerMouchoir(MouchoirEnrhumé mouchoir, Enrhumé nouvelEnrhume);
    List<MouchoirEnrhumé> getMouchoirsActifs();
}