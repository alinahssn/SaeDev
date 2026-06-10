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
    private Button btnGel;
    private Button btnBranca;
    private Button btnAne;
    private Button btnMasque;
    private Button btnChir;
    private Pane panneauJeu;
    private EnvironnementJeu environnement;

    private int idTourSelect = -1;
    private Rectangle caseHighlight = null;

    public GestClic(Controleur controleur, Button btnInterne, Button btnGel, Button btnBranca, Button btnAne, Button btnMasque, Button btnChir, Pane panneauJeu, EnvironnementJeu environnement) {
        this.controleur = controleur;
        this.btnInterne = btnInterne;
        this.btnGel = btnGel;
        this.btnBranca = btnBranca;
        this.btnAne = btnAne;
        this.btnMasque = btnMasque;
        this.btnChir = btnChir;
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;
    }

    private Tour creerTour(int idTour) {
        switch (idTour) {
            case 1: return new InterneDeGarde();
            case 2: return new Infirmier();
            case 3: return new Brancardier();
            case 4: return new Anesthésiste();
            case 5: return new Masquier();
            case 6: return new Chirurgien();
            default: return null;
        }
    }

    private void enregistrerClicBouton(Button bouton, int idTour, String nomTour) {
        bouton.setOnAction(event -> {
            if (environnement.getPatient().getPv() == 0) {
                System.out.println("GAME OVER : Le patient est mort ! Impossible de poser une tour");
                return;
            }
            // Vérification budget avant même de sélectionner la tour
            Tour tourTemp = creerTour(idTour);
            if (tourTemp != null && environnement.getBudget() < tourTemp.getCout()) {
                System.out.println("Budget insuffisant");
                return;
            }
            this.idTourSelect = idTour;
            System.out.println(nomTour + " sélectionné, clique sur la map pour poser");
        });
    }

    public void configurer() {
        // 1. Clic sur les boutons
        enregistrerClicBouton(btnInterne, 1, "Interne de Garde");
        enregistrerClicBouton(btnGel,     2, "Infirmier");
        enregistrerClicBouton(btnBranca,  3, "Brancardier");
        enregistrerClicBouton(btnAne,     4, "Anesthésiste");
        enregistrerClicBouton(btnMasque,  5, "Masquier");
        enregistrerClicBouton(btnChir,    6, "Chir");

        // 2. Clic sur le panneau pour poser la tour
        panneauJeu.setOnMouseClicked(event -> {
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            if (environnement.getTerrain().getMap()[ligne][col] == 1) {

                Tour nouvelleTour = creerTour(idTourSelect);
                if (nouvelleTour == null) { idTourSelect = -1; return; }

                if (!environnement.acheterTour(nouvelleTour.getCout())) {
                    System.out.println("Budget insuffisant");
                    idTourSelect = -1;
                    return;
                }

                environnement.getTerrain().getMap()[ligne][col] = 12;
                nouvelleTour.setX(col * 32);
                nouvelleTour.setY(ligne * 32);
                environnement.getToursActives().add(nouvelleTour);

                ImageView tourPosee = new ImageView();
                if (idTourSelect == 1) {
                    nouvelleTour = new InterneDeGarde();
                    tourPosee.setImage(((ImageView) btnInterne.getGraphic()).getImage());
                } else if (idTourSelect == 2) {
                    nouvelleTour = new Infirmier();
                    tourPosee.setImage(((ImageView) btnGel.getGraphic()).getImage());
                } else if (idTourSelect == 3) {
                    nouvelleTour = new Brancardier();
                    tourPosee.setImage(((ImageView) btnBranca.getGraphic()).getImage());
                } else if (idTourSelect == 4) {
                    nouvelleTour = new Anesthésiste();
                    tourPosee.setImage(((ImageView) btnAne.getGraphic()).getImage());
                } else if (idTourSelect == 5) {
                    nouvelleTour = new Masquier();
                    tourPosee.setImage(((ImageView) btnMasque.getGraphic()).getImage());
                } else if (idTourSelect == 6) {
                    nouvelleTour = new Anesthésiste();
                    tourPosee.setImage(((ImageView) btnChir.getGraphic()).getImage());
                }
                tourPosee.setFitWidth(32);
                tourPosee.setFitHeight(32);
                tourPosee.setLayoutX(col * 32);
                tourPosee.setLayoutY(ligne * 32);
                panneauJeu.getChildren().add(tourPosee);

                System.out.println("Tour posée en [" + ligne + "][" + col + "]");
                idTourSelect = -1;

            } else {
                System.out.println("Case invalide !");
            }
        });

        // 3. Survol souris — prévisualisation colorée
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