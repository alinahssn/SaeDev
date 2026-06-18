package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import universite_paris8.iut.aulhassan.maphopital.modele.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Grippé;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.HashMap;
import java.util.Map;

public class GestProjectile {

    private Pane panneauJeu;
    private EnvironnementJeu environnement;
    private Map<Projectile, Node> vuesProjectiles = new HashMap<>();

    public GestProjectile(Pane panneauJeu, EnvironnementJeu environnement) {
        this.panneauJeu = panneauJeu;
        this.environnement = environnement;

        environnement.getProjectilesActifs().addListener((ListChangeListener<Projectile>) c -> {
            while (c.next()) {

                if (c.wasAdded()) {
                    for (Projectile proj : c.getAddedSubList()) {
                        ImageView vueProj = new ImageView(ChargeurImage.charger(proj.getNomImage()));
                        vueProj.setFitWidth(16);
                        vueProj.setFitWidth(proj.getTaille());
                        vueProj.setFitHeight(proj.getTaille());
                        vueProj.setLayoutX(proj.getX());
                        vueProj.setLayoutY(proj.getY());

                        panneauJeu.getChildren().add(vueProj);
                        vuesProjectiles.put(proj, vueProj);
                    }
                }
                if (c.wasRemoved()) {
                    for (Projectile proj : c.getRemoved()) {
                        Node vue = vuesProjectiles.remove(proj);
                        if (vue != null) panneauJeu.getChildren().remove(vue);
                    }
                }
            }
        });
    }

    public void tiquerProjectiles() {
        for (Projectile proj : environnement.getProjectilesActifs()) {
            Node vue = vuesProjectiles.get(proj);
            if (vue != null && !proj.estFixe()) {
                vue.setLayoutX(proj.getX());
                vue.setLayoutY(proj.getY());
            }
        }
    }
}