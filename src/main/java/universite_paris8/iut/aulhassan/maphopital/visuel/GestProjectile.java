package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import universite_paris8.iut.aulhassan.maphopital.Controleur;
import universite_paris8.iut.aulhassan.maphopital.modele.*;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestProjectile {

    private Pane panneauJeu;
    private EnvironnementJeu environnement;
    private Controleur controleur;

    private List<Projectile> projectilesActifs = new ArrayList<>();
    private Map<Projectile, Node> vuesProjectiles = new HashMap<>();

    public GestProjectile(Pane panneauJeu, EnvironnementJeu environnement, Controleur controleur) {
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;
        this.controleur = controleur;
    }

    public void tiquerProjectiles() {
        // 1. tickCooldown : on décrémente le compteur de chaque tour
        for (Tour tour : environnement.getToursActives()) {
            tour.tickCooldown();
        }

        // 2. Tours : tir sur le premier ennemi à portée
        for (Tour tour : environnement.getToursActives()) {
            for (Ennemi e : environnement.getEnnemisActifs()) {
                if (e.estVivant() && tour.peutTirer(e)) {
                    Projectile proj = new Projectile(tour.getX() + 16, tour.getY() + 16, e, tour.getDegat());
                    projectilesActifs.add(proj);

                    ImageView vueProj = new ImageView(controleur.chargerImage(tour.getNomImageProjectile()));//ajout des attributs dans la classe tour= pas de repetition de code
                    int taille = tour.getTailleProjectile();

                    vueProj.setFitWidth(taille);
                    vueProj.setFitHeight(taille);

                    vueProj.setLayoutX(proj.getX());
                    vueProj.setLayoutY(proj.getY());

                    panneauJeu.getChildren().add(vueProj);
                    vuesProjectiles.put(proj, vueProj);
                }
            }
        }

        // 3. Déplacer projectiles + mettre à jour la position du proj
        for (Projectile proj : projectilesActifs) {
            proj.deplacer();
            Node vue = vuesProjectiles.get(proj);
            if (vue != null) {
                int taille = (int) vue.getBoundsInLocal().getWidth();//seulement le gel qui a un etaille de 62
                 if (taille==16) {
                     vue.setLayoutX(proj.getX());
                     vue.setLayoutY(proj.getY());
                 }
            }
        }

        // 4. Retirer les projectiles arrivés (Moteur mathématique + Moteur graphique)
        List<Projectile> aSupprimer = new ArrayList<>();
        for (Projectile proj : projectilesActifs) {
            if (!proj.estActif()) {
                aSupprimer.add(proj);
                Node vue = vuesProjectiles.remove(proj);
                if (vue != null) {
                    panneauJeu.getChildren().remove(vue);
                }
            }
        }
        projectilesActifs.removeAll(aSupprimer);
    }
}