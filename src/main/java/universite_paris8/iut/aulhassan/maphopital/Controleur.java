package universite_paris8.iut.aulhassan.maphopital;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Gastrique;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Timeline gameloop;
    private int temps;

    @FXML
    private TilePane tilehopital;

    @FXML
    private Pane panneauJeu; //superposition avec le tilepane sinon decallage des images

    private Terrain terrain;
    private Ennemi ennemi1;
    private Gastrique gastrique;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        this.terrain = new Terrain();
        this.ennemi1 = new Ennemi();
        this.gastrique = new Gastrique();
        creerVueModele();


        ImageView rondGastrique = new ImageView(chargerImage("test_gastrique.png"));

        rondGastrique.setFitWidth(32);
        rondGastrique.setFitHeight(32);
        gastrique.setX(10);
        gastrique.setY(10);

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

        //tilehopital.getChildren().clear();
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
                    if(temps==100){
                        System.out.println("fini");
                        gameloop.stop();
                    }
                    else if (temps%5==0){
                        System.out.println("un tour");
                        gastrique.setX(gastrique.getX()+5);
                        gastrique.setY(gastrique.getY()+5);

                    }
                    temps++;
                })
        );
        gameloop.getKeyFrames().add(keyFrame);
    }



}
