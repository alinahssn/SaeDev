package universite_paris8.iut.aulhassan.maphopital;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.MouchoirEnrhumé;
import universite_paris8.iut.aulhassan.maphopital.visuel.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Timeline gameloop;
    private int temps;
    private GestClic clictours;

    @FXML private Button btnInterne;
    @FXML private Button btnGel;
    @FXML private Button btnBranca;
    @FXML private Button btnAne;
    @FXML private Button btnMasque;
    @FXML private Button btnChir;
    @FXML private Button btnRevente;
    @FXML private TilePane tilehopital;
    @FXML private Label labelPV;
    @FXML private Label labelBudget;
    @FXML private Pane panneauJeu;
    @FXML private Label labelVague;
    @FXML private Button btnBonus;
    @FXML private StackPane ecranGameOver;

    private Vague vague;
    private GestProjectile gestProjectile;
    private VueMouchoirs vueMouchoirs;
    private VueNouveauxEnnemis vueNouveauxEnnemis;
    private VueTour vueTour;
    private VueTerrain vueTerrain;
    private EnvironnementJeu environnement;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.environnement = new EnvironnementJeu();

        this.vueTerrain = new VueTerrain(tilehopital, environnement.getTerrain());
        vueTerrain.dessinerCartographie();

        this.vueTour = new VueTour(panneauJeu, environnement);
        this.clictours = new GestClic(btnInterne, btnGel, btnBranca, btnAne, btnMasque, btnChir, btnRevente, panneauJeu, environnement, vueTour);
        this.clictours.configurer();

        this.gestProjectile = new GestProjectile(panneauJeu, environnement);
        this.vague = new Vague(environnement);
        this.vueMouchoirs = new VueMouchoirs(environnement, panneauJeu);
        this.vueNouveauxEnnemis = new VueNouveauxEnnemis(environnement, panneauJeu);

        new VueBouton(environnement, vague, labelPV, labelBudget, labelVague, btnInterne, btnGel, btnBranca, btnAne, btnMasque, btnChir, btnRevente,btnBonus);
        initAnimation();
        gameloop.play();
    }

    private void initAnimation() {
        gameloop = new Timeline();
        temps = 0;
        gameloop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.017), ev -> {

            environnement.unTour(temps);

            Ennemi nouvelEnnemi = vague.tickSpawn();
            if (nouvelEnnemi != null) {
                environnement.getEnnemisActifs().add(nouvelEnnemi);
                new VueEnnemi(nouvelEnnemi, panneauJeu, environnement);
            }

            if (!environnement.getPatient().estVivant()) {
                ecranGameOver.setVisible(true);
                gameloop.stop();
            }

            vueTour.supprimerMasquiersDetruits();
            vueMouchoirs.mettreAJour();
            vueNouveauxEnnemis.mettreAJour();
            gestProjectile.tiquerProjectiles();

            vueTour.mettreAJourPositionsTours();

            temps++;
        });

        gameloop.getKeyFrames().add(keyFrame);
    }

    @FXML
    private void lancerVague() {
        if (!environnement.getEnnemisActifs().isEmpty()) {
            System.out.println("Impossible : il  reste des ennemis sur la carte !");
            return;
        }
        vague.lancerVague();
        vueTerrain.afficherSpawns(environnement.getSpawns(), vague.getSpawnsActifsVague());
    }

    @FXML
    private void ajouteBonus() {
        int coutBonus = 50;
        if (environnement.getPatient().estVivant()) {
                if (environnement.dépense(coutBonus)) {
                    environnement.getPatient().soigner(5);
                    System.out.println("Bonus acheté ! -" + coutBonus + "€ (Patient soigné)");

                } else {

                    System.out.println("Pas assez de budget pour acheter le bonus ! Il faut " + coutBonus + "€.");
                }
        }
    }
    @FXML
    private void retourMenu() throws IOException {
        Stage stage = (Stage) panneauJeu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }


}