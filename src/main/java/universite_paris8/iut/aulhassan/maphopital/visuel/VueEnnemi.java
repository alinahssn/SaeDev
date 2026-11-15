package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;

public class VueEnnemi {

    private final ImageView imageView;
    private final BarreVie barreVie;

    public VueEnnemi(Ennemi ennemi, Pane panneau, EnvironnementJeu environnement) {

        String nomImage;
        if      (ennemi instanceof SujetAlpha) nomImage = "sujet_alpha.png";
        else if (ennemi instanceof Ebola)      nomImage = "ébola.png";
        else if (ennemi instanceof Covidé)     nomImage = "covidé.png";
        else if (ennemi instanceof Grippé)     nomImage = "grippé.png";
        else if (ennemi instanceof Rabique)    nomImage = "rabique.png";
        else if (ennemi instanceof Enrhumé)    nomImage = "enrhumé.png";
        else                                   nomImage = "gastrique.png";

        this.imageView = new ImageView(ChargeurImage.charger(nomImage));
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.translateXProperty().bind(ennemi.xProperty());
        imageView.translateYProperty().bind(ennemi.yProperty());

        this.barreVie = new BarreVie(ennemi.pvProperty(), ennemi.getPvMax());

        barreVie.getFond().layoutXProperty().bind(ennemi.xProperty());
        barreVie.getFond().layoutYProperty().bind(ennemi.yProperty().subtract(10));
        barreVie.getBarre().layoutXProperty().bind(ennemi.xProperty());
        barreVie.getBarre().layoutYProperty().bind(ennemi.yProperty().subtract(10));

        ennemi.pvProperty().addListener((obs, ancienPv, nouveauPv) -> {
            if (ancienPv.intValue() > 0 && nouveauPv.intValue() <= 0) {
                environnement.ajouterBudget(ennemi.getRecompense());
                AnimationRecompense.jouer(panneau, ennemi.getRecompense(), ennemi.getX(), ennemi.getY());
                panneau.getChildren().remove(imageView);
                panneau.getChildren().remove(barreVie.getFond());
                panneau.getChildren().remove(barreVie.getBarre());
                environnement.getEnnemisActifs().remove(ennemi);
            }
        });

        panneau.getChildren().add(barreVie.getFond());
        panneau.getChildren().add(barreVie.getBarre());
        panneau.getChildren().add(imageView);
    }
}