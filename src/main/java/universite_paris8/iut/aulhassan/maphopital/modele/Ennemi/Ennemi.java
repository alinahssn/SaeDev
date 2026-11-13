package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;
import universite_paris8.iut.aulhassan.maphopital.modele.Tour.Masquier;

import java.util.ArrayList;

public class Ennemi {

    private SimpleIntegerProperty x = new SimpleIntegerProperty(16*32);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private SimpleIntegerProperty pv;

    private int pvMax;
    private int attaque;
    private int vitesse;
    private int vitesseBase;
    private boolean estRalenti = false;
    private int recompense;

    private boolean estMort;


    private ArrayList<Sommet> chemin;
    private int indexChemin = 0;
    private int ciblePixelX = 0;
    private int ciblePixelY = 0;

    private int framesBloque = 0;
    private int cooldownAttaqueMasquier = 0;
    private boolean nouvelleCase = false;
    private int spawnX;
    private int spawnY;

    public Ennemi(int pv, int attaque, int vitesse, int recompense) {
        this.pvMax = pv;
        this.pv = new SimpleIntegerProperty(pv);
        this.attaque = attaque;
        this.vitesse = vitesse;
        this.vitesseBase = vitesse;
        this.recompense = recompense;
    }

    public Ennemi() { this(80, 5, 1, 15);}


    public void bloquer(int frames) {
        this.framesBloque = Math.max(this.framesBloque, frames);
    }

    public boolean estBloque() {
        return framesBloque > 0;
    }

    public void attaquerMasquier(Masquier masquier) {
        if (cooldownAttaqueMasquier > 0) {
            cooldownAttaqueMasquier--;
            return;
        }
        masquier.subirDegats(attaque);
        cooldownAttaqueMasquier = 30;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void ralentir(double facteur) {
        if (!estRalenti) {
            this.vitesse = Math.max(1, (int)(vitesseBase * facteur));
            this.estRalenti = true;
        }
    }

    public void restaurerVitesse() {
        if (estRalenti) {
            this.vitesse = vitesseBase;
            this.estRalenti = false;
        }
    }

    public boolean estRalenti() { return estRalenti; }

    public int getPv() { return pv.get(); }
    public int getPvMax() { return pvMax; }
    public int getAttaque() { return attaque; }
    public int getVitesse() { return vitesse; }
    public int getRecompense() { return recompense; }
    public int getSpawnX() { return spawnX; }
    public int getSpawnY() { return spawnY; }

    public void setSpawn(int spawnX, int spawnY) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    public void setRecompense(int recompense) {
        this.recompense = recompense;
    }

    public void subirDegats(int degats) {
        pv.set(Math.max(0, pv.get() - degats));

        if (!estVivant()) {
            mourir();
        }
    }

    private void mourir() { this.estMort = true; }

    public boolean estVivant() { return this.pv.get() > 0; }

    public int getX() { return this.x.get(); }
    public void setX(int nouveauX) { this.x.set(nouveauX); }
    public SimpleIntegerProperty xProperty() { return this.x; }

    public int getY() { return this.y.get(); }
    public void setY(int nouveauY) { this.y.set(nouveauY); }
    public SimpleIntegerProperty yProperty() { return this.y; }

    public void setChemin(ArrayList<Sommet> chemin) {
        this.chemin = chemin;
        if (chemin != null && chemin.size() > 1) {
            indexChemin = 1;
            Sommet premier = chemin.get(1);
            ciblePixelX = premier.getX() * 32;
            ciblePixelY = premier.getY() * 32;
        }
    }

    public ArrayList<Sommet> getChemin() { return this.chemin; }

    private int avancerVers(int position, int cible, int vitesse) {
        if (position < cible) {
            return Math.min(position + vitesse, cible);
        }
        if (position > cible) {
            return Math.max(position - vitesse, cible);
        }
        return position;
    }

    public void deplacer() {
        if (chemin == null) return;

        if (framesBloque > 0) {
            framesBloque--;
            return;
        }
        nouvelleCase = false;

        setX(avancerVers(getX(), ciblePixelX, getVitesse()));
        setY(avancerVers(getY(), ciblePixelY, getVitesse()));

        if (getX() == ciblePixelX && getY() == ciblePixelY && indexChemin < chemin.size()) {
            nouvelleCase = true;
            indexChemin++;

            if (indexChemin < chemin.size()) {
                Sommet prochain = chemin.get(indexChemin);
                ciblePixelX = prochain.getX() * 32;
                ciblePixelY = prochain.getY() * 32;

            }
        }
    }

    public SimpleIntegerProperty pvProperty() { return pv; }

    public boolean estArriveSurNouvelleCase() {
        return nouvelleCase;
    }
}

