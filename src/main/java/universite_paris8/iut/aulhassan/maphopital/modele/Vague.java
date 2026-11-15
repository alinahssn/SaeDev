package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.FabriqueEnnemi.*;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;

import java.util.ArrayList;
import java.util.List;

public class Vague {

    private EnvironnementJeu environnement;

    private SimpleIntegerProperty numeroVague = new SimpleIntegerProperty(0);
    private int ennemisASpawner = 0;
    private int compteurSpawn = 0;
    private int delaiSpawn = 120;

    public Vague(EnvironnementJeu environnement) {
        this.environnement = environnement;
    }

    public void lancerVague() {
        if (ennemisASpawner > 0) return;

        if (numeroVague.get() >= vagues.size()) {
            System.out.println("Plus de vagues !");
            return;
        }

        numeroVague.set(numeroVague.get() + 1);

        ennemisASpawner = getTailleVague(numeroVague.get());

        compteurSpawn = 0;

        System.out.println("Vague " + numeroVague.get() + " lancée !");
    }

    public Ennemi tickSpawn() {
        if (ennemisASpawner <= 0) return null;

        compteurSpawn++;
        if (compteurSpawn < delaiSpawn) return null;

        compteurSpawn = 0;
        ennemisASpawner--;

        int indexDansVague = getTailleVague(numeroVague.get()) - ennemisASpawner - 1;//position virus dans la vague

        Ennemi ennemi = creerEnnemi(numeroVague.get(), indexDansVague);

        Sommet spawn = choisirSpawn(numeroVague.get(), indexDansVague);
        ennemi.setX(spawn.getX() * 32);
        ennemi.setY(spawn.getY() * 32);
        ennemi.setSpawn(spawn.getX() * 32, spawn.getY() * 32);

        ennemi.setChemin(environnement.getBfs().cheminVersCible(spawn));

        return ennemi;
    }

    private int getTailleVague(int vague) {
        if (vague < 1 || vague > vagues.size()) return 0;
        return vagues.get(vague - 1).size();
    }

    private List<List<FabriqueEnnemi>> vagues = List.of(
            List.of(new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique()),
            List.of(new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabEnrhume(), new FabEnrhume()),
            List.of(new FabEnrhume(),new FabEnrhume(), new FabEnrhume(), new FabCovide(), new FabCovide(), new FabGrippe()),
            List.of(new FabEnrhume(), new FabCovide(), new FabCovide(), new FabGrippe(), new FabGrippe(), new FabEbola()),
            List.of(new FabEbola(), new FabEbola(), new FabRabique(), new FabGrippe(), new FabGastrique(), new FabGastrique(), new FabGastrique(),new FabGastrique()),
            List.of(new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique(), new FabGastrique()),
            List.of(new FabSujetAlpha())
    );

    private Ennemi creerEnnemi(int vague, int index) {
        return vagues.get(vague - 1).get(index).creerEnnemi();
    }

    public int[] getSpawnsActifsVague() {
        return getSpawnsActifs(numeroVague.get());
    }

    private int[] getSpawnsActifs(int vague) {
        switch (vague) {
            case 1: return new int[]{0};
            case 2: return new int[]{1};
            case 3: return new int[]{0, 1};
            case 4: return new int[]{2};
            case 5: return new int[]{0, 1, 2};
            case 6: return new int[]{0, 1, 2, 3};
            case 7: return new int[]{4};
            default: return new int[]{0};
        }
    }

    private Sommet choisirSpawn(int vague, int index) {
        int[] spawnsActifs = getSpawnsActifs(vague);
        List<Sommet> spawns = environnement.getSpawns();
        int indiceSpawn = spawnsActifs[index % spawnsActifs.length];//repartition equitable virus dans diff entrées active
        return spawns.get(indiceSpawn);
    }

    /*public boolean vagueEnCours() {
        return ennemisASpawner > 0;
    }

    public int getNumeroVague() {
        return numeroVague.get();
    }
    */

    public SimpleIntegerProperty numeroVagueProperty() {
        return numeroVague;
    }

    public boolean toutesLesVaguesFinies() {
        return this.numeroVague.get() == vagues.size() && this.ennemisASpawner == 0;
    }
}