package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.*;

public class GestClic {

    private Button btnInterne;
    private Button btnGel;
    private Button btnBranca;
    private Button btnAne;
    private Button btnMasque;
    private Button btnChir;
    private Button btnRevente;
    private Pane panneauJeu;
    private EnvironnementJeu environnement;
    private VueTour vueTour;

    private int idTourSelect = -1;
    private Rectangle caseHighlight = null;
    private Tour tourSelectionnee = null;
    private ImageView imageTourSelectionnee = null;

    public GestClic(Button btnInterne, Button btnGel, Button btnBranca, Button btnAne, Button btnMasque, Button btnChir, Button btnRevente, Pane panneauJeu, EnvironnementJeu environnement, VueTour vueTour) {
        this.btnInterne = btnInterne;
        this.btnGel = btnGel;
        this.btnBranca = btnBranca;
        this.btnAne = btnAne;
        this.btnMasque = btnMasque;
        this.btnChir = btnChir;
        this.btnRevente = btnRevente;
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;
        this.vueTour = vueTour;
    }

    private void enregistrerClicBouton(Button bouton, int idTour, String nomTour) {
        bouton.setOnAction(event -> {
            if (environnement.getPatient().getPv() == 0) {
                System.out.println("GAME OVER : Le patient est mort ! Impossible de poser une tour");
                return;
            }
            this.idTourSelect = idTour;
            System.out.println(nomTour + " sélectionné, clique sur la map pour poser");
        });
    }

    public void configurer() {
        enregistrerClicBouton(btnInterne, 1, "Interne de Garde");
        enregistrerClicBouton(btnGel, 2, "Infirmier");
        enregistrerClicBouton(btnBranca, 3, "Brancardier");
        enregistrerClicBouton(btnAne, 4, "Anesthésiste");
        enregistrerClicBouton(btnMasque, 5, "Masquier");
        enregistrerClicBouton(btnChir, 6, "Chirurgien");

        panneauJeu.setOnMouseClicked(event -> {
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            int valeurCase = environnement.getTerrain().getMap()[ligne][col];
            boolean caseValide = (idTourSelect == 5) ? valeurCase == 0 : valeurCase == 1;

            if (caseValide) {
                Tour nouvelleTour = creerTour(idTourSelect);
                ImageView tourPosee = creerImageTour(idTourSelect);

                if (nouvelleTour != null && environnement.poserTour(nouvelleTour, col, ligne)) {
                    vueTour.afficherTour(nouvelleTour, col, ligne, tourPosee);

                    Tour tourAVendre = nouvelleTour;
                    tourPosee.setOnMouseClicked(clickSurTour -> {
                        this.tourSelectionnee = tourAVendre;
                        this.imageTourSelectionnee = (ImageView) clickSurTour.getSource();
                        System.out.println("Tour sélectionnée pour revente !");
                    });

                    System.out.println("Tour posée en [" + ligne + "][" + col + "]");
                } else {
                    System.out.println("Achat impossible : Pas assez d'argent");
                }
                idTourSelect = -1;
            } else {
                System.out.println("Case invalide !");
            }
        });

        panneauJeu.setOnMouseMoved(event -> {
            if (caseHighlight != null) panneauJeu.getChildren().remove(caseHighlight);
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            int valeurCase = environnement.getTerrain().getMap()[ligne][col];
            boolean caseValide = (idTourSelect == 5) ? valeurCase == 0 : valeurCase == 1;

            caseHighlight = new Rectangle(32, 32);
            caseHighlight.setLayoutX(col * 32);
            caseHighlight.setLayoutY(ligne * 32);
            caseHighlight.setMouseTransparent(true);
            caseHighlight.setFill(caseValide ? Color.rgb(0, 255, 0, 0.4) : Color.rgb(255, 0, 0, 0.4));

            panneauJeu.getChildren().add(caseHighlight);
        });

        btnRevente.setOnAction(event -> {
            if (tourSelectionnee != null && imageTourSelectionnee != null) {
                vueTour.supprimerTour(tourSelectionnee);
                environnement.revendreTour(tourSelectionnee);
                this.tourSelectionnee = null;
                this.imageTourSelectionnee = null;
                System.out.println("Tour revendue !");
            } else {
                System.out.println("Impossible de vendre : Aucune tour sélectionnée !");
            }
        });
    }

    private Tour creerTour(int id) {
        return switch (id) {
            case 1 -> new InterneDeGarde();
            case 2 -> new Infirmier();
            case 3 -> new Brancardier();
            case 4 -> new Anesthésiste();
            case 5 -> new Masquier();
            case 6 -> new Chirurgien();
            default -> null;
        };
    }

    private ImageView creerImageTour(int id) {
        ImageView img = new ImageView();
        img.setImage(switch (id) {
            case 1 -> ((ImageView) btnInterne.getGraphic()).getImage();
            case 2 -> ((ImageView) btnGel.getGraphic()).getImage();
            case 3 -> ((ImageView) btnBranca.getGraphic()).getImage();
            case 4 -> ((ImageView) btnAne.getGraphic()).getImage();
            case 5 -> ((ImageView) btnMasque.getGraphic()).getImage();
            case 6 -> ((ImageView) btnChir.getGraphic()).getImage();
            default -> null;
        });
        return img;
    }
}