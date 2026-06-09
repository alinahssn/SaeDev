package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.aulhassan.maphopital.Controleur;
import universite_paris8.iut.aulhassan.maphopital.modele.*;

public class GestClic {

    private Controleur controleur;
    private Button btnInterne;
    private Pane panneauJeu;
    private EnvironnementJeu environnement;

    // Variables internes pour mémoriser l'état du clic
    private int idTourSelect = -1;
    private Rectangle caseHighlight = null;

    public GestClic(Controleur controleur, Button btnInterne, Pane panneauJeu, EnvironnementJeu environnement) {
        this.controleur = controleur;
        this.btnInterne = btnInterne;
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;
    }

    public void configurer() {
        // 1. Clic sur le bouton de la boutique
        btnInterne.setOnAction(event -> {
            if (environnement.getPatient().getPv() == 0) {
                System.out.println("GAME OVER : Le patient est mort ! Impossible de poser une tour");
                return;
            }
            this.idTourSelect = 1;
            System.out.println("Interne sélectionné, clique sur la map pour poser");
        });

        // 2. Clic sur le panneau pour poser la tour
        panneauJeu.setOnMouseClicked(event -> {
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            if (environnement.getTerrain().getMap()[ligne][col] == 1) {
                environnement.getTerrain().getMap()[ligne][col] = 12;

                InterneDeGarde nouvelleTour = new InterneDeGarde();
                nouvelleTour.setX(col * 32);
                nouvelleTour.setY(ligne * 32);
                environnement.getToursActives().add(nouvelleTour);

                ImageView tourPosee = new ImageView(
                        ((ImageView) btnInterne.getGraphic()).getImage()
                );
                tourPosee.setFitWidth(32);
                tourPosee.setFitHeight(32);
                tourPosee.setLayoutX(col * 32);
                tourPosee.setLayoutY(ligne * 32);
                panneauJeu.getChildren().add(tourPosee);

                idTourSelect = -1;
                System.out.println("Tour posée en [" + ligne + "][" + col + "]");
            } else {
                System.out.println("Case invalide !");
            }
        });

        // 3. Déplacement de la souris pour la prévisualisation (Highlight)
        panneauJeu.setOnMouseMoved(event -> {
            if (caseHighlight != null) panneauJeu.getChildren().remove(caseHighlight);
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            caseHighlight = new Rectangle(32, 32);
            caseHighlight.setLayoutX(col * 32);
            caseHighlight.setLayoutY(ligne * 32);
            caseHighlight.setMouseTransparent(true);

            if (environnement.getTerrain().getMap()[ligne][col] == 1) {
                caseHighlight.setFill(Color.rgb(0, 255, 0, 0.4));
            } else {
                caseHighlight.setFill(Color.rgb(255, 0, 0, 0.4));
            }

            panneauJeu.getChildren().add(caseHighlight);
        });
    }
}