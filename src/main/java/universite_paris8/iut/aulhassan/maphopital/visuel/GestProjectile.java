package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestProjectile {

    private Pane panneauJeu;
    private EnvironnementJeu environnement;

    private List<Projectile> projectilesActifs = new ArrayList<>();
    private Map<Projectile, Node> vuesProjectiles = new HashMap<>();

    public GestProjectile(Pane panneauJeu, EnvironnementJeu environnement) {
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;
    }

    public void tiquerProjectiles() {
        for (Tour tour : environnement.getToursActives()) {
            Projectile proj = tour.agir(environnement.getEnnemisActifs());

            if (proj != null) {
                projectilesActifs.add(proj);

                ImageView vueProj = new ImageView(ChargeurImage.charger(tour.getNomImageProjectile()));
                int taille = tour.getTailleProjectile();
                vueProj.setFitWidth(taille);
                vueProj.setFitHeight(taille);
                vueProj.setLayoutX(proj.getX());
                vueProj.setLayoutY(proj.getY());

                panneauJeu.getChildren().add(vueProj);
                vuesProjectiles.put(proj, vueProj);
            }
        }


        for (Projectile proj : projectilesActifs) {
            proj.deplacer();
            Node vue = vuesProjectiles.get(proj);
            if (vue != null) {
                if (!proj.estFixe()) {
                    vue.setLayoutX(proj.getX());
                    vue.setLayoutY(proj.getY());
                }
            }
        }

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