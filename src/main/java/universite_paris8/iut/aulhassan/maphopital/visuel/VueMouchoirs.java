package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.MouchoirEnrhumé;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;

import java.util.HashMap;
import java.util.Map;

public class VueMouchoirs {

    private EnvironnementJeu environnement;
    private Pane panneauJeu;

    private Map<MouchoirEnrhumé, ImageView> vuesMouchoirs = new HashMap<>();

    public VueMouchoirs(EnvironnementJeu environnement, Pane panneauJeu) {
        this.environnement = environnement;
        this.panneauJeu = panneauJeu;
    }

    public void mettreAJour() {

        for (MouchoirEnrhumé mouchoir : environnement.getNouveauxMouchoirs()) {

            ImageView imageView =  new ImageView(ChargeurImage.charger("mouchoir2.png"));

            imageView.setFitWidth(28);
            imageView.setFitHeight(28);

            imageView.setLayoutX(mouchoir.getX() + 2);
            imageView.setLayoutY(mouchoir.getY() + 2);

            panneauJeu.getChildren().add(imageView);

            vuesMouchoirs.put(mouchoir, imageView);
        }

        environnement.viderNouveauxMouchoirs();

        for (MouchoirEnrhumé mouchoir : environnement.getMouchoirsSupprimes()) {

            ImageView imageView = vuesMouchoirs.get(mouchoir);

            if (imageView != null) {
                panneauJeu.getChildren().remove(imageView);
                vuesMouchoirs.remove(mouchoir);
            }
        }

        environnement.viderMouchoirsSupprimes();
    }
}