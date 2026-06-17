package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;
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

        numeroVague.set(numeroVague.get() + 1);

        switch (numeroVague.get()) {
            case 1: ennemisASpawner = 5;  break;
            case 2: ennemisASpawner = 5;  break;
            case 3: ennemisASpawner = 6;  break;
            case 4: ennemisASpawner = 6;  break;
            case 5: ennemisASpawner = 8;  break;
            case 6: ennemisASpawner = 8;  break;
            case 7: ennemisASpawner = 1;  break;
            default:
                System.out.println("Plus de vagues !");
                numeroVague.set(numeroVague.get() - 1);
        }

        compteurSpawn = 0;
        System.out.println("Vague " + numeroVague.get() + " lancée !");
    }

    public Ennemi tickSpawn() {
        if (ennemisASpawner <= 0) return null;

        compteurSpawn++;
        if (compteurSpawn < delaiSpawn) return null;

        compteurSpawn = 0;
        ennemisASpawner--;

        int indexDansVague = getTailleVague(numeroVague.get()) - ennemisASpawner - 1;

        Ennemi ennemi = creerEnnemi(numeroVague.get(), indexDansVague);

        Sommet spawn = choisirSpawn(numeroVague.get(), indexDansVague);
        ennemi.setX(spawn.getX() * 32);
        ennemi.setY(spawn.getY() * 32);
        ennemi.setSpawn(spawn.getX() * 32, spawn.getY() * 32);

        Sommet spawnEnnemi = new Sommet(ennemi.getX() / 32, ennemi.getY() / 32);
        ArrayList<Sommet> chemin = environnement.getBfs().cheminVersSource(spawnEnnemi);
        //java.util.Collections.reverse(chemin);
        ennemi.setChemin(chemin);

        return ennemi;
    }

    private int getTailleVague(int vague) {
        switch (vague) {
            case 1: return 5;
            case 2: return 5;
            case 3: return 6;
            case 4: return 6;
            case 5: return 8;
            case 6: return 8;
            case 7: return 1;
            default: return 0;
        }
    }

    private Ennemi creerEnnemi(int vague, int index) {
        switch (vague) {
            case 1:
                return new Gastrique();
            case 2:
                if (index < 3) return new Gastrique();
                else           return new Enrhumé();
            case 3:
                if (index < 3)      return new Enrhumé();
                else if (index < 5) return new Covidé();
                else                return new Grippé();
            case 4:
                if (index == 0)     return new Enrhumé();
                else if (index < 3) return new Covidé();
                else if (index < 5) return new Grippé();
                else                return new Ebola();
            case 5:
                if (index < 2)       return new Ebola();
                else if (index == 2) return new Rabique();
                else if (index == 3) return new Grippé();
                else                 return new Gastrique();
            case 6:
                return new Gastrique();
            case 7:
                return new SujetAlpha();
            default:
                return new Gastrique();
        }
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
        int indiceSpawn = spawnsActifs[index % spawnsActifs.length];
        return spawns.get(indiceSpawn);
    }

    public boolean vagueEnCours() {
        return ennemisASpawner > 0;
    }

    public int getNumeroVague() {
        return numeroVague.get();
    }

    public SimpleIntegerProperty numeroVagueProperty() {
        return numeroVague;
    }
}