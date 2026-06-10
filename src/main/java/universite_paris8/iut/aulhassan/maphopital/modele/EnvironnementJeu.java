package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EnvironnementJeu {

    // On regroupe les objets du modèle qu'on a déjà
    private Terrain terrain;
    private Patient patient;
    private int[][] distMap;
    private int budget = 200;

    // On regroupe les listes de données qui étaient dans le contrôleur
    private List<Tour> toursActives;
    private ObservableList<Ennemi> ennemisActifs = FXCollections.observableArrayList();
    private List<Projectile> projectilesActifs;

    public EnvironnementJeu() {
        // Au moment où on crée le jeu, on initialise tout le modèle
        this.terrain = new Terrain();
        this.patient = new Patient();

        // On calcule la carte des distances pour les ennemis
        BFS bfs = new BFS(this.terrain, 23, 12);
        this.distMap = bfs.getDistMap();

        // On initialise nos listes vides
        this.toursActives = new ArrayList<>();
        this.projectilesActifs = new ArrayList<>();
    }


    public Terrain getTerrain() { return terrain; }
    public Patient getPatient() { return patient; }
    public int[][] getDistMap() { return distMap; }
    public int getBudget() { return budget; }

    public List<Tour> getToursActives() { return toursActives; }

    public ObservableList<Ennemi> getEnnemisActifs() { return ennemisActifs; }

    public List<Projectile> getProjectilesActifs() { return projectilesActifs; }

    public void ajouterBudget (int montant) {
        this.budget += montant;
    }

    public boolean dépense (int montant){
        if(this.budget >= montant){
            this.budget -= montant;
            return true;
        }
        return false;
    }
}