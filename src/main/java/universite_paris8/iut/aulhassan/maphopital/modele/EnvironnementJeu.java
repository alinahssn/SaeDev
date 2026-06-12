package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.BFS;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Graphes;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.ArrayList;
import java.util.List;

public class EnvironnementJeu {

    // On regroupe les objets du modèle qu'on a déjà
    private Terrain terrain;
    private Patient patient;
    private SimpleIntegerProperty budget = new SimpleIntegerProperty(200);

    // On regroupe les listes de données qui étaient dans le contrôleur
    private List<Tour> toursActives;
    private ObservableList<Ennemi> ennemisActifs = FXCollections.observableArrayList();
    private ObservableList<Projectile> projectilesActifs = FXCollections.observableArrayList();

    private Graphes graphe;
    private BFS bfs;
    private Sommet cible;
    private List<Sommet> spawns;


    public EnvironnementJeu() {
        // Au moment où on crée le jeu, on initialise tout le modèle
        this.terrain = new Terrain();
        this.patient = new Patient();
        graphe = new Graphes(terrain);

        Sommet source = graphe.getSommet(16, 0);  // position de départ des ennemis
        cible = graphe.getSommet(23, 12);  // le lit

        // BFS calculé depuis la cible : donne le chemin vers la cible
        // depuis n'importe quel sommet du graphe (donc depuis chaque spawn)
        bfs = new BFS(graphe, cible);

        // Les 5 points de spawn possibles
        spawns = new ArrayList<>();
        spawns.add(graphe.getSommet(16, 0));   // Spawn 1
        spawns.add(graphe.getSommet(47, 0));   // Spawn 2
        spawns.add(graphe.getSommet(47, 26));  // Spawn 3
        spawns.add(graphe.getSommet(37, 26));  // Spawn 4
        spawns.add(graphe.getSommet(0, 13));   // Spawn 5

        // On initialise nos listes vides
        this.toursActives = new ArrayList<>();
        this.projectilesActifs = FXCollections.observableArrayList();    }

    public void unTour(int temps) {

        if (temps % 12 == 0) {
            for (Ennemi e : ennemisActifs) {
                if (e.estVivant()) {
                    e.deplacer();

                    if (e.getX() == cible.getX() * 32 && e.getY() == cible.getY() * 32 && patient.estVivant()) {
                        patient.setPv(patient.getPv() - e.getAttaque());
                    }
                }
            }
        }
        for (Tour tour : toursActives) {
            Projectile nouveauProj = tour.agir(ennemisActifs);
            // Si la tour a décidé de tirer, on ajoute le projectile à la liste globale
            if (nouveauProj != null) {
                projectilesActifs.add(nouveauProj);
            }
        }
        List<Projectile> aSupprimer = new ArrayList<>();
        for (Projectile proj : projectilesActifs) {
            proj.deplacer();
            if (!proj.estActif()) {
                aSupprimer.add(proj);
            }
        }
        projectilesActifs.removeAll(aSupprimer);
    }




    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }
    public int getBudget() { return this.budget.get(); }

    public List<Tour> getToursActives() { return toursActives; }
    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }

    public ObservableList<Projectile> getProjectilesActifs() { return projectilesActifs; }

    public SimpleIntegerProperty budgetProperty() {
        return this.budget;
    }

    public void ajouterBudget (int montant) {
        this.budget.set(this.budget.get() + montant);
    }

    public boolean dépense(int montant) {
        if (this.budget.get() >= montant) {
            this.budget.set(this.budget.get() - montant);
            return true;
        }
        return false;
    }

    // Dans EnvironnementJeu.java
    public BFS getBfs() { return bfs; }
    public Sommet getCible() { return cible; }
    public List<Sommet> getSpawns() { return spawns; }
}