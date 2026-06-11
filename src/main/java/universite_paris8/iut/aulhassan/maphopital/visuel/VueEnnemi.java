package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class VueEnnemi {

    private final ImageView imageView;
    private final BarreVie barreVie;
    private final Pane panneau;

    public VueEnnemi(Ennemi ennemi, String nomImage, Pane panneau) {
        this.panneau = panneau;

        this.imageView = new ImageView(ChargeurImage.charger(nomImage));
        this.imageView.setFitWidth(32);
        this.imageView.setFitHeight(32);
        this.imageView.translateXProperty().bind(ennemi.xProperty());
        this.imageView.translateYProperty().bind(ennemi.yProperty());

        this.barreVie = new BarreVie(ennemi);

        ennemi.pvProperty().addListener((obs, ancienne, nouvelle) -> {
            if (nouvelle.intValue() <= 0) {
                supprimer();
            }
        });

        panneau.getChildren().addAll(imageView, barreVie);
    }

    public void supprimer() {
        panneau.getChildren().remove(imageView);
        panneau.getChildren().remove(barreVie);
    }
}