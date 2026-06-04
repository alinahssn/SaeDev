package universite_paris8.iut.aulhassan.maphopital;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Timeline gameloop;
    private int temps;
    private Rectangle caseHighlight = null;

    @FXML private Button btnInterne;
    @FXML private VBox panneauTour;
    @FXML private TilePane tilehopital;
    @FXML private Button btnBonus;
    @FXML private Button btnVague;
    @FXML private Label labelPV;
    @FXML private Label labelBudget;
    @FXML private Pane panneauJeu;

    private int idTourSelect = -1;

    private int ennemisRestantsDansVague = 0;
    private int timerSpawn = 0;
    private int intervalleSpawn = 60;

    private Patient patient;
    private Terrain terrain;
    private int[][] distMap;

    private List<Tour> toursActives = new ArrayList<>();
    private List<Ennemi> ennemisActifs = new ArrayList<>();
    private List<Projectile> projectilesActifs = new ArrayList<>();
    private Map<Projectile, Circle> vuesProjectiles = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        terrain = new Terrain();
        patient = new Patient();

        creerVueModele();

        BFS bfs = new BFS(terrain, 23, 12);
        distMap = bfs.getDistMap();

        creerBoutonInventaire();

        labelPV.setText(patient.getPv() + " / " + patient.getPvMax());

        initAnimation();
        gameloop.play();
    }

    private Image chargerImage(String nomFichier) {
        URL url = getClass().getResource("image/" + nomFichier);
        return new Image(String.valueOf(url));
    }

    public void creerVueModele() {
        Image im0  = chargerImage("solhopital.png");
        Image im1  = chargerImage("solchambre.png");
        Image im2  = chargerImage("mur.png");
        Image im3  = chargerImage("lit.png");
        Image im4  = chargerImage("chevet.png");
        Image im5  = chargerImage("chaise.png");
        Image im6  = chargerImage("machine.png");
        Image im7  = chargerImage("bureau.png");
        Image im8  = chargerImage("chaise2.png");
        Image im9  = chargerImage("distrib.png");
        Image im10 = chargerImage("plante.png");
        Image im11 = chargerImage("lit2.png");

        for (int i = 0; i < terrain.getHauteur(); i++) {
            for (int j = 0; j < terrain.getLargeur(); j++) {
                ImageView imv = new ImageView();
                imv.setFitWidth(32);
                imv.setFitHeight(32);
                switch (terrain.getMap()[i][j]) {
                    case 0:  imv.setImage(im1);  break;
                    case 1:  imv.setImage(im0);  break;
                    case 2:  imv.setImage(im2);  break;
                    case 3:  imv.setImage(im3);  break;
                    case 4:  imv.setImage(im4);  break;
                    case 5:  imv.setImage(im5);  break;
                    case 6:  imv.setImage(im6);  break;
                    case 7:  imv.setImage(im7);  break;
                    case 8:  imv.setImage(im8);  break;
                    case 9:  imv.setImage(im9);  break;
                    case 10: imv.setImage(im10); break;
                    case 11: imv.setImage(im11); break;
                    default: imv.setImage(im1);  break;
                }
                tilehopital.getChildren().add(imv);
            }
        }
    }

    private void initAnimation() {
        gameloop = new Timeline();
        temps = 0;
        gameloop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.017), ev -> {

            //Déplacement ennemis + Dégât patient
            if (temps % 12 == 0) {
                for (Ennemi e : ennemisActifs) {
                    if (e.estVivant()) e.deplacer(distMap);
                }
                for (Ennemi e : ennemisActifs) {
                    if (e.estVivant() && e.getX() == 23 * 32 && e.getY() == 12 * 32 && patient.estVivant()) {
                        patient.setPv(patient.getPv() - e.getAttaque());
                        if (patient.getPv() <= 0) {
                            patient.setPv(0);
                            labelPV.setText("0 / MORT");
                        } else {
                            labelPV.setText(patient.getPv() + " / " + patient.getPvMax());
                        }
                    }
                }
            }

            //Spawn vague
            if (ennemisRestantsDansVague > 0) {
                timerSpawn++;
                if (timerSpawn >= intervalleSpawn) {
                    timerSpawn = 0;
                    ennemisRestantsDansVague--;

                    Gastrique nouvelEnnemi = new Gastrique();
                    nouvelEnnemi.setX(16 * 32);
                    nouvelEnnemi.setY(0 * 32);
                    ennemisActifs.add(nouvelEnnemi);

                    ImageView vueEnnemi = new ImageView(chargerImage("gastrique.png"));
                    vueEnnemi.setFitWidth(32);
                    vueEnnemi.setFitHeight(32);
                    vueEnnemi.translateXProperty().bind(nouvelEnnemi.xProperty());
                    vueEnnemi.translateYProperty().bind(nouvelEnnemi.yProperty());
                    panneauJeu.getChildren().add(vueEnnemi);
                }
            }


            //tickCooldown : on décrémente le compteur de chaque tour à chaque frame
            for (Tour tour : toursActives) {
                tour.tickCooldown();
            }

            //Tours : tir sur le premier ennemi à portée
            for (Tour tour : toursActives) {
                for (Ennemi e : ennemisActifs) {
                    if (e.estVivant() && tour.peutTirer(e)) {
                        Projectile proj = new Projectile(tour.getX() + 16, tour.getY() + 16, e, tour.getDegat());
                        projectilesActifs.add(proj);

                        Circle cercle = new Circle(5, Color.ORANGE);
                        cercle.setLayoutX(proj.getX());
                        cercle.setLayoutY(proj.getY());
                        panneauJeu.getChildren().add(cercle);
                        vuesProjectiles.put(proj, cercle);


                    }
                }
            }

            //Déplacer projectiles + mettre à jour position
            for (Projectile proj : projectilesActifs) {
                proj.deplacer();
                Circle cercle = vuesProjectiles.get(proj);
                if (cercle != null) {
                    cercle.setLayoutX(proj.getX());
                    cercle.setLayoutY(proj.getY());
                }
            }

            //Retirer les projectiles arrivés
            List<Projectile> aSupprimer = new ArrayList<>();
            for (Projectile proj : projectilesActifs) {
                if (!proj.estActif()) {
                    aSupprimer.add(proj);
                    Circle cercle = vuesProjectiles.remove(proj);
                    if (cercle != null) panneauJeu.getChildren().remove(cercle);
                }
            }
            projectilesActifs.removeAll(aSupprimer);

            temps++;
        });

        gameloop.getKeyFrames().add(keyFrame);
    }

    @FXML
    private void lancerVague() {
        if (ennemisRestantsDansVague == 0) {
            ennemisRestantsDansVague = 5;
            timerSpawn = 0;
            System.out.println("Vague lancée !");
        }
    }

    @FXML
    private void ajouteBonus() {
        if (patient.estVivant()) {
            patient.soigner(5);
            labelPV.setText(patient.getPv() + " / " + patient.getPvMax());
        }
    }

    private void creerBoutonInventaire() {
        btnInterne.setOnAction(event -> {
            if (patient.getPv() == 0) {
                System.out.println("GAME OVER : Le patient est mort ! Impossible de poser une tour");
                return;
            }
            this.idTourSelect = 1;
            System.out.println("Interne sélectionné, clique sur la map pour poser");
        });

        panneauJeu.setOnMouseClicked(event -> {
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;



            if (terrain.getMap()[ligne][col] == 1) {
                terrain.getMap()[ligne][col] = 12;

                InterneDeGarde nouvelleTour = new InterneDeGarde();
                nouvelleTour.setX(col * 32);
                nouvelleTour.setY(ligne * 32);
                toursActives.add(nouvelleTour);

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

        panneauJeu.setOnMouseMoved(event -> {
            if (caseHighlight != null) panneauJeu.getChildren().remove(caseHighlight);
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            caseHighlight = new Rectangle(32, 32);
            caseHighlight.setLayoutX(col * 32);
            caseHighlight.setLayoutY(ligne * 32);
            caseHighlight.setMouseTransparent(true);
            if (terrain.getMap()[ligne][col] == 1) {
                caseHighlight.setFill(Color.rgb(0, 255, 0, 0.4));
            } else {//12 si tour posée
                caseHighlight.setFill(Color.rgb(255, 0, 0, 0.4));
            }

            panneauJeu.getChildren().add(caseHighlight);
        });
    }
}