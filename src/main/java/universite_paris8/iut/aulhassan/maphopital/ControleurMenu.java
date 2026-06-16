package universite_paris8.iut.aulhassan.maphopital;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;


public class ControleurMenu {


    @FXML private VBox ecranPrincipal;
    @FXML private StackPane ecranInfos;
    @FXML private StackPane ecranRegles;


    @FXML
    private void jouer() throws IOException {
        Stage stage = (Stage) ecranPrincipal.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("vue1.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }


    @FXML
    private void ouvrirInfos() {
        ecranInfos.setVisible(true);
    }


    @FXML
    private void ouvrirRegles() {
        ecranRegles.setVisible(true);
    }


    @FXML
    private void fermerPopup() {
        ecranInfos.setVisible(false);
        ecranRegles.setVisible(false);
    }
}
