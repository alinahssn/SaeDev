package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.BFS;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Graphes;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Masquier;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironnementJeu {

    private Terrain terrain;
    private Patient patient;
    private SimpleIntegerProperty budget = new SimpleIntegerProperty(200);

    private List<Tour> toursActives;
    private ObservableList<Ennemi> ennemisActifs = FXCollections.observableArrayList();
    private ObservableList<Projectile> projectilesActifs = FXCollections.observableArrayList();    private List<MouchoirEnrhumé> mouchoirsActifs;
    private List<MouchoirEnrhumé> nouveauxMouchoirs = new ArrayList<>();
    private List<MouchoirEnrhumé> mouchoirsSupprimes = new ArrayList<>();
    private List<Ennemi> nouveauxEnnemis = new ArrayList<>();

    private Graphes graphe;
    private BFS bfs;
    private Sommet cible;
    private List<Sommet> spawns;
    private Map<Tour, ImageView> vuesTours = new HashMap<>();


    public EnvironnementJeu() {
        this.terrain = new Terrain();
        this.patient = new Patient();
        this.mouchoirsActifs = new ArrayList<>();

        graphe = new Graphes(terrain);
        cible = graphe.getSommet(23, 12);
        bfs = new BFS(graphe, cible);

        spawns = new ArrayList<>();
        spawns.add(graphe.getSommet(16, 0));
        spawns.add(graphe.getSommet(47, 0));
        spawns.add(graphe.getSommet(47, 26));
        spawns.add(graphe.getSommet(37, 26));
        spawns.add(graphe.getSommet(0, 13));

        this.toursActives = new ArrayList<>();
    }

    public void unTour(int temps) {

        this.mettreAJourMalusTours();

        if (temps % 12 == 0) {
            for (Ennemi e : ennemisActifs) {
                if (e.estVivant()) {
                    e.deplacer();
                    gererPouvoirsEnnemis(e);
                    if (e.getX() == cible.getX() * 32 && e.getY() == cible.getY() * 32 && patient.estVivant()) {//virus arrivé au patient?
                        patient.setPv(patient.getPv() - e.getAttaque());
                    }
                }
            }
            gererMasquiersDetruits();
        }
    }

    public void mettreAJourMalusTours() {
        for (Tour tour : toursActives) {
            double multiplicateur = 1.0;
            for (Ennemi e : ennemisActifs) {
                if (e instanceof Grippé && e.estVivant()) {
                    int dx = tour.getX() - e.getX();
                    int dy = tour.getY() - e.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance <= Grippé.PORTEE_RALENTISSEMENT * 32) {
                        multiplicateur = 2.0;
                        break;
                    }
                }
            }
            tour.setMultiplicateurCooldownProjectil(multiplicateur);
        }
    }

    public boolean poserTour(Tour tour, int col, int ligne) {
        if (!dépense(tour.getCout())) return false;
        terrain.getMap()[ligne][col] = 12;
        tour.setX(col * 32);
        tour.setY(ligne * 32);
        toursActives.add(tour);
        return true;
    }

    public void revendreTour(Tour tour) {
        int col = tour.getX() / 32;
        int ligne = tour.getY() / 32;
        ajouterBudget((int) (tour.getCout() * 0.7));
        toursActives.remove(tour);
        supprimerVueTour(tour);
        if (tour instanceof Masquier) {
            terrain.getMap()[ligne][col] = 0;
        } else {
            terrain.getMap()[ligne][col] = 1;
        }
    }

    private void gererPouvoirsEnnemis(Ennemi e) {
        if (e instanceof Enrhumé enrhumé) {
            MouchoirEnrhumé mouchoir = enrhumé.utiliserPouvoir();
            if (mouchoir != null) {
                mouchoirsActifs.add(mouchoir);
                nouveauxMouchoirs.add(mouchoir);
            }
        }
        if(e instanceof Covidé covidé) {
            for (MouchoirEnrhumé mouchoir : mouchoirsActifs) {
            Enrhumé nouvelEnrhume = covidé.utiliserPouvoir(mouchoir);
                if (nouvelEnrhume != null) {
                    Sommet spawn = new Sommet(
                            nouvelEnrhume.getSpawnX() / 32,
                            nouvelEnrhume.getSpawnY() / 32
                    );

                    ArrayList<Sommet> chemin = bfs.cheminVersSource(spawn);
                    java.util.Collections.reverse(chemin);
                    nouvelEnrhume.setChemin(chemin);
                    ennemisActifs.add(nouvelEnrhume);
                    nouveauxEnnemis.add(nouvelEnrhume);
                    mouchoirsSupprimes.add(mouchoir);
                }
            }
            mouchoirsActifs.removeAll(mouchoirsSupprimes);
        }
    }


    public void viderNouveauxMouchoirs() {
        nouveauxMouchoirs.clear();
    }
    public void viderMouchoirsSupprimes() {
        mouchoirsSupprimes.clear();
    }


    public List<MouchoirEnrhumé> getNouveauxMouchoirs() {
        return nouveauxMouchoirs;
    }
    public List<MouchoirEnrhumé> getMouchoirsSupprimes() {
        return mouchoirsSupprimes;
    }

    public List<Ennemi> getNouveauxEnnemis() {
        return nouveauxEnnemis;
    }
    public void viderNouveauxEnnemis() {
        nouveauxEnnemis.clear();
    }

    private void gererMasquiersDetruits() {
        retirerMasquiersDetruits();
    }

    public List<Tour> retirerMasquiersDetruits() {
        List<Tour> detruits = new ArrayList<>();
        for (Tour t : toursActives) {
            if (t instanceof Masquier masquier && masquier.estDetruit()) {
                detruits.add(t);//supp si trouve un masquier à 0
            }
        }
        for (Tour t : detruits) {
            int col = t.getX() / 32;
            int ligne = t.getY() / 32;
            terrain.getMap()[ligne][col] = 0;
            toursActives.remove(t);
        }
        return detruits;
    }

    public void ajouterBudget(int montant) { this.budget.set(this.budget.get() + montant); }

    public boolean dépense(int montant) {
        if (this.budget.get() >= montant) {
            this.budget.set(this.budget.get() - montant);
            return true;
        }
        return false;
    }


    public void ajouterVueTour(Tour t, ImageView img) { vuesTours.put(t, img); }
    public ImageView getVueTour(Tour t) { return vuesTours.get(t); }
    public void supprimerVueTour(Tour t) { vuesTours.remove(t); }



    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }

    public List<Tour> getToursActives() { return toursActives; }
    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }
    public List<Projectile> getProjectilesActifs() { return projectilesActifs; }

    public List<MouchoirEnrhumé> getMouchoirsActifs() { return mouchoirsActifs;}

    public SimpleIntegerProperty budgetProperty() { return this.budget; }
    public int getBudget() { return this.budget.get(); }

    public BFS getBfs() { return bfs; }
    public Sommet getCible() { return cible; }
    public List<Sommet> getSpawns() { return spawns; }
    public Graphes getGraphe() { return graphe; }

}