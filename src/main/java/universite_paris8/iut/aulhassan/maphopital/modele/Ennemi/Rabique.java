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
    public String toString(){
        return "Rabique : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
