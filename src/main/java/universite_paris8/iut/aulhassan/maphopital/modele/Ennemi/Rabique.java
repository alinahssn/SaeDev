package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Rabique extends Ennemi {
    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 16;
    private static final int RECOMPENSE = 30;

    public Rabique() {

        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public void deplacer() {

        int cibleX = 23 * 32;
        int cibleY = 12 * 32;

        if (getX() < cibleX) setX(Math.min(getX() + getVitesse(), cibleX));
        else if (getX() > cibleX) setX(Math.max(getX() - getVitesse(), cibleX));
        if (getY() < cibleY) setY(Math.min(getY() + getVitesse(), cibleY));
        else if (getY() > cibleY) setY(Math.max(getY() - getVitesse(), cibleY));
    }

    @Override
    public String toString(){
        return "Rabique : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
