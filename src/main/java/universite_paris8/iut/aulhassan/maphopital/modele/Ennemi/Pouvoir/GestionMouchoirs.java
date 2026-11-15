package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir;

import universite_paris8.iut.aulhassan.maphopital.modele.BFS.BFS;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Enrhumé;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.ActionsMouchoir;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.MouchoirEnrhumé;

import java.util.ArrayList;
import java.util.List;

public class GestionMouchoirs implements ActionsMouchoir {

    private List<MouchoirEnrhumé> actifs = new ArrayList<>();
    private List<MouchoirEnrhumé> nouveaux = new ArrayList<>();
    private List<MouchoirEnrhumé> supprimes = new ArrayList<>();
    private List<Ennemi> nouveauxEnnemis = new ArrayList<>();
    private BFS bfs;

    public GestionMouchoirs(BFS bfs) {
        this.bfs = bfs;
    }

    @Override
    public boolean ajouterMouchoir(MouchoirEnrhumé mouchoir) {
        for (MouchoirEnrhumé m : actifs) {
            if (m.getX() == mouchoir.getX() && m.getY() == mouchoir.getY()) {
                return false;   // il y a déjà un mouchoir ici : refusé
            }
        }
        actifs.add(mouchoir);
        nouveaux.add(mouchoir);
        return true;            // posé
    }

    @Override
    public void transformerMouchoir(MouchoirEnrhumé mouchoir, Enrhumé nouvelEnrhume) {
        Sommet spawn = new Sommet(nouvelEnrhume.getSpawnX() / 32, nouvelEnrhume.getSpawnY() / 32);
        nouvelEnrhume.setChemin(bfs.cheminVersCible(spawn));
        nouveauxEnnemis.add(nouvelEnrhume);
        supprimes.add(mouchoir);
        actifs.remove(mouchoir);
    }

    @Override
    public List<MouchoirEnrhumé> getMouchoirsActifs() {
        return new ArrayList<>(actifs);   // une COPIE
    }

    public List<MouchoirEnrhumé> getNouveaux() { return nouveaux; }
    public List<MouchoirEnrhumé> getSupprimes() { return supprimes; }
    public List<Ennemi> getNouveauxEnnemis() { return nouveauxEnnemis; }
    public void viderNouveaux() { nouveaux.clear(); }
    public void viderSupprimes() { supprimes.clear(); }
    public void viderNouveauxEnnemis() { nouveauxEnnemis.clear(); }
}