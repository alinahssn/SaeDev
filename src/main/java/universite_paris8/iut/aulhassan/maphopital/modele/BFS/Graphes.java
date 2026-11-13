package universite_paris8.iut.aulhassan.maphopital.modele.BFS;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

public class Graphes {

    private Terrain terrain;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private Set<Sommet> obstacles = new HashSet<>();

    public Graphes(Terrain terrain) {
        this.terrain = terrain;
        this.listeAdj = new HashMap<>();
        construit();
    }

    private void ajouterVoisin(Sommet sommet, int x, int y) {
        if (dansGraphes(x, y)) {
            Sommet voisin = getSommet(x, y); // recherche le sommet correspondant

            if (voisin != null) {
                listeAdj.get(sommet).add(voisin);
            }
        }
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

                ajouterVoisin(s, i - 1, j); // gauche
                ajouterVoisin(s, i + 1, j); // droite
                ajouterVoisin(s, i, j + 1); // bas
                ajouterVoisin(s, i, j - 1); // haut

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
//
    public Sommet getSommet(int x, int y) {
        Sommet sommet = new Sommet(x, y);

        if (listeAdj.containsKey(sommet)) {
            return sommet;
        }

        return null;
    }

    public Set<Sommet> getSommets() {
        return this.listeAdj.keySet();
    }

}