package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.BFS;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Graphes;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Masquier;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.MouchoirEnrhumé;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Enrhumé;

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
    private List<Projectile> projectilesActifs;
    private List<MouchoirEnrhumé> mouchoirsActifs;
    private List<MouchoirEnrhumé> nouveauxMouchoirs = new ArrayList<>();
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
        this.projectilesActifs = FXCollections.observableArrayList();
    }

    public void unTour(int temps) {
        if (temps % 12 == 0) {
            for (Ennemi e : ennemisActifs) {
                if (e.estVivant()) {
                    e.deplacer();
                    gererPouvoirsEnnemis(e);
                    if (e.getX() == cible.getX() * 32 && e.getY() == cible.getY() * 32 && patient.estVivant()) {
                        patient.setPv(patient.getPv() - e.getAttaque());
                    }
                }
            }
            gererMasquiersDetruits();
        }
    }

    // ── Logique tours ──

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
    }

    public List<MouchoirEnrhumé> getNouveauxMouchoirs() {
        return nouveauxMouchoirs;
    }

    public void viderNouveauxMouchoirs() {
        nouveauxMouchoirs.clear();
    }
    private void gererMasquiersDetruits() {
        retirerMasquiersDetruits();
    }

    public List<Tour> retirerMasquiersDetruits() {
        List<Tour> detruits = new ArrayList<>();
        for (Tour t : toursActives) {
            if (t instanceof Masquier masquier && masquier.estDetruit()) {
                detruits.add(t);
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

    // ── Vues tours ──

    public void ajouterVueTour(Tour t, ImageView img) { vuesTours.put(t, img); }
    public ImageView getVueTour(Tour t) { return vuesTours.get(t); }
    public void supprimerVueTour(Tour t) { vuesTours.remove(t); }

    // ── Getters ──

    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }
    public int getBudget() { return this.budget.get(); }
    public List<Tour> getToursActives() { return toursActives; }
    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }
    public List<Projectile> getProjectilesActifs() { return projectilesActifs; }
    public SimpleIntegerProperty budgetProperty() { return this.budget; }
    public BFS getBfs() { return bfs; }
    public List<MouchoirEnrhumé> getMouchoirsActifs() { return mouchoirsActifs;}
    public Sommet getCible() { return cible; }
    public List<Sommet> getSpawns() { return spawns; }
    public Graphes getGraphe() { return graphe; }

    public void ajouterBudget(int montant) { this.budget.set(this.budget.get() + montant); }

    public boolean dépense(int montant) {
        if (this.budget.get() >= montant) {
            this.budget.set(this.budget.get() - montant);
            return true;
        }
        return false;
    }
}