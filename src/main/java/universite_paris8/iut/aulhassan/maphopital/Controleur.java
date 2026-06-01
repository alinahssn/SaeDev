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
import javafx.util.Duration;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.control.Label;
import java.awt.dnd.DragSource;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Timeline gameloop;
    private int temps;
    private Rectangle caseHighlight = null;

    @FXML
    private Button btnInterne;

    @FXML
    private VBox panneauTour;

    private int idTourSelect = -1;//quelle tour jai select


    @FXML
    private TilePane tilehopital;

    @FXML
    private Button btnBonus;

    @FXML
    private Label labelPV;

    @FXML
    private Pane panneauJeu; //superposition avec le tilepane sinon decallage des images

    private Patient patient;
    private Terrain terrain;
    private Ennemi ennemi1;
    private Gastrique gastrique;
    private int[][] distMap;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        this.terrain = new Terrain();
        this.ennemi1 = new Ennemi();
        this.patient = new Patient();
        this.gastrique = new Gastrique();
        creerVueModele();

        BFS bfs = new BFS(terrain, 23, 12);
        this.distMap = bfs.getDistMap();

        creerBoutonInventaire();

        ImageView rondGastrique = new ImageView(chargerImage("test_gastrique.png"));

        rondGastrique.setFitWidth(32);
        rondGastrique.setFitHeight(32);
        gastrique.setX(16*32);
        gastrique.setY(0*32);

        rondGastrique.translateXProperty().bind(gastrique.xProperty());
        rondGastrique.translateYProperty().bind(gastrique.yProperty());
        panneauJeu.getChildren().add(rondGastrique);

        initAnimation();
        gameloop.play();

    }


    private Image chargerImage(String nomFichier) {
        URL url = getClass().getResource("image/" + nomFichier);
        return new Image(String.valueOf(url));
    }

    public void creerVueModele() {
        Image im0 = chargerImage("solhopital.png");//placer les tours
        Image im1 = chargerImage("solchambre.png");//chemin pr les ennemis
        Image im2 = chargerImage("mur.png");
        Image im3 = chargerImage("lit.png");
        Image im4 = chargerImage("chevet.png");
        Image im5 = chargerImage("chaise.png");
        Image im6 = chargerImage("machine.png");
        Image im7 = chargerImage("bureau.png");
        Image im8 = chargerImage("chaise2.png");
        Image im9 = chargerImage("distrib.png");
        Image im10 = chargerImage("plante.png");
        Image im11 = chargerImage("lit2.png");

        //TailSet

        for (int i = 0; i < terrain.getHauteur(); i++) {
            for (int j = 0; j < terrain.getLargeur(); j++) {

                ImageView imv = new ImageView();
                imv.setFitWidth(32);
                imv.setFitHeight(32);

                int valeurTuile = terrain.getMap()[i][j];

                switch (valeurTuile) {
                    case 0:
                        imv.setImage(im1);
                        break;
                    case 1:
                        imv.setImage(im0);
                        break;
                    case 2:
                        imv.setImage(im2);
                        break;
                    case 3:
                        imv.setImage(im3);
                        break;
                    case 4:
                        imv.setImage(im4);
                        break;
                    case 5:
                        imv.setImage(im5);
                        break;
                    case 6:
                        imv.setImage(im6);
                        break;
                    case 7:
                        imv.setImage(im7);
                        break;
                    case 8:
                        imv.setImage(im8);
                        break;
                    case 9:
                        imv.setImage(im9);
                        break;
                    case 10:
                        imv.setImage(im10);
                        break;
                    case 11:
                        imv.setImage(im11);
                        break;
                    default:
                        imv.setImage(im1);
                        break;
                }
                tilehopital.getChildren().add(imv);
            }
        }
    }

    private void initAnimation(){
        gameloop = new Timeline();
        temps=0;
        gameloop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.017),
                (ev ->{
                    if (temps%5==0){
                        gastrique.deplacer(distMap);

                    }
                    else if (gastrique.getX()==23*32 && gastrique.getY()==12*32 && patient.estVivant()){
                        int nouveauPv = patient.getPv()-gastrique.getAttaque();
                        patient.setPv(nouveauPv);
                        labelPV.setText(patient.getPv() + " / " + patient.getPvMax());


                        //gastrique.setX(gastrique.getX()+5);
                        //gastrique.setY(gastrique.getY()+5);

                        if(patient.getPv()<=0){
                            patient.setPv(0);
                            labelPV.setText("0" + " / " + "MORT");

                        }


                    }
                    System.out.println(patient.estVivant());
                    temps++;
                })
        );
        gameloop.getKeyFrames().add(keyFrame);
    }

    @FXML
    private void ajouteBonus(){
        if(patient.estVivant()){
            patient.soigner(5);
            labelPV.setText(patient.getPv() + " / " + patient.getPvMax());
        }
    }

    private void creerBoutonInventaire() {
        btnInterne.setOnAction(event -> {
            this.idTourSelect = 1;
            System.out.println("Interne sélectionné, clique sur la map pour poser");
        });

        panneauJeu.setOnMouseClicked(event -> {
            if (idTourSelect == -1) return; // aucune tour sélectionnée

            //clic en case de grille
            int col   = (int) event.getX() / 32; //convertir pixel en tuile
            int ligne = (int) event.getY() / 32;

            //case valide pour poser la tour
            if (terrain.getMap()[ligne][col] == 1) {
                terrain.getMap()[ligne][col] = 12;
                ImageView tourPosee = new ImageView(
                        ((ImageView) btnInterne.getGraphic()).getImage()//balises de la vue
                );
                tourPosee.setFitWidth(32);
                tourPosee.setFitHeight(32);//image taille d'une case
                tourPosee.setLayoutX(col * 32);
                tourPosee.setLayoutY(ligne * 32); //reconverti en pixels
                panneauJeu.getChildren().add(tourPosee);

                idTourSelect = -1;
                System.out.println("Tour posée en [" + ligne + "][" + col + "]");
            } else {
                System.out.println("Case invalide !");
            }
        });

        panneauJeu.setOnMouseMoved(event -> {
            if (caseHighlight != null) {
                panneauJeu.getChildren().remove(caseHighlight);//on l'enlève à chaque mouvement
            }
            if (idTourSelect == -1) return;

            int col   = (int) event.getX() / 32;
            int ligne = (int) event.getY() / 32;

            caseHighlight = new Rectangle(32, 32);
            caseHighlight.setLayoutX(col * 32);
            caseHighlight.setLayoutY(ligne * 32);
            caseHighlight.setMouseTransparent(true);//pas bloquer les clics

            if (terrain.getMap()[ligne][col] == 1) {
                caseHighlight.setFill(Color.rgb(0, 255, 0, 0.4));
            } else {
                caseHighlight.setFill(Color.rgb(255, 0, 0, 0.4));
            }

            panneauJeu.getChildren().add(caseHighlight);
        });
    }




}
