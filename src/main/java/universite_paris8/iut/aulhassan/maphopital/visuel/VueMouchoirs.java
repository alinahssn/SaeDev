package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.GestionMouchoirs;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.MouchoirEnrhumé;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;

import java.util.HashMap;
import java.util.Map;

public class VueMouchoirs {

    private EnvironnementJeu environnement;
    private Pane panneauJeu;
    private GestionMouchoirs gestionMouchoirs;


    private Map<MouchoirEnrhumé, ImageView> vuesMouchoirs = new HashMap<>();

    public VueMouchoirs(EnvironnementJeu environnement, Pane panneauJeu) {
        this.environnement = environnement;
        this.panneauJeu = panneauJeu;
        this.gestionMouchoirs = environnement.getGestionMouchoirs();
    }

    public void mettreAJour() {
        for (MouchoirEnrhumé mouchoir : gestionMouchoirs.getNouveaux()) {

            ImageView imageView =  new ImageView(ChargeurImage.charger("mouchoir2.png"));

            imageView.setFitWidth(28);
            imageView.setFitHeight(28);

            imageView.setLayoutX(mouchoir.getX() + 2);
            imageView.setLayoutY(mouchoir.getY() + 2);

            panneauJeu.getChildren().add(imageView);

            vuesMouchoirs.put(mouchoir, imageView);
        }

        gestionMouchoirs.viderNouveaux();

        for (MouchoirEnrhumé mouchoir : gestionMouchoirs.getSupprimes()) {

            ImageView imageView = vuesMouchoirs.get(mouchoir);

            if (imageView != null) {
                panneauJeu.getChildren().remove(imageView);
                vuesMouchoirs.remove(mouchoir);
            }
        }

        gestionMouchoirs.viderSupprimes();
    }
}