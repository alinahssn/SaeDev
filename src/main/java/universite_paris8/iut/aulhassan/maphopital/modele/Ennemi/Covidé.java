package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Covidé extends Ennemi {

    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 2;
    private static final int RECOMPENSE = 30;

    public Covidé() {
        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public String toString(){
        return "Covidé : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
