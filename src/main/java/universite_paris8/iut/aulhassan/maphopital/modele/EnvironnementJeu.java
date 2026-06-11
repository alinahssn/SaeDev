package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Projectile;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Tour;

import java.util.ArrayList;
import java.util.List;

public class EnvironnementJeu {

    // On regroupe les objets du modèle qu'on a déjà
    private Terrain terrain;
    private Patient patient;
    private int[][] distMap;
    private SimpleIntegerProperty budget = new SimpleIntegerProperty(200);

    // On regroupe les listes de données qui étaient dans le contrôleur
    private List<Tour> toursActives;
    private ObservableList<Ennemi> ennemisActifs = FXCollections.observableArrayList();
    private List<Projectile> projectilesActifs;
    private Graphes graphe;
    private BFS bfs;
    private Sommet cible;


    public EnvironnementJeu() {
        // Au moment où on crée le jeu, on initialise tout le modèle
        this.terrain = new Terrain();
        this.patient = new Patient();
        graphe = new Graphes(terrain);

        Sommet source = graphe.getSommet(16, 0);  // position de départ des ennemis
        cible = graphe.getSommet(23, 12);  // le lit

        bfs = new BFS(graphe, source);

        // On initialise nos listes vides
        this.toursActives = new ArrayList<>();
        this.projectilesActifs = new ArrayList<>();
    }


    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }
    public int[][] getDistMap() { return distMap; }
    public int getBudget() { return this.budget.get(); }

    public List<Tour> getToursActives() { return toursActives; }

    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }

    public List<Projectile> getProjectilesActifs() { return projectilesActifs; }

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
}