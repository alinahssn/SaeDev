package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.Image;
import universite_paris8.iut.aulhassan.maphopital.Main;

import java.net.URL;

public class ChargeurImage {

    public static Image charger(String nomFichier) {
        URL url = Main.class.getResource("image/" + nomFichier);
        return new Image(String.valueOf(url));
    }
}