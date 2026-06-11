package universite_paris8.iut.aulhassan.maphopital;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.visuel.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
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

    private Vague vague;
    private GestProjectile gestProjectile;
    private EnvironnementJeu environnement;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.environnement = new EnvironnementJeu();

        VueTerrain vueTerrain = new VueTerrain(tilehopital, environnement.getTerrain());
        vueTerrain.dessinerCartographie();

        this.clictours = new GestClic(btnInterne, btnGel, btnBranca, btnAne, btnMasque, btnChir, btnRevente, panneauJeu, environnement);
        this.clictours.configurer();

        this.gestProjectile = new GestProjectile(panneauJeu, environnement);
        this.vague = new Vague(environnement);

        new VueBouton(environnement, labelPV, labelBudget); // ← les listeners sont là-dedans

        initAnimation();
        gameloop.play();
    }

    private void initAnimation() {
        gameloop = new Timeline();
        temps = 0;
        gameloop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.017), ev -> {

            if (temps % 12 == 0) {
                for (Ennemi e : environnement.getEnnemisActifs()) {
                    if (e.estVivant()) e.deplacer(environnement.getDistMap());
                }
                for (Ennemi e : environnement.getEnnemisActifs()) {
                    if (e.estVivant() && e.getX() == 23 * 32 && e.getY() == 12 * 32 && environnement.getPatient().estVivant()) {
                        environnement.getPatient().setPv(environnement.getPatient().getPv() - e.getAttaque());
                    }
                }
                temps = 0;
            }

            Ennemi nouvelEnnemi = vague.tickSpawn();
            if (nouvelEnnemi != null) {
                environnement.getEnnemisActifs().add(nouvelEnnemi);
                new VueEnnemi(nouvelEnnemi, panneauJeu, environnement);
            }

            gestProjectile.tiquerProjectiles();
            temps++;
        });

        gameloop.getKeyFrames().add(keyFrame);
    }

    @FXML
    private void lancerVague() {
        vague.lancerVague();
    }

    @FXML
    private void ajouteBonus() {
        if (environnement.getPatient().estVivant()) {
            environnement.getPatient().soigner(5);
        }
    }
}