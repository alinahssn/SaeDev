package universite_paris8.iut.aulhassan.maphopital.modele;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Graphes {

    private Terrain terrain;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private Set<Sommet> obstacles = new HashSet<>();

    public Graphes(Terrain terrain) {
        this.terrain = terrain;
        this.listeAdj = new HashMap<>();
        construit();
    }

    private void construit() {
        int[][] map = terrain.getMap();
        int h = terrain.getHauteur();
        int l = terrain.getLargeur();

        // 1. créer les sommets pour chaque case traversable
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < l; i++) {
                if (map[j][i] == 0 || map[j][i] == 3) {
                    listeAdj.put(new Sommet(i, j), new HashSet<>());
                }
            }
        }
        for(int j = 0; j < h; ++j ){
            for(int i = 0; i < l; ++i) {
                Sommet s = this.getSommet(i, j);
                if (s == null) continue;
                //droite
                if(dansGraphes(i-1,j) && getSommet(i-1,j) != null) {
                    ((Set)this.listeAdj.get(s)).add(this.getSommet(i - 1, j));
                }
                //droite
                if (dansGraphes(i + 1, j) && getSommet(i+1,j) != null) {
                    ((Set)this.listeAdj.get(s)).add(this.getSommet(i + 1, j));
                }
                //bas
                if (dansGraphes(i, j + 1) && getSommet(i,j+1) != null) {
                    ((Set)this.listeAdj.get(s)).add(this.getSommet(i, j + 1));
                }
                //haut
                if (dansGraphes(i, j - 1) && getSommet(i,j-1) != null) {
                    ((Set)this.listeAdj.get(s)).add(this.getSommet(i, j - 1));
                }
            }
        }


    }

    public boolean dansGraphes(int x, int y){
        return x >= 0 && x < terrain.getLargeur() && y >= 0 && y < terrain.getHauteur();
    }

    public void deconnecte(Sommet s) {
        obstacles.add(s);
    }

    public void reconnecte(Sommet s) {
        obstacles.remove(s);
    }

    public boolean estDeconnecte(Sommet s) {
        return obstacles.contains(s);
    }

    public Set<Sommet> adjacents(Sommet s) {
        return !estDeconnecte(s) ? listeAdj.get(s) : new HashSet<>();
    }

    public Sommet getSommet(int x, int y) {
        Iterator var4 = this.listeAdj.keySet().iterator();

        Sommet sommet;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            sommet = (Sommet)var4.next();
        } while(sommet.getX() != x || sommet.getY() != y);

        return sommet;
    }
    public Set<Sommet> getSommets() {
        return this.listeAdj.keySet();
    }




}