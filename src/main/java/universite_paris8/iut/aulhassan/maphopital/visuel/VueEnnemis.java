package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public class VueEnnemis {

    private EnvironnementJeu environnement;
    private Pane panneauJeu;

    public VueEnnemis(EnvironnementJeu environnement, Pane panneauJeu) {
        this.environnement = environnement;
        this.panneauJeu = panneauJeu;
    }

    public void mettreAJour() {
        for (Ennemi ennemi : environnement.getNouveauxEnnemis()) {
            new VueEnnemi(ennemi, panneauJeu, environnement);
        }

        environnement.viderNouveauxEnnemis();
    }
}