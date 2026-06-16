package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Brancardier;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Chirurgien;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Masquier;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VueTour {

    private Pane panneau;
    private EnvironnementJeu environnement;
    private Map<Tour, BarreVie> barresVie = new HashMap<>();
    private Map<Chirurgien, ImageView> vueScalpels = new HashMap<>();

    public VueTour(Pane panneau, EnvironnementJeu environnement) {
        this.panneau = panneau;
        this.environnement = environnement;
    }

    public void afficherTour(Tour tour, int col, int ligne, ImageView image) {
        image.setFitWidth(32);
        image.setFitHeight(32);
        image.setLayoutX(col * 32);
        image.setLayoutY(ligne * 32);
        panneau.getChildren().add(image);
        environnement.ajouterVueTour(tour, image);

        if (tour instanceof Masquier masquier) {
            BarreVie barre = new BarreVie(masquier.pvProperty(), masquier.getPvMax());
            barre.positionner(col * 32, ligne * 32 - 10);
            panneau.getChildren().add(barre.getFond());
            panneau.getChildren().add(barre.getBarre());
            barresVie.put(tour, barre);
        }
        if (tour instanceof Chirurgien chirurgien) {
            ImageView scalpel = new ImageView(ChargeurImage.charger("scalpel.png"));

            scalpel.setFitWidth(30);
            scalpel.setFitHeight(30);
            scalpel.setLayoutX((col * 32) + 25);
            scalpel.setLayoutY(ligne * 32);
            scalpel.setVisible(false);

            panneau.getChildren().add(scalpel);
            vueScalpels.put(chirurgien, scalpel);
        }
    }

    public void mettreAJourPositionsTours() {
        for (Tour tour : environnement.getToursActives()) {

            if (tour instanceof Brancardier brancardier) {
                ImageView imageBrancard = environnement.getVueTour(brancardier);
                if (imageBrancard != null) {
                    imageBrancard.setLayoutX(brancardier.getBrancardX());
                    imageBrancard.setLayoutY(brancardier.getBrancardY());
                }
            }

            if (tour instanceof Chirurgien chirurgien) {
                ImageView scalpel = vueScalpels.get(chirurgien);
                if (scalpel != null) {
                    scalpel.setVisible(chirurgien.isaAttaque());
                    scalpel.setFitWidth(50);
                    scalpel.setFitHeight(50);

                    Ennemi cible = chirurgien.getCibleActuelle();
                    if (chirurgien.isaAttaque() && cible != null && cible.estVivant()) {
                        scalpel.setVisible(true);
                        scalpel.setLayoutX(cible.getX());
                        scalpel.setLayoutY(cible.getY());
                    } else {
                        scalpel.setVisible(false);
                    }
                }
            }
        }    }

    public void supprimerTour(Tour tour) {
        ImageView img = environnement.getVueTour(tour);
        if (img != null) panneau.getChildren().remove(img);
        environnement.supprimerVueTour(tour);

        if (tour instanceof Chirurgien chirurgien) {
            ImageView scalpel = vueScalpels.remove(chirurgien);
            if (scalpel != null) panneau.getChildren().remove(scalpel);
        }

        BarreVie barre = barresVie.remove(tour);
        if (barre != null) {
            panneau.getChildren().remove(barre.getFond());
            panneau.getChildren().remove(barre.getBarre());
        }
    }

    public void supprimerMasquiersDetruits() {
        List<Tour> detruits = environnement.retirerMasquiersDetruits();
        for (Tour t : detruits) {
            supprimerTour(t);
        }
    }
}