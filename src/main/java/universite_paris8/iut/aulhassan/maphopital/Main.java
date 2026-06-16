package universite_paris8.iut.aulhassan.maphopital;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);


        stage.setTitle("CODE ROUGE : INVASION À L'HÔPITAL");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
