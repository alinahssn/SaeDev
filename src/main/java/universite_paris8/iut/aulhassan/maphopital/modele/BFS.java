package universite_paris8.iut.aulhassan.maphopital.modele;

import java.util.*;

public class BFS {

    private Graphes g;
    private Sommet source;
    private ArrayList<Sommet> parcours;
    private Map<Sommet, Sommet> predecesseurs;

    public BFS(Graphes g, Sommet source) {
        this.g = g;
        this.source = source;
        parcours = new ArrayList<>();
        predecesseurs = new HashMap<>();
        algoBFS();
    }
    //

    private void algoBFS() {
        LinkedList<Sommet> fifo = new LinkedList<>();
        fifo.add(source);
        predecesseurs.put(source, null);
        while (!fifo.isEmpty()) {
            Sommet actuel = fifo.poll();
            parcours.add(actuel);
            for (Sommet voisin : g.adjacents(actuel)) {
                if (!predecesseurs.containsKey(voisin)) {
                    predecesseurs.put(voisin, actuel);
                    fifo.add(voisin);
                }
            }
        }
    }

    public ArrayList<Sommet> cheminVersSource(Sommet cible) {
        if (!predecesseurs.containsKey(cible)) {
            return new ArrayList<>();
        }
        ArrayList<Sommet> chemin = new ArrayList<>();
        Sommet actuel = cible;
        while (actuel != null) {
            chemin.add(actuel);
            actuel = predecesseurs.get(actuel);
        }
        Collections.reverse(chemin);
        return chemin;
    }

    public ArrayList<Sommet> getParcours() {
        return parcours;
    }

    public Map<Sommet, Sommet> getPredecesseurs() {
        return predecesseurs;
    }

    public void setSource(Sommet source) {
        this.source = source;
        clear();
        algoBFS();
    }

    public void setG(Graphes g) {
        this.g = g;
        clear();
        algoBFS();
    }

    private void clear() {
        this.parcours.clear();
        this.predecesseurs.clear();
    }
}