package universite_paris8.iut.aulhassan.maphopital.modele.BFS.deplacement;

import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.ArrayList;

public class DeplacementBFS implements StrategieDeplacement{

    private ArrayList<Sommet> chemin;
    private int indexChemin = 0;
    private int ciblePixelX = 0;
    private int ciblePixelY = 0;
    private boolean vientAtteindreNouvelleCase = false;

    public void setChemin(ArrayList<Sommet> chemin) {
        this.chemin = chemin;
        if (chemin != null && chemin.size() > 1) {
            indexChemin = 1;
            Sommet premier = chemin.get(1);
            ciblePixelX = premier.getX() * 32;
            ciblePixelY = premier.getY() * 32;
        }
    }

    public ArrayList<Sommet> getChemin() {
        return chemin;
    }

    public boolean estArriveSurNouvelleCase() {
        return vientAtteindreNouvelleCase;
    }

    private int avancerVers(int position, int cible, int vitesse) {
        if (position < cible) {
            return Math.min(position + vitesse, cible);
        }
        if (position > cible) {
            return Math.max(position - vitesse, cible);
        }
        return position;
    }

    @Override
    public void deplacer(Ennemi ennemi) {
        if (chemin == null) return;

        if (ennemi.estBloque()) {
            ennemi.decrementerBlocage();
            return;
        }
        vientAtteindreNouvelleCase = false;

        ennemi.setX(avancerVers(ennemi.getX(), ciblePixelX, ennemi.getVitesse()));
        ennemi.setY(avancerVers(ennemi.getY(), ciblePixelY, ennemi.getVitesse()));

        if (ennemi.getX() == ciblePixelX && ennemi.getY() == ciblePixelY && indexChemin < chemin.size()) {
            vientAtteindreNouvelleCase = true;
            indexChemin++;

            if (indexChemin < chemin.size()) {
                Sommet prochain = chemin.get(indexChemin);
                ciblePixelX = prochain.getX() * 32;
                ciblePixelY = prochain.getY() * 32;

            }

        }

    }

}
