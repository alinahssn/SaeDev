package universite_paris8.iut.aulhassan.maphopital;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private TilePane tilehopital;

    private Terrain terrain;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        this.terrain = new Terrain();
        creerVueModele();
    }

    public void creerVueModele() {
        Image image = new Image ("8.png");
        Image image2 = new Image ("carré-blanc-cadre-gris-seul.png");
        for (int i=0; i<terrain.getHauteur(); i++){
            for (int j=0; j<terrain.getLargeur(); j++){
                if (terrain.getMap()[i][j]==0) {
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    tilehopital.getChildren().add(imageView);
                } else {
                    ImageView imageView = new ImageView();
                    imageView.setImage(image2);
                    tilehopital.getChildren().add(imageView);
                }
            }
        }
    }

}
