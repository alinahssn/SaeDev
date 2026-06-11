package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public class BarreVie extends Group {

    public BarreVie(Ennemi ennemi) {
        Rectangle fond = new Rectangle(32, 6);
        fond.setFill(Color.DARKGRAY);

        Rectangle barre = new Rectangle(32, 6);
        barre.setFill(Color.GREEN);

        barre.widthProperty().bind(
                ennemi.pvProperty().multiply(32.0).divide(ennemi.getPvMax())
        );

        ennemi.pvProperty().addListener((obs, ancienneValeur, nouvelleValeur) -> {
            double ratio = nouvelleValeur.doubleValue() / ennemi.getPvMax();
            if (ratio < 0.2) {
                barre.setFill(Color.RED);
            } else if (ratio < 0.4) {
                barre.setFill(Color.ORANGE);
            } else if (ratio < 0.6) {
                barre.setFill(Color.YELLOW);
            } else {
                barre.setFill(Color.GREEN);
            }
        });

        translateXProperty().bind(ennemi.xProperty());
        translateYProperty().bind(ennemi.yProperty().subtract(10));

        getChildren().addAll(fond, barre);
    }

}