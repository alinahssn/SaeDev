package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

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

    public void deplacer(int[][] distMap) {
        int col = getX() / 32;
        int lig = getY() / 32;

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        int bestCol = col, bestLig = lig;
        int bestDist = distMap[lig][col];

        for (int[] dir : directions) {
            int nc = col + dir[0];
            int nl = lig + dir[1];

            if (nc < 0 || nc >= distMap[0].length) continue;
            if (nl < 0 || nl >= distMap.length) continue;
            if (distMap[nl][nc] == -1) continue;

            if (distMap[nl][nc] < bestDist) {
                bestDist = distMap[nl][nc];
                bestCol = nc;
                bestLig = nl;
            }
        }

        setX(bestCol * 32);
        setY(bestLig * 32);
    }

    public SimpleIntegerProperty pvProperty() {
        return pv;
    }
}