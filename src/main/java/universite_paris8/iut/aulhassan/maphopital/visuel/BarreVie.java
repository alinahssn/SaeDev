package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BarreVie {

    private Rectangle fond;
    private Rectangle barre;

    public BarreVie(SimpleIntegerProperty pvProperty, int pvMax) {
        fond = new Rectangle(32, 6);
        fond.setFill(Color.DARKGRAY);

        barre = new Rectangle(32, 6);
        barre.setFill(Color.GREEN);

        barre.widthProperty().bind(
                pvProperty.multiply(32.0).divide(pvMax)
        );

        pvProperty.addListener((obs, ancienneValeur, nouvelleValeur) -> {
            double ratio = nouvelleValeur.doubleValue() / pvMax;
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
    }

    public void positionner(double x, double y) {
        fond.setLayoutX(x);
        fond.setLayoutY(y);
        barre.setLayoutX(x);
        barre.setLayoutY(y);
    }

    public Rectangle getFond() { return fond; }
    public Rectangle getBarre() { return barre; }
}