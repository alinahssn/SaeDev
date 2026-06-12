package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class SujetAlpha extends Ennemi {
    private static final int PV_DEPART = 500;
    private static final int ATTAQUE = 100;
    private static final int  VITESSE = 8;
    private static final int RECOMPENSE = 400;

    public SujetAlpha() {
        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public String toString(){
        return "Sujet Alpha : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
