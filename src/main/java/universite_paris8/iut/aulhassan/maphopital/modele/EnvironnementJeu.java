package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.BFS;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Graphes;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.MouchoirEnrhumé;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.GestionMouchoirs;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Masquier;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.ArrayList;
import java.util.List;

public class EnvironnementJeu {

    private Terrain terrain;
    private Patient patient;
    private SimpleIntegerProperty budget = new SimpleIntegerProperty(200);

    private List<Tour> toursActives;
    private ObservableList<Ennemi> ennemisActifs = FXCollections.observableArrayList();
    private ObservableList<Projectile> projectilesActifs = FXCollections.observableArrayList();

    private GestionMouchoirs gestionMouchoirs;

    private Graphes graphe;
    private BFS bfs;
    private Sommet cible;
    private List<Sommet> spawns;


    public EnvironnementJeu() {
        this.terrain = new Terrain();
        this.patient = new Patient();

        graphe = new Graphes(terrain);
        cible = graphe.getSommet(23, 12);
        bfs = new BFS(graphe, cible);

        this.gestionMouchoirs = new GestionMouchoirs(bfs);
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
        faireAgirTours();
        deplacerProjectiles();

        if (temps % 12 == 0) {
            faireAvancerEnnemis();
        }
    }

    private void faireAvancerEnnemis() {
        for (Ennemi e : ennemisActifs) {
            if (e.estVivant()) {
                e.deplacer();
                gererPouvoirsEnnemis(e);
                attaquerPatientSiArrive(e);
            }
        }
    }

    private void attaquerPatientSiArrive(Ennemi e) {
        if (e.getX() == cible.getX() * 32 && e.getY() == cible.getY() * 32 && patient.estVivant()) {//virus arrivé au patient?
            patient.setPv(patient.getPv() - e.getAttaque());
        }
    }

    private void deplacerProjectiles() {
        for (int i = projectilesActifs.size() - 1; i >= 0; i--) {
            Projectile proj = projectilesActifs.get(i);
            proj.deplacer();
            if (!proj.estActif()) {
                projectilesActifs.remove(i);
            }
        }
    }

    private void faireAgirTours() {
        for (Tour tour : toursActives) {
            Projectile proj = tour.agir(ennemisActifs);
            if (proj != null) {
                projectilesActifs.add(proj);
            }
        }
    }

    public void mettreAJourMalusTours() {
        for (Tour tour : toursActives) {
            double multiplicateur = 1.0;
            for (Ennemi e : ennemisActifs) {
                multiplicateur = Math.max(multiplicateur, e.getMalusCooldownSur(tour));
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
        if (tour instanceof Masquier) {
            terrain.getMap()[ligne][col] = 0;
        } else {
            terrain.getMap()[ligne][col] = 1;
        }
    }

    private void gererPouvoirsEnnemis(Ennemi e) {
        e.utiliserPouvoir(gestionMouchoirs);
    }

    public GestionMouchoirs getGestionMouchoirs() { return gestionMouchoirs;}


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



    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }

    public List<Tour> getToursActives() { return toursActives; }
    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }
    public ObservableList<Projectile> getProjectilesActifs() { return projectilesActifs; }

    public SimpleIntegerProperty budgetProperty() { return this.budget; }
    public int getBudget() { return this.budget.get(); }

    public BFS getBfs() { return bfs; }
    public Sommet getCible() { return cible; }
    public List<Sommet> getSpawns() { return spawns; }
    public Graphes getGraphe() { return graphe; }

}