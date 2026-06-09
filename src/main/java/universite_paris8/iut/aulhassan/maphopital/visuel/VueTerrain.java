package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.aulhassan.maphopital.Controleur;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

public class VueTerrain {

    private TilePane tilehopital;
    private Terrain terrain;
    private Controleur controleur; // On garde une référence au contrôleur pour utiliser sa méthode chargerImage

    // Le constructeur demande tout ce dont il a besoin pour dessiner
    public VueTerrain(TilePane tilehopital, Terrain terrain, Controleur controleur) {
        this.tilehopital = tilehopital;
        this.terrain = terrain;
        this.controleur = controleur;
    }

    public void dessinerCartographie() {
        Image im0  = controleur.chargerImage("solhopital.png");
        Image im1  = controleur.chargerImage("solchambre.png");
        Image im2  = controleur.chargerImage("mur.png");
        Image im3  = controleur.chargerImage("lit.png");
        Image im4  = controleur.chargerImage("chevet.png");
        Image im5  = controleur.chargerImage("chaise.png");
        Image im6  = controleur.chargerImage("machine.png");
        Image im7  = controleur.chargerImage("bureau.png");
        Image im8  = controleur.chargerImage("chaise2.png");
        Image im9  = controleur.chargerImage("distrib.png");
        Image im10 = controleur.chargerImage("plante.png");
        Image im11 = controleur.chargerImage("lit2.png");

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
}