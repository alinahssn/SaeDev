package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.GestionMouchoirs;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public class VueNouveauxEnnemis {

    private EnvironnementJeu environnement;
    private Pane panneauJeu;

    public VueNouveauxEnnemis(EnvironnementJeu environnement, Pane panneauJeu) {
        this.environnement = environnement;
        this.panneauJeu = panneauJeu;
    }

    public void mettreAJour() {
        GestionMouchoirs gestionMouchoirs = environnement.getGestionMouchoirs();
        for (Ennemi ennemi : gestionMouchoirs.getNouveauxEnnemis()) {
            environnement.getEnnemisActifs().add(ennemi);
            new VueEnnemi(ennemi, panneauJeu, environnement);
        }

        gestionMouchoirs.viderNouveauxEnnemis();
    }
}