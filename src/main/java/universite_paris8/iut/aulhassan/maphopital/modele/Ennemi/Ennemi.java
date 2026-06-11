package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

import java.util.ArrayList;

public class Ennemi {

    private SimpleIntegerProperty x = new SimpleIntegerProperty(16*32);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private SimpleIntegerProperty pv;
    private int pvMax;
    private int attaque;
    private int vitesse;
    private int recompense;
    private boolean estMort;
    private Terrain terrain;
    private ArrayList<Sommet> chemin;
    private int indexChemin = 0;
    private int ciblePixelX = 0;
    private int ciblePixelY = 0;


//
    public Ennemi(int pv, int attaque, int vitesse, int recompense) {
        this.pvMax = pv;
        this.pv = new SimpleIntegerProperty(pv);
        this.attaque = attaque;
        this.vitesse = vitesse;
        this.recompense = recompense;
    }

    public Ennemi() {
        this(80, 5, 1, 15);
    }

    public int getPv() {
        return pv.get();
    }

    public int getPvMax() {
        return pvMax;
    }

    public int getAttaque() {
        return attaque;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getRecompense() {
        return recompense;
    }


    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setRecompense(int recompense) {
        this.recompense = recompense;
    }

    public void subirDegats(int degats) {
        pv.set(Math.max(0, pv.get() - degats));
        if (pv.get() <= 0) mourir();
    }

    private void mourir() {
        this.estMort = true;
    }

    public boolean estVivant() {
        return this.pv.get() > 0;
    }

    public int getX() {
        return this.x.get();
    }

    public void setX(int nouveauX) {
        this.x.set(nouveauX);
    }

    public SimpleIntegerProperty xProperty() {
        return this.x;
    }

    public int getY() {
        return this.y.get();
    }

    public void setY(int nouveauY) {
        this.y.set(nouveauY);
    }

    public SimpleIntegerProperty yProperty() {
        return this.y;
    }

    public void setChemin(ArrayList<Sommet> chemin) {
        this.chemin = chemin;

        if (chemin != null && chemin.size() > 1) {

            indexChemin = 1;

            Sommet premier = chemin.get(1);

            ciblePixelX = premier.getX() * 32;
            ciblePixelY = premier.getY() * 32;
        }
    }

    public void deplacer() {
        if (chemin == null) return;


        if (getX() < ciblePixelX) setX(getX() + vitesse);
        else if (getX() > ciblePixelX) setX(getX() - vitesse);
        if (getY() < ciblePixelY) setY(getY() + vitesse);
        else if (getY() > ciblePixelY) setY(getY() - vitesse);


        if (getX() == ciblePixelX && getY() == ciblePixelY && indexChemin < chemin.size()) {
            Sommet prochaine = chemin.get(indexChemin);
            ciblePixelX = prochaine.getX() * 32;
            ciblePixelY = prochaine.getY() * 32;
            indexChemin++;
        }
    }


    public SimpleIntegerProperty pvProperty() {
        return pv;
    }
}