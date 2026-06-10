package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ennemi {

    private SimpleIntegerProperty x = new SimpleIntegerProperty(16);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private int pv;
    private int pvMax;
    private int attaque;
    private int vitesse;
    private int recompense;
    private boolean estMort;
    private Terrain terrain;
    private int ciblePixelX;
    private int ciblePixelY;

    public Ennemi(int pv, int attaque, int vitesse, int recompense) {
        this.pv = pv;
        this.pvMax = pv;
        this.attaque = attaque;
        this.vitesse = vitesse;
        this.recompense = recompense;

    }

    public Ennemi() {
        this(80, 5, 1, 15);
    }

    public int getPv() {
        return pv;
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

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
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
        this.pv -= degats;

        if (this.pv <= 0) {
            this.pv = 0;
            mourir();
        }
    }

    private void mourir() {
        this.estMort = true;
    }

    public boolean estVivant() {
        return this.pv > 0;
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

    public void deplacer(int[][] distMap) {


        if (ciblePixelX == 0 && ciblePixelY == 0) {
            ciblePixelX = getX();
            ciblePixelY = getY();
        }

        if(getX() == ciblePixelX && getY() == ciblePixelY) {

        int col = getX() / 32;
        int lig = getY() / 32;

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        int bestCol = col, bestLig = lig;
        int bestDist = distMap[lig][col];

        for (int[] dir : directions) {
            int nc = col + dir[0];
            int nl = lig + dir[1];

            if (nc >= 0 && nc < distMap[0].length) {
                if (nl >= 0 && nl < distMap.length) {
                    if (distMap[nl][nc] != -1) {
                        if (distMap[nl][nc] < bestDist) {
                            bestDist = distMap[nl][nc];
                            bestCol = nc;
                            bestLig = nl;
                        }
                    }
                }
            }
        }
        ciblePixelX = bestCol * 32;
        ciblePixelY = bestLig * 32;
        }


        if (getX() < ciblePixelX) setX(getX() + vitesse);
        if (getX() > ciblePixelX) setX(getX() - vitesse);
        if (getY() < ciblePixelY) setY(getY() + vitesse);
        if (getY() > ciblePixelY) setY(getY() - vitesse);

    }

}