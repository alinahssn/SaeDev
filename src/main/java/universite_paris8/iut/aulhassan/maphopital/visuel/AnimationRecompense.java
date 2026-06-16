package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AnimationRecompense {

    public static void jouer(Pane panneau, int montant, double x, double y) {
        Text texte = new Text("+" + montant + "€");
        texte.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        texte.setFill(Color.GOLD);
        texte.setStroke(Color.DARKGOLDENROD);
        texte.setStrokeWidth(0.5);
        texte.setLayoutX(x);
        texte.setLayoutY(y);
        texte.setOpacity(1.0);

        panneau.getChildren().add(texte);

        Timeline anim = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(texte.layoutYProperty(), y),
                        new KeyValue(texte.opacityProperty(), 1.0)
                ),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(texte.layoutYProperty(), y - 40),
                        new KeyValue(texte.opacityProperty(), 0.0)
                )
        );

        anim.setOnFinished(e -> panneau.getChildren().remove(texte));
        anim.play();
    }

    public static void jouerAchat(Pane panneau, int montant, double x, double y) {
        Text texte = new Text("-" + montant + "€");
        texte.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        texte.setFill(Color.RED);
        texte.setStroke(Color.DARKRED);
        texte.setStrokeWidth(0.5);
        texte.setLayoutX(x);
        texte.setLayoutY(y);

        panneau.getChildren().add(texte);

        Timeline anim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(texte.layoutYProperty(), y), new KeyValue(texte.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(900), new KeyValue(texte.layoutYProperty(), y - 40), new KeyValue(texte.opacityProperty(), 0.0))
        );
        anim.setOnFinished(e -> panneau.getChildren().remove(texte));
        anim.play();
    }

    public static void jouerRevente(Pane panneau, int montant, double x, double y) {
        Text texte = new Text("+" + montant + "€");
        texte.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        texte.setFill(Color.LIMEGREEN); // Vert pour la revente (ou Color.GOLD si tu préfères)
        texte.setStroke(Color.FORESTGREEN);
        texte.setStrokeWidth(0.5);
        texte.setLayoutX(x);
        texte.setLayoutY(y);

        panneau.getChildren().add(texte);

        Timeline anim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(texte.layoutYProperty(), y), new KeyValue(texte.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(900), new KeyValue(texte.layoutYProperty(), y - 40), new KeyValue(texte.opacityProperty(), 0.0))
        );
        anim.setOnFinished(e -> panneau.getChildren().remove(texte));
        anim.play();
    }






}