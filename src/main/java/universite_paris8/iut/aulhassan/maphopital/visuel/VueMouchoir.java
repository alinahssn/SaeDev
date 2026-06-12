package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.MouchoirEnrhumé;

import javafx.scene.image.ImageView;

public class VueMouchoir {

    private ImageView imageView;

    public VueMouchoir(MouchoirEnrhumé mouchoir, Pane panneauJeu) {
        this.imageView = new ImageView(ChargeurImage.charger("mouchoir2.png")
        );

        imageView.setFitWidth(28);
        imageView.setFitHeight(28);

        imageView.setLayoutX(mouchoir.getX() + 2);
        imageView.setLayoutY(mouchoir.getY() + 2);

        panneauJeu.getChildren().add(imageView);

    }
}
